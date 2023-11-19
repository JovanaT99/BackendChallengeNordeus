package com.example.demo.repository;

import com.example.demo.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query("SELECT m FROM Manager m " +
            "JOIN Follow f ON m.id = f.manager.id " +
            "WHERE f.auction.id = :auctionId")
    List<Manager> findManagersFollowingAuction(@Param("auctionId") Long auctionId);

    @Query("SELECT m FROM Manager m " +
            "JOIN Bid b ON m.id = b.manager.id " +
            "WHERE b.auction.id = :auctionId " +
            "ORDER BY b.price DESC " +
            "LIMIT 1")
    List<Manager> findWinnerManagerOfAuction(@Param("auctionId") Long auctionId);

}
