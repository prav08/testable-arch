package io.pillopl.testablearch.ex3.ui;

import io.pillopl.testablearch.ex3.MessagingApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MessagingApplication.class)
public class CreditCardApplicationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void gettingCardIs200OK() throws Exception {
        //given
        HttpEntity<String> request = new HttpEntity<>("{\"pesel\": \"717171717\"}", applicationJsonHeader());

        //expect
        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/", request
                , Void.class).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void notGettingCardIs403FORBIDDEN() throws Exception {
        //given
        HttpEntity<String> request = new HttpEntity<>("{\"pesel\": \"617171717\"}", applicationJsonHeader());

        //expect
        assertThat(this.restTemplate.postForEntity("http://localhost:" + port + "/", request
                , Void.class).getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    MultiValueMap<String, String> applicationJsonHeader() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Content-Type", "application/json");
        return map;
    }
}