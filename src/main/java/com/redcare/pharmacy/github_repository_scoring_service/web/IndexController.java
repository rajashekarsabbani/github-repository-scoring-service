package com.redcare.pharmacy.github_repository_scoring_service.web;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller for index and health checks.
 */
@Hidden
@RestController
@Slf4j
public class IndexController {
    @GetMapping({"/", "/index.html"})
    public String index() {
        log.trace("GithubRepositoryScoringService: index called ..");
        return "GithubRepositoryScoringService";
    }
}
