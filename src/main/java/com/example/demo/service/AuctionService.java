package com.example.demo.service;


import com.example.demo.model.Bid;
import com.example.demo.model.Follow;

public interface AuctionService {


    Bid placeBid(Long auctionId, Long managerId);


    Follow placeFollow(Long auctionId, Long managerId);
}
