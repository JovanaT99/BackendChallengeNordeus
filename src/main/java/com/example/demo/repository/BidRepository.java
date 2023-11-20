package com.example.demo.repository;

import com.example.demo.model.Auction;
import com.example.demo.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b " +
            "JOIN FETCH b.manager " +
            "WHERE b.auction.id = :auctionId " +
            "ORDER BY b.id DESC")
    List<Bid> findByAuctionIdOrderByIdDesc(Long auctionId);

}
