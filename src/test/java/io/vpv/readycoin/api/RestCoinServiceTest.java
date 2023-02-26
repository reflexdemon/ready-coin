package io.vpv.readycoin.api;

import io.vpv.readycoin.ReadyCoinApplication;
import io.vpv.readycoin.domain.APIError;
import io.vpv.readycoin.service.CoinService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RestCoinServiceTest extends ReadyCoinApplication {

    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CoinService coinService;
    @BeforeEach
    void setUp() {
        coinService.reset();
    }

    private ResponseEntity<Map<Double, Integer>> callAPI(Integer amount) {
        ParameterizedTypeReference<Map<Double, Integer>> responseType =
                new ParameterizedTypeReference<>() {};
        String endpoint = "http://localhost:" + port + "/api/coin/change/" + amount;
        RequestEntity<Void> request = RequestEntity.get(endpoint)
                .accept(MediaType.APPLICATION_JSON).build();
        return restTemplate.exchange(request, responseType);
    }

    @Test
    void emptyCoinBalanceAllHappyPath() {
        Assertions.assertThat(callAPI(10).getBody())
                .containsEntry(0.25, 40);
        Assertions.assertThat(callAPI(20).getBody())
                .containsEntry(0.25, 60)
                .containsEntry(0.1, 50);
        Assertions.assertThat(callAPI(10).getBody())
                .containsEntry(0.10, 50)
                .containsEntry(0.05, 100);
    }

    @Test
    void handleUserInputError() {
        Integer amount = 11;
        String endpoint = "http://localhost:" + port + "/api/coin/change/" + amount;
        RequestEntity<Void> request = RequestEntity.get(endpoint)
                .accept(MediaType.APPLICATION_JSON).build();
        Assertions.assertThat(restTemplate.exchange(request, APIError.class).getBody())
                .hasFieldOrPropertyWithValue("code", 400);
    }

    @Test
    void handleServiceException() {
        emptyCoinBalanceAllHappyPath();
        Integer amount = 10;
        String endpoint = "http://localhost:" + port + "/api/coin/change/" + amount;
        RequestEntity<Void> request = RequestEntity.get(endpoint)
                .accept(MediaType.APPLICATION_JSON).build();
        Assertions.assertThat(restTemplate.exchange(request, APIError.class).getBody())
                .hasFieldOrPropertyWithValue("code", 500);
    }
}