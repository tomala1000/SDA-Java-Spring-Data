package com.kchmielewski.sda.java.spring02java.data.player.service;

import com.kchmielewski.sda.java.spring02java.data.player.entity.PlayerEntity;
import com.kchmielewski.sda.java.spring02java.data.player.model.Player;
import com.kchmielewski.sda.java.spring02java.data.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public List<Player> all() {
        return toPlayerDto(repository.findAll());
    }

    public void add(Player player) {
        checkNotNull(player, "PlayerEntity cannot be null");
        repository.save(toEntity(player));
    }

    public void remove(Player player) {
//        checkNotNull(player, "PlayerEntity cannot be null");
//        players.remove(player);
    }


    private PlayerEntity toEntity(Player player) {
        return new PlayerEntity(player.getName(), player.getSurname());
    }

    private Player toPlayerDto(PlayerEntity entity) {
        return new Player(entity.getName(), entity.getSurname());
    }

    private List<Player> toPlayerDto(List<PlayerEntity> entities) {
        return entities.stream().map(this::toPlayerDto).collect(Collectors.toList());
    }
}
