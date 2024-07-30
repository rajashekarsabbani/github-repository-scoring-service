package com.redcare.pharmacy.github_repository_scoring_service.service.client;

import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubApiPageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubInternalRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * Defines the HttpExchange interface for the GitHub's repositories API.
 * See <a href="https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories">GitHub repositories API</a>
 */
public interface GithubRepositoryClient {

    /**
     * Fetch the GitHub repositories using the given filters.
     *
     * @param queryQualifier query qualifier of the search. This is mandatory parameter. The query contains one or more search keywords and qualifiers. Qualifiers allow you to limit your search to specific areas of GitHub.
     * @param sort sort option of the search repositories. Sorts the results of the query by any one field example: number of stars, forks, or updated..
     * @param order Determines whether the first search result returned is the highest number of matches (desc) or lowest number of matches (asc). This parameter is ignored unless you provide sort.
     * @param page The page number of the results to fetch, defaults to 1.
     * @param perPage The number of results per page (max 100).
     *
     * @return {@link GithubApiPageResponse} The page of {@link GithubInternalRepository}.
     */
    @GetExchange(value="/search/repositories", accept = "application/vnd.github+json")
    GithubApiPageResponse<GithubInternalRepository> fetchRepositories(
            @RequestParam(name = "q") String queryQualifier,
            @RequestParam(name = "sort") String sort,
            @RequestParam(name = "order") String order,
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "per_page") Integer perPage
    );

}
