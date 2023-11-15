package com.example.demo.db;

import com.example.demo.model.Auction;
import com.example.demo.model.Manager;
import com.example.demo.model.Player;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuctionRepository auctionRepository;
    private final PlayerRepository playerRepository;

    private final ManagerRepository managerRepository;


    @Autowired
    public DataLoader(AuctionRepository auctionRepository, PlayerRepository playerRepository, ManagerRepository managerRepository) {
        this.auctionRepository = auctionRepository;
        this.playerRepository = playerRepository;
        this.managerRepository = managerRepository;
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


        Player player3 = new Player();
        player3.setName("Pera Petrovic");
        player3.setPosition("Odbrambeni");
        playerRepository.save(player3);



        Player player4 = new Player();
        player4.setName("Pera Petrovic");
        player4.setPosition("Odbrambeni");
        playerRepository.save(player4);


        Player player5 = new Player();
        player5.setName("Pera Petrovic");
        player5.setPosition("Odbrambeni");
        playerRepository.save(player5);



        Player player6 = new Player();
        player6.setName("Pera Petrovic");
        player6.setPosition("Odbrambeni");
        playerRepository.save(player6);



        Player player7 = new Player();
        player7.setName("Pera Petrovic");
        player7.setPosition("Odbrambeni");
        playerRepository.save(player7);




        Player player8 = new Player();
        player8.setName("Pera Petrovic");
        player8.setPosition("Odbrambeni");
        playerRepository.save(player8);



        Player player9 = new Player();
        player9.setName("Pera Petrovic");
        player9.setPosition("Odbrambeni");
        playerRepository.save(player9);


        Player player10 = new Player();
        player10.setName("Pera Petrovic");
        player10.setPosition("Odbrambeni");
        playerRepository.save(player10);



        // Kreiranje i snimanje aukcija
        Auction auction1 = new Auction();
        auction1.setPlayer(player1);
        auction1.setStartPrice(100.0);
        auction1.setCurrentPrice(150.0);
        auction1.setStatus("Aktivna");
        auction1.setStartAt(LocalDateTime.now());
        auction1.setEndAt(LocalDateTime.now().plusSeconds(120));
        auction1.setCreatedAt(LocalDateTime.now());
        auctionRepository.save(auction1);


        Manager manager = new Manager();
        manager.setName("Jovana");
        manager.setEmail("jo@gmail.com");
        manager.setPhone(638121528L);
        managerRepository.save(manager);





    }





}
