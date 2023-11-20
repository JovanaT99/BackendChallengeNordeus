package com.example.demo.db;

import com.example.demo.model.Auction;
import com.example.demo.model.Manager;
import com.example.demo.model.Player;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.ManagerRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Component
public class DataLoader implements CommandLineRunner {

    private final AuctionRepository auctionRepository;
    private final PlayerRepository playerRepository;
    private final ManagerRepository managerRepository;
    private final List<String> possibleNames = Arrays.asList("Marko", "Ivan", "Pera", "Jovana", "Milica", "Natalija", "Heni", "Zlatko", "Dusan", "Andrej");
    private final List<String> possiblePositions = Arrays.asList("Napadač", "Odbrambeni", "Vezni igrač", "Golman");
    private final MailService mailService;


    @Autowired
    public DataLoader(AuctionRepository auctionRepository, PlayerRepository playerRepository, ManagerRepository managerRepository, MailService mailService) {
        this.auctionRepository = auctionRepository;
        this.playerRepository = playerRepository;
        this.managerRepository = managerRepository;
        this.mailService = mailService;
    }

    @Override
    public void run(String... args) {

        createPlayersAndAuctions();

        Manager manager = new Manager();
        manager.setName("Jovana");
        manager.setEmail("jovanablagojevic98@gmail.com");
        manager.setPhone(638121528L);
        managerRepository.save(manager);


        Manager manager1 = new Manager();
        manager1.setName("anavoy");
        manager1.setEmail("jovanablagojevic99@icloud.com");
        manager1.setPhone(638121528L);
        managerRepository.save(manager1);


        Manager manager2 = new Manager();
        manager2.setName("JB");
        manager2.setEmail("jovana@solrise.finance");
        manager2.setPhone(638121528L);
        managerRepository.save(manager2);

    }

    @Scheduled(fixedRate = 600000, initialDelay = 600000) // 10 minuta
    public void createPlayersAndAuctions() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Player player = new Player();
            String randomName = possibleNames.get(random.nextInt(possibleNames.size()));
            player.setName(randomName + " " + i);
            String randomPosition = possiblePositions.get(random.nextInt(possiblePositions.size()));
            player.setPosition(randomPosition);
            player.setRank(random.nextInt(9)+1);
            playerRepository.save(player);

            Auction auction = new Auction();
            auction.setPlayer(player);
            auction.setStartPrice(100.0 + random.nextInt(100));
            auction.setCurrentPrice(auction.getStartPrice());
            auction.setStatus("active");
            auction.setStartAt(LocalDateTime.now());
            auction.setEndAt(LocalDateTime.now().plusSeconds(120));
            auction.setCreatedAt(LocalDateTime.now());
            auctionRepository.save(auction);
        }
        System.out.println("Created 10 new auctions");
        //Za sve managere poslati im mejl da ima nove aukcije
        List<Manager> managerRepository = this.managerRepository.findAll();
        for (Manager manager : managerRepository) {
            mailService.sendTextEmail(manager.getEmail(), "New auctions starting", "There are 10 new auctions, check them out!");
        }
    }

//    @Scheduled(fixedRate = 1000) // Every second
//    public void checkIfEnded() {
//        System.out.println("Checking if auctions ended");
//
//        List<Auction> auctions = auctionRepository.findByStatus("active");
//
//        for (Auction auction : auctions) {
//            if (auction.getEndAt().isBefore(LocalDateTime.now())) {
//                auction.setStatus("ended");
//                auctionRepository.save(auction);
//
//                List<Manager> allManagers = managerRepository.findManagersFollowingAuction(auction.getId());
//                List<Manager> winnerManagerList = managerRepository.findWinnerManagerOfAuction(auction.getId());
//                Optional<Manager> winnerManager = winnerManagerList.stream().findFirst();
//
//                System.out.println("Other manager: " + allManagers.size());
//                System.out.println("Winner manager: " + winnerManager.isPresent());
//                for (Manager manager : allManagers) {
//
//                    String finalText = "";
//                    if (winnerManager.isPresent() && manager.getId().equals(winnerManager.get().getId())) {
//                        finalText = "You won this auction for " + (auction.getCurrentPrice() - 1);
//                    } else if (winnerManager.isPresent()) {
//                        finalText = "You did't win. Winning bet was " + (auction.getCurrentPrice() - 1);
//                    }
//
//                    System.out.println("Send for " + manager.getEmail() + "final text: " + finalText);
//
//
//                    mailService.sendTextEmail(
//                            manager.getEmail(),
//                            "Auction ended (" + auction.getPlayer().getName() + ")",
//                            "Auction for " + auction.getPlayer().getName() + " has ended. " + finalText
//                    );
//                }
//            }
//        }
//    }
}



