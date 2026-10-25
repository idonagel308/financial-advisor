package com.idonagel.financialadvisor.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void validRequestReturnsRecommendation() throws Exception {
        String body = """
            {
              "timeHorizon": 3, "riskTolerance": 1, "upcomingExpenses": 3, "familySupport": 3,
              "monthlyIncome": 8000, "monthlyExpenses": 7000, "currentEquity": 10000, "years": 30
            }
            """;

        mockMvc.perform(post("/api/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.riskScore").value(2.5))
                .andExpect(jsonPath("$.stockPercentage").value(50.0));
    }
    @Test
    void invalidValueReturnsBadRequest() throws Exception {
        String body = """
            {
              "timeHorizon": 3, "riskTolerance": 7, "upcomingExpenses": 3, "familySupport": 3,
              "monthlyIncome": 8000, "monthlyExpenses": 7000, "currentEquity": 10000, "years": 30
            }
            """;

        mockMvc.perform(post("/api/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());


    }
    @Test
    void missingFieldReturnsBadRequest() throws Exception {
        String body = """
            {
              "timeHorizon": 3, "riskTolerance": 1, "upcomingExpenses": 3, "familySupport": 3,
              "monthlyIncome": 8000, "monthlyExpenses": 7000, "currentEquity": 10000
            }
            """;

        mockMvc.perform(post("/api/recommendation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());

    }
}
