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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final ManagerRepository managerRepository;

    private final FollowRepository followRepository;
    private final MailService mailService;


    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository, BidRepository bidRepository, ManagerRepository managerRepository, FollowRepository followRepository, MailService mailService) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.managerRepository = managerRepository;
        this.followRepository = followRepository;
        this.mailService = mailService;
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


        auction.setCurrentPrice(auction.getCurrentPrice() + 1);
        if (isAuctionLastFiveSecond(auction)) {
            auction.setEndAt(LocalDateTime.now().plusSeconds(5));
        }

        auctionRepository.save(auction);

        Optional<Follow> optionalFollow = followRepository.findByAuctionIdAndManagerId(auctionId, managerId);
        if (optionalFollow.isEmpty()) {
            Follow follow = new Follow();
            follow.setAuction(auction);
            follow.setManager(manager);
            followRepository.save(follow);

        }

        List<Manager> allManagers = managerRepository.findManagersFollowingAuction(auctionId);
//        System.out.println("Other"+allManagers.size());

        for (Manager otherManager : allManagers) {
            // Ne šalji email manageru koji je postavio ponudu
            if (otherManager.getId().equals(managerId)) {
                continue;
            }
            System.out.println("Send " + otherManager.getEmail());
            mailService.sendTextEmail(
                    otherManager.getEmail(),
                    "New bid on auction you follow (" + auction.getPlayer().getName() + ")",
                    "There is new bid for " + bid.getPrice() + " by manager " + manager.getName()
            );
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
        Optional<Follow> optionalFollow = followRepository.findByAuctionIdAndManagerId(auctionId, managerId);
        if (optionalFollow.isPresent()) {
            throw new IllegalArgumentException("Follow vec postoji.");
        }

        Auction auction = optionalAuction.get();
        Manager manager = optionalManager.get();

        if (!isAuctionActive(auction)) {
            throw new IllegalStateException("Aukcija je već završena.");
        }

        Follow follow = new Follow();
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
