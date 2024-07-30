package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * Generic paging response class for the GitHub api classes.
 *
 * @param <T> Type of the Element
 */
@Builder
public record GithubApiPageResponse<T>(
        @JsonProperty("total_count")
        long totalCount,
        @JsonProperty("incomplete_results")
        boolean incompleteResults,
        List<T> items
) {
}
