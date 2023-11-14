package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String position;
    private Integer rank;


    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auction> auctions;

}
