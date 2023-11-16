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
    @Query("SELECT a FROM Auction a WHERE a.endAt > :currentTime")
    List<Auction> findByEndAtAfter(LocalDateTime currentTime);

// vratiti da je managerid jednak managerid(ovo znaci sve aukcije koje manager follovuje - koristiti follow tabelu ) i veci od trenutnog vremena  - /manager/{managerid}/auctions

    @Query("SELECT f.auction FROM Follow f " +
            "WHERE f.manager.id = :managerId " +
            "AND f.auction.endAt > :currentTime")
    List<Auction> findAllForManager(@Param("managerId") Long managerId, @Param("currentTime") LocalDateTime currentTime);

}






