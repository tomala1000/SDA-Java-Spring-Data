package com.kchmielewski.sda.java.spring01java.data.player.web;

import com.kchmielewski.sda.java.spring01java.data.player.model.Player;
import com.kchmielewski.sda.java.spring01java.data.player.service.PlayerService;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PlayerControllerTest {
    private final PlayerService service = new PlayerService();
    private final MessageSource messageSource = mock(MessageSource.class);
    private final PlayerController controller = new PlayerController(service, messageSource);
    private MockMvc mvc;

    @Before
    public void setup() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mvc = standaloneSetup(controller).setViewResolvers(viewResolver).build();
    }

    @Test
    public void display() throws Exception {
        MvcResult result = mvc.perform(get("/players")).andExpect(status().isOk()).andReturn();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getModelAndView().getViewName()).isEqualTo("players");
        softly.assertThat(service.all()).hasSize(0);
        softly.assertAll();
    }

    @Test
    public void add() throws Exception {
        MvcResult result = mvc.perform(post("/players").param("name", "Name").param("surname", "Surname"))
                .andExpect(status().is3xxRedirection()).andReturn();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getModelAndView().getViewName()).isEqualTo("redirect:players");
        softly.assertThat(service.all()).hasSize(1);
        softly.assertAll();
    }

    @Test
    public void remove() throws Exception {
        service.add(new Player("Abc", "Def"));

        MvcResult result = mvc.perform(post("/players/remove").param("name", "Abc").param("surname", "Def"))
                .andExpect(status().isOk()).andReturn();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getModelAndView().getViewName()).isEqualTo("players");
        softly.assertThat(service.all()).hasSize(0);
        softly.assertAll();
    }
}