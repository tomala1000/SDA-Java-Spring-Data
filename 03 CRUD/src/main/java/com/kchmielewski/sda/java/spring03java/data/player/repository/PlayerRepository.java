package com.kchmielewski.sda.java.spring03java.data.player.repository;

import com.kchmielewski.sda.java.spring03java.data.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
}
