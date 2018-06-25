package me.dehasi.statistics.controller;

import me.dehasi.statistics.domain.Statistic;
import me.dehasi.statistics.service.StatisticService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

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
    public void getStatistic_returnsStatistic() throws Exception {
        Statistic t = new Statistic(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 1);
        when(service.getStatistic()).thenReturn(t);

        ResultActions resultActions = mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk());

//        resultActions.andDo(document("{class-name}/{method-name}",
//                REQUEST_FIELDS_SNIPPET));
    }


}
