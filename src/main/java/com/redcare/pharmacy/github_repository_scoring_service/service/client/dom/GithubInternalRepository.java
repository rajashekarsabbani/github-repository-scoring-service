package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * GitHub repository representation
 */
@Builder
public record GithubInternalRepository(
        long id,
        String name,
        @JsonProperty("full_name")
        String fullName,
        RepositoryOwner owner,
        @JsonProperty("html_url")
        String htmlUrl,
        String description,
        String language,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @JsonProperty("updated_at")
        LocalDateTime updatedAt,
        @JsonProperty("forks_count")
        long forksCount,
        @JsonProperty("stargazers_count")
        long stargazersCount
) {
}
