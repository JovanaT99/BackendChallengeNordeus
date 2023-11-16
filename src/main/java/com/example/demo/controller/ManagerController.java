package com.example.demo.controller;


import com.example.demo.model.Auction;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")

public class ManagerController {
    private final AuctionRepository auctionRepository;
    @Autowired
    public ManagerController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }


//    @GetMapping("/{managerId}/auctions")
//        public List<Auction> getManagerAuctions() {
//            return auctionRepository.findAllForManager();
//        }
//
}
