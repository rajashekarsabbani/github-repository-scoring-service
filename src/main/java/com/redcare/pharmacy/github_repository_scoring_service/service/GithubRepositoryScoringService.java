package com.redcare.pharmacy.github_repository_scoring_service.service;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepositoryFilter;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;

/**
 * Defines the interface for the GitHub's repositories scoring.
 */
public interface GithubRepositoryScoringService {

    /**
     * Fetches the popular GitHub's repositories using filters and paging.
     *
     * @param filter Search filter with langauge and createdFrom options.
     * @param pageRequest paging request with page and size of the paging results.
     * @return {@link PageResponse} page of items of type  {@link GithubRepository}
     */
    PageResponse<GithubRepository> fetchPopularGithubRepositories(GithubRepositoryFilter filter, PagingRequest pageRequest);

}
