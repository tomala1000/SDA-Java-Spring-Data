package com.kchmielewski.sda.java.spring02java.data.player.web;

import com.kchmielewski.sda.java.spring02java.data.player.model.Player;
import com.kchmielewski.sda.java.spring02java.data.player.service.PlayerService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("players")
@SessionAttributes("playerSession")
public class PlayerController {
    private final PlayerService playerService;
    private final MessageSource messageSource;

    public PlayerController(PlayerService playerService, MessageSource messageSource) {
        this.playerService = playerService;
        this.messageSource = messageSource;
    }

    @ModelAttribute
    public Player defaultPlayer() {
        return new Player("Fill my name...", "...and my surname!");
    }

    @ModelAttribute
    public PlayerSession session() {
        return new PlayerSession();
    }

    @GetMapping("")
    public String display(Model model) {
        model.addAttribute("players", playerService.all());
        model.addAttribute("hello", playerService.all());

        return "players";
    }

    @GetMapping("/get/{from}/{to}")
    public String display(@PathVariable int from, @PathVariable int to, Model model) {
        if (true) {
            throw new NullPointerException();
        }
        model.addAttribute("players", playerService.all().subList(from, to));

        return "players";
    }

    @PostMapping("")
    public String add(@Valid @ModelAttribute Player player, BindingResult bindingResult, Model model, @ModelAttribute PlayerSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("players", playerService.all());
            return "players";
        }
        playerService.add(player);
        session.setCounter(session.getCounter() + 1);
        session.setMostRecentPlayer(player);
        model.addAttribute("players", playerService.all());

        return "redirect:players";
    }

    @PostMapping("remove")
    public String remove(@ModelAttribute Player player, Model model) {
        playerService.remove(player);
        model.addAttribute("players", playerService.all());

        return "players";
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception exception, Model model, Locale locale) {
        model.addAttribute("exception", exception);
        model.addAttribute("message", messageSource.getMessage("error.message", null, locale));

        return "errors";
    }
}
