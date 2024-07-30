package com.redcare.pharmacy.github_repository_scoring_service.api.dom;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Represents the GitHub Repository API object.
 */
@Builder
public record GithubRepository(
        @Schema(description = "ID of the github repository")
        long id,
        @Schema(description = "Short name of the github repository")
        String name,
        @Schema(description = "Full name of the github repository")
        String fullName,
        @Schema(description = "Owner login name of the github repository")
        String owner,
        @Schema(description = "Html url of the github repository")
        String url,
        @Schema(description = "Language of the github repository example: Java C++ PHP..")
        String language,
        @Schema(description = "Description of the github repository")
        String description,
        @Schema(description = "The count of the stars given to the github repository")
        long starsCount,
        @Schema(description = "The count of the forks of the github repository")
        long forksCount,
        @Schema(description = "The created date time github repository")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime createdAt,
        @Schema(description = "The updated date time github repository")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime updatedAt
) {
}
