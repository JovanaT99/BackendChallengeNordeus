package com.example.demo.service;

import com.example.demo.model.Auction;
import com.example.demo.model.Bid;
import com.example.demo.model.Follow;
import com.example.demo.model.Manager;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.BidRepository;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final ManagerRepository managerRepository;

    private final FollowRepository followRepository;


    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository, BidRepository bidRepository, ManagerRepository managerRepository, FollowRepository followRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.managerRepository=managerRepository;
        this.followRepository = followRepository;
    }

    @Override
    public Bid placeBid(Long auctionId, Long managerId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isEmpty()) {
            throw new IllegalArgumentException("Aukcija sa datim ID-om ne postoji.");
        }

        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isEmpty()) {
            throw new IllegalArgumentException("Manager sa datim ID-om ne postoji.");
        }


        Auction auction = optionalAuction.get();
        Manager manager = optionalManager.get();

        if (!isAuctionActive(auction)) {
            throw new IllegalStateException("Aukcija je već završena.");
        }

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setManager(manager);
        bid.setPrice(auction.getCurrentPrice());
        bid.setBidAt(LocalDateTime.now());
        bidRepository.save(bid);


        auction.setCurrentPrice(auction.getCurrentPrice()+1);
        if(isAuctionLastFiveSecond(auction))    {
            System.out.println("test");
         auction.setEndAt(LocalDateTime.now().plusSeconds(5));
 }

        auctionRepository.save(auction);

        Optional<Follow> optionalFollow = followRepository.findByAuctionIdAndManagerId(auctionId,managerId);
        if (optionalFollow.isEmpty()) {
            Follow follow=new Follow();
            follow.setAuction(auction);
            follow.setManager(manager);
            followRepository.save(follow);

        }



        return bid;
    }

    @Override
    public Follow placeFollow(Long auctionId, Long managerId) {
        Optional<Auction> optionalAuction = auctionRepository.findById(auctionId);
        if (optionalAuction.isEmpty()) {
            throw new IllegalArgumentException("Aukcija sa datim ID-om ne postoji.");
        }

        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isEmpty()) {
            throw new IllegalArgumentException("Manager sa datim ID-om ne postoji.");
        }
        Optional<Follow> optionalFollow = followRepository.findByAuctionIdAndManagerId(auctionId,managerId);
        if (optionalFollow.isPresent()) {
            throw new IllegalArgumentException("Follow vec postoji.");
        }

        Auction auction = optionalAuction.get();
        Manager manager = optionalManager.get();

        if (!isAuctionActive(auction)) {
            throw new IllegalStateException("Aukcija je već završena.");
        }

       Follow follow=new Follow();
        follow.setAuction(auction);
        follow.setManager(manager);
        followRepository.save(follow);
        return follow;
    }










    private boolean isAuctionActive(Auction auction) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return auction.getEndAt().isAfter(currentDateTime);
    }

    private boolean isAuctionLastFiveSecond(Auction auction) {
        LocalDateTime currentDateTime = LocalDateTime.now().plusSeconds(5);
        return auction.getEndAt().isBefore(currentDateTime);
    }


}
