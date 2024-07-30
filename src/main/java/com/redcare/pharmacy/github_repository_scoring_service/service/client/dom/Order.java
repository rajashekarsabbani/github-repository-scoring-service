package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the GitHub order options.
 */
@Getter
@RequiredArgsConstructor
public enum Order {
    DESC("desc"),
    ASC("asc");

    private final String value;
}
