package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the GitHub sort options
 */
@Getter
@RequiredArgsConstructor
public enum Sort {
    STARS("stars"),
    FORKS("forks"),
    UPDATED("updated");

    private final String value;
}
