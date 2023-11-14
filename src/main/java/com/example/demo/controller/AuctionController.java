package com.example.demo.controller;

import com.example.demo.model.Auction;
import com.example.demo.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }
    @GetMapping
    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }


}