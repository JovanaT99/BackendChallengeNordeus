package com.example.demo.repository;

import com.example.demo.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query("SELECT a FROM Auction a JOIN FETCH a.player WHERE a.endAt > :currentTime")
    List<Auction> findByEndAtBefore(LocalDateTime currentTime);

    @Query("SELECT f.auction FROM Follow f " +
            "WHERE f.manager.id = :managerId ")
    List<Auction> findAllForManager(@Param("managerId") Long managerId);


    @Query("SELECT a FROM Auction a " +
            "JOIN FETCH a.player " +
            "WHERE a.status = :status")
    List<Auction> findByStatus(String status);

}






