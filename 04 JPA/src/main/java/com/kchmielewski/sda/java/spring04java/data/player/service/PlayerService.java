package com.kchmielewski.sda.java.spring04java.data.player.service;

import com.kchmielewski.sda.java.spring04java.data.player.entity.PlayerEntity;
import com.kchmielewski.sda.java.spring04java.data.player.model.Player;
import com.kchmielewski.sda.java.spring04java.data.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public List<Player> findByName(String name) {
        return toPlayerDto(repository.findAllByName(name));
    }

    public List<Player> findAll() {
        return toPlayerDto(repository.findAll());
    }

    public Optional<Player> findOne(Integer playerId) {
        return repository.findById(playerId).map(this::toPlayerDto);
    }

    public void add(Player player) {
        checkNotNull(player, "Player cannot be null");
        checkArgument(player.getId() == null, "If player is to be added, it needs it's id to be null but is %s.", player.getId());
        repository.save(toEntity(player));
    }

    public void remove(Integer playerId) {
        checkNotNull(playerId, "Player id cannot be null");
        repository.deleteById(playerId);
    }

    public void edit(Player player) {
        checkNotNull(player, "Player cannot be null");
        checkArgument(player.getId() != null, "If player is to be edited, it needs it's id to be set.");
        repository.save(toEntity(player));
    }


    private PlayerEntity toEntity(Player player) {
        return new PlayerEntity(player.getId(), player.getName(), player.getSurname());
    }

    private Player toPlayerDto(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getName(), entity.getSurname());
    }

    private List<Player> toPlayerDto(List<PlayerEntity> entities) {
        return entities.stream().map(this::toPlayerDto).collect(Collectors.toList());
    }
}
