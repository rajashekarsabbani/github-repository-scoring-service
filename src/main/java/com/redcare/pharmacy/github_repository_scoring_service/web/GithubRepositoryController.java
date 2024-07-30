package com.redcare.pharmacy.github_repository_scoring_service.web;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepositoryFilter;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;
import com.redcare.pharmacy.github_repository_scoring_service.service.GithubRepositoryScoringService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller for the GitHub's repositories.
 */
@Validated
@RestController
@RequestMapping("/v1/github/repositories")
@RequiredArgsConstructor
public class GithubRepositoryController {

    private final GithubRepositoryScoringService githubRepositoryScoringService;

    /**
     * Get API call to fetch popular GitHub's repositories.
     *
     * @param filter        Filter options to search repositories, example: language and createdFrom.
     * @param pageRequest   Paging options for the search of repositories to fetch repositories using page and size.
     * @param bindingResult Binding result for spring bean validations.
     * @return {@link PageResponse} Page of {@link GithubRepository} items.
     */
    @Operation(
            summary = "Fetches popular GitHub repositories with filter and paging options. " +
                    "Returns the repositories in the populary order, the popularity scoring of the repository is based on stars count, forks count and updated date time of the repositories." +
                    "The returned repositores are sorted in the descending order of stars count, forks count and updated date time of the repositories."

    )
    @GetMapping(value = "/popular-repositories", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission('USER')")
    public PageResponse<GithubRepository> fetchPopularGithubRepositories(@ParameterObject @Valid @NotNull GithubRepositoryFilter filter, @ParameterObject @Valid @NotNull PagingRequest pageRequest, final BindingResult bindingResult) {
        return githubRepositoryScoringService.fetchPopularGithubRepositories(filter, pageRequest);
    }

}
