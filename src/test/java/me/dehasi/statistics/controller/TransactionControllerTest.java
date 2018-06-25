package me.dehasi.statistics.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.dehasi.statistics.domain.Transaction;
import me.dehasi.statistics.service.StatisticService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZonedDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StatisticService service;

    private MockMvc mockMvc;

    @Before
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void submitTransaction_inMinuteTransaction_201() throws Exception {

        Transaction transaction = new Transaction(BigDecimal.TEN, ZonedDateTime.now(Clock.systemUTC()).toInstant().toEpochMilli());

        String content = objectMapper.writeValueAsString(transaction);

        ResultActions resultActions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated());
    }

    @Test
    public void submitTransaction_oldMinuteTransaction_204() throws Exception {
        Transaction transaction = new Transaction(BigDecimal.TEN, ZonedDateTime.now(Clock.systemUTC()).toInstant().minusSeconds(100).toEpochMilli());
        String content = objectMapper.writeValueAsString(transaction);

        ResultActions resultActions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isNoContent());
    }
}
