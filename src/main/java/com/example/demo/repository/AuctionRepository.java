package com.example.demo.repository;

import com.example.demo.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AuctionRepository  extends JpaRepository<Auction, Long> {


}






