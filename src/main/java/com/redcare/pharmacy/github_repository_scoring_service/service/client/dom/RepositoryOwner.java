package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * Represents the repository owner details
 */
@Builder
public record RepositoryOwner(
        long id,
        String login,
        @JsonProperty("avatar_url")
        String avatarUrl
        
){}
