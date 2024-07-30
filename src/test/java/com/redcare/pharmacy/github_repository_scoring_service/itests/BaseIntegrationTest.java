package com.redcare.pharmacy.github_repository_scoring_service.itests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"classpath:application-itests.properties"})
public abstract class BaseIntegrationTest {

    protected static final String ROLE_USER = "USER";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @RegisterExtension
    protected static final WireMockExtension WM = WireMockExtension.newInstance()
            .options(WireMockConfiguration.options().dynamicPort())
            .build();

    @DynamicPropertySource
    static void registerBackendServer(DynamicPropertyRegistry registry) {
        registry.add("com.redcare.pharmacy.github.repository.client.base-url", () -> "http://localhost:" + WM.getPort());
    }

    @SneakyThrows
    protected static String getFileContentAsString(final String fileName) {
        try (InputStream stream = new ClassPathResource(fileName).getInputStream()) {
            return IOUtils.toString(stream, StandardCharsets.UTF_8);
        }
    }
}
