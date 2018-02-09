package com.kchmielewski.sda.java.spring03java.data.player.service;

import com.kchmielewski.sda.java.spring03java.data.player.model.Player;
import com.kchmielewski.sda.java.spring03java.data.player.repository.PlayerRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

public class PlayerServiceTest {
    private final PlayerRepository repository = mock(PlayerRepository.class);
    private final PlayerService service = new PlayerService(repository);

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

        assertThat(service.findAll()).hasSize(1).containsExactly(new Player("Adam", "Lallana"));
    }

    @Test
    public void removeDecreasesSizeOfPlayers() {
        service.add(new Player("Adam", "Lallana"));
        service.add(new Player("Philippe", "Coutinho"));
        service.remove(1);

        assertThat(service.findAll()).hasSize(1).containsExactly(new Player("Philippe", "Coutinho"));
    }
}