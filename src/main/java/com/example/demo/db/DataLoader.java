package com.example.demo.db;

import com.example.demo.model.Auction;
import com.example.demo.model.Player;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuctionRepository auctionRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public DataLoader(AuctionRepository auctionRepository, PlayerRepository playerRepository) {
        this.auctionRepository = auctionRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        // Kreiranje i snimanje igrača
        Player player1 = new Player();
        player1.setName("Marko Marković");
        player1.setPosition("Napadač");
        playerRepository.save(player1);

        Player player2 = new Player();
        player2.setName("Ivan Ivanović");
        player2.setPosition("Odbrambeni");
        playerRepository.save(player2);

        // Kreiranje i snimanje aukcija
        Auction auction1 = new Auction();
        auction1.setPlayer(player1);
        auction1.setStartPrice(100.0);
        auction1.setCurrentPrice(150.0);
        auction1.setStatus("Aktivna");
        auction1.setStartAt(LocalDateTime.now());
        auction1.setEndAt(LocalDateTime.now().plusDays(7));
        auction1.setCreatedAt(LocalDateTime.now());
        auctionRepository.save(auction1);

    }
}
