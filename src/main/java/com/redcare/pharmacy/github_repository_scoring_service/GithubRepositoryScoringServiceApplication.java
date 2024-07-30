package com.redcare.pharmacy.github_repository_scoring_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GitHub's repositories scoring service application.
 * Provides the functionality of the popularity scoring of the GitHub's repositories.
 */
@SpringBootApplication
public class GithubRepositoryScoringServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubRepositoryScoringServiceApplication.class, args);
    }

}
