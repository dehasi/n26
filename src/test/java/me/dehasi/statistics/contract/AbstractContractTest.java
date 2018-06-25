package me.dehasi.statistics.contract;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractContractTest {
    @LocalServerPort
    int port;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void configureRestAssured() {
        RestAssured.port = port;
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }
}
