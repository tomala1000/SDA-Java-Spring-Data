package com.kchmielewski.sda.java.spring01java.data.player.service;

import com.kchmielewski.sda.java.spring01java.data.player.model.Player;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerServiceTest {
    private final PlayerService service = new PlayerService();

    @Test
    public void forNullAddThrowsException() {
        assertThatThrownBy(() -> service.add(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNullRemoveThrowsException() {
        assertThatThrownBy(() -> service.remove(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addIncreasesSizeOfPlayers() {
        service.add(new Player("Adam", "Lallana"));

        assertThat(service.all()).hasSize(1).containsExactly(new Player("Adam", "Lallana"));
    }

    @Test
    public void removeDecreasesSizeOfPlayers() {
        service.add(new Player("Adam", "Lallana"));
        service.add(new Player("Philippe", "Coutinho"));
        service.remove(new Player("Adam", "Lallana"));

        assertThat(service.all()).hasSize(1).containsExactly(new Player("Philippe", "Coutinho"));
    }
}