package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditAccidentPage() throws Exception {
        Accident accident = new Accident();
        when(accidentService.findById(any(Integer.class))).thenReturn(accident);
        this.mockMvc.perform(get("/formUpdateAccident?id=" + accident.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }

    @Test
    @WithMockUser
    public void shouldCreateNewAccidentAndRedirect() throws Exception {
        String name = "accident_name";
        String typeId = "1";
        String rId1 = "1";
        String rId2 = "2";
        String text = "accident_text";
        String address = "accident_address";
        this.mockMvc.perform(post("/saveAccident")
                .param("name", name)
                .param("type.id", typeId)
                .param("rIds", rId1, rId2)
                .param("text", text)
                .param("address", address))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argument.capture(), eq(new String[]{rId1, rId2}));
        assertThat(argument.getValue().getName()).isEqualTo(name);
        assertThat(argument.getValue().getText()).isEqualTo(text);
        assertThat(argument.getValue().getAddress()).isEqualTo(address);
        assertThat(argument.getValue().getType().getId())
                .isEqualTo(Integer.parseInt(typeId));
    }

    @Test
    @WithMockUser
    public void shouldUpdateAccidentAndRedirect() throws Exception {
        String name = "accident_name";
        String typeId = "1";
        String rId1 = "1";
        String rId2 = "2";
        String text = "accident_text";
        String address = "accident_address";
        this.mockMvc.perform(post("/updateAccident")
                        .param("name", name)
                        .param("type.id", typeId)
                        .param("rIds", rId1, rId2)
                        .param("text", text)
                        .param("address", address))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argument.capture(), eq(new String[]{rId1, rId2}));
        assertThat(argument.getValue().getName()).isEqualTo(name);
        assertThat(argument.getValue().getText()).isEqualTo(text);
        assertThat(argument.getValue().getAddress()).isEqualTo(address);
        assertThat(argument.getValue().getType().getId())
                .isEqualTo(Integer.parseInt(typeId));
    }
}