package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;



@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table
public class Auction {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @JsonBackReference
   private Player player;
   private  double startPrice;
   private double currentPrice;
   private String status;
   private LocalDateTime startAt;

   private LocalDateTime endAt;
   private LocalDateTime createdAt;


}
