package com.redcare.pharmacy.github_repository_scoring_service.itests.web;

import com.redcare.pharmacy.github_repository_scoring_service.itests.BaseIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IndexControllerTest extends BaseIntegrationTest {

    @Test
    @SneakyThrows
    void testIndex() {
        mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("GithubRepositoryScoringService"));
    }

    @Test
    @SneakyThrows
    void testIndexHtml() {
        mockMvc.perform(get("/index.html").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("GithubRepositoryScoringService"));
    }
}
