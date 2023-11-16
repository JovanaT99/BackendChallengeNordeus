package com.example.demo.db;

import com.example.demo.model.Auction;
import com.example.demo.model.Manager;
import com.example.demo.model.Player;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Component
public class DataLoader implements CommandLineRunner {

    private final AuctionRepository auctionRepository;
    private final PlayerRepository playerRepository;
    private final ManagerRepository managerRepository;
    private final List<String> possibleNames = Arrays.asList("Marko", "Ivan", "Pera", "Jovana", "Milica","Natalija","Heni","Zlatko","Dusan","Andrej");
    private final List<String> possiblePositions = Arrays.asList("Napadač", "Odbrambeni", "Vezni igrač", "Golman");


    @Autowired
    public DataLoader(AuctionRepository auctionRepository, PlayerRepository playerRepository, ManagerRepository managerRepository) {
        this.auctionRepository = auctionRepository;
        this.playerRepository = playerRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createPlayersAndAuctions();

        Manager manager = new Manager();
        manager.setName("Jovana");
        manager.setEmail("jo@gmail.com");
        manager.setPhone(638121528L);
        managerRepository.save(manager);
    }

    @Scheduled(fixedRate = 600000) // 600000 ms = 10 minuta
    public void createPlayersAndAuctions() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Player player = new Player();
            String randomName = possibleNames.get(random.nextInt(possibleNames.size()));
            player.setName(randomName + " " + i);
            String randomPosition = possiblePositions.get(random.nextInt(possiblePositions.size()));
            player.setPosition(randomPosition);
            playerRepository.save(player);

            Auction auction = new Auction();
            auction.setPlayer(player);
            auction.setStartPrice(100.0 + random.nextDouble() * 100.0); // Nasumična početna cena između 100 i 200
            auction.setCurrentPrice(auction.getStartPrice());
            auction.setStatus("Aktivna");
            auction.setStartAt(LocalDateTime.now());
            auction.setEndAt(LocalDateTime.now().plusSeconds(120));
            auction.setCreatedAt(LocalDateTime.now());
            auctionRepository.save(auction);
        }
        }
    }



