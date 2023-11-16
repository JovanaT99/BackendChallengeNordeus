package com.example.demo.repository;

import com.example.demo.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AuctionRepository  extends JpaRepository<Auction, Long> {
    @Query("SELECT a FROM Auction a WHERE a.endAt > :currentTime")
     List<Auction> findByEndAtAfter(LocalDateTime currentTime);

    //vratiti sve aukcije za koje je endAt veci od trenutnog vremena /-auctions
// vratiti da je managerid jednak managerid(ovo znaci sve aukcije koje manager follovuje - koristiti follow tabelu ) i veci od trenutnog vremena  - /manager/{managerid}/auctions
//List<Auction> findAllForManager();


}






