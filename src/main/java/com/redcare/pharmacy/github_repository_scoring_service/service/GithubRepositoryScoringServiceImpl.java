package com.redcare.pharmacy.github_repository_scoring_service.service;


import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepositoryFilter;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;
import com.redcare.pharmacy.github_repository_scoring_service.common.util.PagingHelper;
import com.redcare.pharmacy.github_repository_scoring_service.converter.GithubRepositoryConverter;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.GithubRepositoryClient;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Comparator;

/**
 * Implementation of the {@link GithubRepositoryScoringService}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GithubRepositoryScoringServiceImpl implements GithubRepositoryScoringService {

    private final GithubRepositoryClient githubRepositoryClient;
    private final GithubRepositoryConverter githubRepositoryConverter;

    /**
     * Comparator to sort by popularity i.e sort by stars, forks and updated date time of the GitHub repositories
     */
    private static final Comparator<GithubInternalRepository> SORT_BY_POPULARITY = Comparator.comparing(GithubInternalRepository::stargazersCount)
            .thenComparing(GithubInternalRepository::forksCount)
            .thenComparing(GithubInternalRepository::updatedAt)
            .reversed();

    @Override
    public PageResponse<GithubRepository> fetchPopularGithubRepositories(final @NotNull GithubRepositoryFilter filter, final @NotNull PagingRequest pageRequest) {
        final String queryQualifier = getQueryQualifier(filter);
        Assert.hasText(queryQualifier, "At least one query filter ( language or createdFrom ) is mandatory");
        // Fetch the GitHub repositories with applying sort on 'stars' field in descending order, since github search supports sort by only one field
        // Then sort manually to find the popularity scoring
        final GithubApiPageResponse<GithubInternalRepository> repositoriesPage = githubRepositoryClient.fetchRepositories(
                getQueryQualifier(filter),
                Sort.STARS.getValue(),
                Order.DESC.getValue(),
                pageRequest.getPage(),
                pageRequest.getSize());
        Assert.notNull(repositoriesPage, "Github search query result page cannot be null");
        // Sort the items by popularity i.e sort by 'stars', 'forks', 'updated' fields in descending order
        return PagingHelper.toPageResonse(pageRequest, repositoriesPage, SORT_BY_POPULARITY, githubRepositoryConverter::convert);
    }

    private String getQueryQualifier(final GithubRepositoryFilter filter) {
        return GithubSearchParameterBuilder.builder()
                .add(QueryQualifier.LANGUAGE.getValue(), filter.language())
                .add(QueryQualifier.CREATED_FROM.getValue(), filter.createdFrom())
                .build();
    }

}
