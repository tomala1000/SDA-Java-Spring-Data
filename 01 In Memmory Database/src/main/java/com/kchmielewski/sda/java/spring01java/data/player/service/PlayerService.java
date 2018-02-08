package com.kchmielewski.sda.java.spring01java.data.player.service;

import com.kchmielewski.sda.java.spring01java.data.player.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PlayerService {
    private final List<Player> players = new ArrayList<>();

    public List<Player> all() {
        return Collections.unmodifiableList(players);
    }

    public void add(Player player) {
        checkNotNull(player, "Player cannot be null");
        players.add(player);
    }

    public void remove(Player player) {
        checkNotNull(player, "Player cannot be null");
        players.remove(player);
    }
}
