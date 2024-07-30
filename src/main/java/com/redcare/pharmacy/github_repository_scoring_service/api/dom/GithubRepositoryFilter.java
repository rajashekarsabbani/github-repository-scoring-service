package com.redcare.pharmacy.github_repository_scoring_service.api.dom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Filter class for the GitHub repositories search.
 * At least one of the filter either language or createdFrom is mandatory.
 */
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryFilter(
        @Schema(description = "Any one language of the github repository, example: C++ or Go or Java or JavaScript or PHP or Python or Ruby..")
        String language,
        @Schema(description = "Created from date of the github repository (inclusive)")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate createdFrom
) {
}