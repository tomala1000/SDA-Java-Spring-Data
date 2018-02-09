package com.kchmielewski.sda.java.spring04java.data.player.repository;

import com.kchmielewski.sda.java.spring04java.data.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    List<PlayerEntity> findAllByName(String name);
}
