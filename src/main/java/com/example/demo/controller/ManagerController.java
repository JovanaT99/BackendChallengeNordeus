package com.example.demo.controller;


import com.example.demo.model.Auction;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/managers")
@AllArgsConstructor

public class ManagerController {
    private final AuctionRepository auctionRepository;

    @GetMapping("{managerId}/auctions")
    public List<Auction> getAllAuctionsForManager(@PathVariable Long managerId) {
        LocalDateTime currentTime = LocalDateTime.now();
        return auctionRepository.findAllForManager(managerId);
    }
}
