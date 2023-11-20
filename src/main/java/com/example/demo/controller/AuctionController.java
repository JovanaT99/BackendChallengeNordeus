package com.example.demo.controller;

import com.example.demo.model.Auction;
import com.example.demo.model.Bid;
import com.example.demo.model.Follow;
import com.example.demo.model.Player;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.AuctionService;
import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionRepository auctionRepository;
    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionRepository auctionRepository, AuctionService auctionService) {
        this.auctionRepository = auctionRepository;
        this.auctionService = auctionService;
    }


    @GetMapping
    public List<Auction> getAuctionsEndAtAfterCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return auctionRepository.findByEndAtBefore(currentTime);
    }

    @GetMapping("{auctionId}")
    public Object getAuctionAndBids(
            @PathVariable Long auctionId
    ) {
        return auctionService.auctionWithBids(auctionId);
    }

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<Bid> postaviPonudu(
            @PathVariable Long auctionId,
            @RequestParam Long managerId) {

        try {
            Bid bid = auctionService.placeBid(auctionId, managerId);
            return ResponseEntity.ok(bid);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Došlo je do greške prilikom postavljanja ponude.");
        }
    }

    @PostMapping("/{auctionId}/follow")
    public ResponseEntity<Follow> postaviFollow(
            @PathVariable Long auctionId,
            @RequestParam Long managerId) {

        try {
            Follow follow = auctionService.placeFollow(auctionId, managerId);
            return ResponseEntity.ok(follow);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Došlo je do greške prilikom postavljanja ponude.");
        }
    }



}