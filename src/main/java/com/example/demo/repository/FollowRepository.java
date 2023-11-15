package com.example.demo.repository;
import com.example.demo.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByAuctionIdAndManagerId(Long auctionId, Long managerId);
}
