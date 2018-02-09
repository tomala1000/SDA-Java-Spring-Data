package com.kchmielewski.sda.java.spring04java.data.player.web;

import com.kchmielewski.sda.java.spring04java.data.player.model.Player;
import com.kchmielewski.sda.java.spring04java.data.player.service.PlayerService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

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


    @GetMapping("byName/{name}")
    public String findByName(@PathVariable String name, Model model) {
        model.addAttribute("players", playerService.findByName(name));

        return "players";
    }

    @GetMapping("")
    public String display(Model model) {
        model.addAttribute("players", playerService.findAll());

        return "players";
    }

    @GetMapping("edit/{playerId}")
    public String edit(Model model, @PathVariable Integer playerId) {
        Optional<Player> player = playerService.findOne(playerId);
        if (player.isPresent()) {
            model.addAttribute("player", player.get());
            return "playerEdit";
        }

        return "players";
    }

    @GetMapping("/get/{from}/{to}")
    public String display(@PathVariable int from, @PathVariable int to, Model model) {
        model.addAttribute("players", playerService.findAll().subList(from, to));

        return "players";
    }

    @PostMapping("")
    public String handleForm(@Valid Player player, BindingResult bindingResult, Model model,
                             @ModelAttribute PlayerSession session, @RequestParam(defaultValue = "") String removeId,
                             @RequestParam(defaultValue = "") String editId) {
        if (!editId.isEmpty()) {
            player.setId(Integer.valueOf(editId));
            return edit(player, bindingResult, model);
        } else if (!removeId.isEmpty()) {
            player.setId(Integer.valueOf(removeId));
            return remove(player.getId(), model);
        }
        return add(player, bindingResult, model, session);
    }

    @ExceptionHandler(Exception.class)
    public String error(Exception exception, Model model, Locale locale) {
        model.addAttribute("exception", exception);
        model.addAttribute("message", messageSource.getMessage("error.message", null, locale));

        return "errors";
    }

    private String add(@Valid Player player, BindingResult bindingResult, Model model, PlayerSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("players", playerService.findAll());
            return "/players";
        }
        playerService.add(player);
        session.setCounter(session.getCounter() + 1);
        session.setMostRecentPlayer(player);
        model.addAttribute("players", playerService.findAll());

        return "redirect:/players";
    }

    private String edit(Player player, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("player", player);
            return "playerEdit";
        }
        playerService.edit(player);
        model.addAttribute("players", playerService.findAll());

        return "redirect:/players";
    }

    private String remove(Integer playerId, Model model) {
        playerService.remove(playerId);
        model.addAttribute("players", playerService.findAll());

        return "redirect:/players";
    }
}
