package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the GitHub query qualifier parameter names.
 */
@Getter
@RequiredArgsConstructor
public enum QueryQualifier {
    LANGUAGE("language:"),
    CREATED_FROM("created:>=");

    private final String value;

}
