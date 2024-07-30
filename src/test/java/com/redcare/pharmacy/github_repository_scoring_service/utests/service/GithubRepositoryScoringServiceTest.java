package com.redcare.pharmacy.github_repository_scoring_service.utests.service;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepositoryFilter;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;
import com.redcare.pharmacy.github_repository_scoring_service.converter.GithubRepositoryConverter;
import com.redcare.pharmacy.github_repository_scoring_service.converter.GithubRepositoryConverterImpl;
import com.redcare.pharmacy.github_repository_scoring_service.service.GithubRepositoryScoringServiceImpl;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.GithubRepositoryClient;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.*;
import com.redcare.pharmacy.github_repository_scoring_service.utests.BaseUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GithubRepositoryScoringServiceTest extends BaseUnitTest {

    @Mock
    private GithubRepositoryClient githubRepositoryClient;
    @Spy
    private GithubRepositoryConverter githubRepositoryConverter = new GithubRepositoryConverterImpl();

    @InjectMocks
    private GithubRepositoryScoringServiceImpl service;

    @Test
    void testFetchPopularGithubRepositories() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .language("Java")
                .createdFrom(LocalDate.now())
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        final GithubInternalRepository item1 = createGithubInternalRepository(2L, 2L, LocalDateTime.now());
        final GithubInternalRepository item2 = createGithubInternalRepository(1L, 1L, LocalDateTime.now());
        final List<GithubInternalRepository> sourceItems = List.of(item1, item2);
        final List<GithubRepository> resultItems = List.of(toGithubRepository(item1), toGithubRepository(item2));
        callAndVerifyFetchPopularGithubRepositories(filter, pagingRequest, sourceItems, resultItems);
    }

    @Test
    void testFetchPopularGithubRepositoriesUsesStarsDescOrderWhenStarsCountIsDifferent() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .language("Java")
                .createdFrom(LocalDate.now())
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        final GithubInternalRepository item1 = createGithubInternalRepository(2L, 2L, LocalDateTime.now());
        final GithubInternalRepository item2 = createGithubInternalRepository(3L, 1L, LocalDateTime.now());
        final List<GithubInternalRepository> sourceItems = List.of(item1, item2);
        final List<GithubRepository> resultItems = List.of(toGithubRepository(item2), toGithubRepository(item1));
        callAndVerifyFetchPopularGithubRepositories(filter, pagingRequest, sourceItems, resultItems);
    }

    @Test
    void testFetchPopularGithubRepositoriesUsesStarsAndForksDescOrderWhenStarsCountSameButForksCountIsDifferent() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .language("Java")
                .createdFrom(LocalDate.now())
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        final GithubInternalRepository item1 = createGithubInternalRepository(2L, 1L, LocalDateTime.now());
        final GithubInternalRepository item2 = createGithubInternalRepository(2L, 2L, LocalDateTime.now());
        final List<GithubInternalRepository> sourceItems = List.of(item1, item2);
        final List<GithubRepository> resultItems = List.of(toGithubRepository(item2), toGithubRepository(item1));
        callAndVerifyFetchPopularGithubRepositories(filter, pagingRequest, sourceItems, resultItems);
    }

    @Test
    void testFetchPopularGithubRepositoriesUsesStarsAndForksAndUpdatedDescOrderWhenStarsCountAndForksCountSameButUpdatedIsDifferent() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .language("Java")
                .createdFrom(LocalDate.now())
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        final GithubInternalRepository item1 = createGithubInternalRepository(2L, 2L, LocalDateTime.now().minusDays(1));
        final GithubInternalRepository item2 = createGithubInternalRepository(2L, 2L, LocalDateTime.now());
        final List<GithubInternalRepository> sourceItems = List.of(item1, item2);
        final List<GithubRepository> resultItems = List.of(toGithubRepository(item2), toGithubRepository(item1));
        callAndVerifyFetchPopularGithubRepositories(filter, pagingRequest, sourceItems, resultItems);
    }

    private void callAndVerifyFetchPopularGithubRepositories(
            final GithubRepositoryFilter filter,
            final PagingRequest pagingRequest,
            final List<GithubInternalRepository> sourceItems,
            final List<GithubRepository> resultItems) {
        final GithubApiPageResponse<GithubInternalRepository> sourcePage = GithubApiPageResponse.<GithubInternalRepository>builder()
                .incompleteResults(true)
                .totalCount(10)
                .items(sourceItems)
                .build();
        final String queryQualifier = String.join(" ", QueryQualifier.LANGUAGE.getValue() + filter.language(), QueryQualifier.CREATED_FROM.getValue() + filter.createdFrom().toString());
        when(githubRepositoryClient.fetchRepositories(
                queryQualifier,
                Sort.STARS.getValue(),
                Order.DESC.getValue(),
                pagingRequest.getPage(),
                pagingRequest.getSize())).thenReturn(sourcePage);
        final PageResponse<GithubRepository> page = service.fetchPopularGithubRepositories(filter, pagingRequest);
        assertThat(page).isNotNull();
        assertThat(page.getPaging()).isNotNull();
        assertThat(page.getContent()).isNotEmpty().hasSize(sourceItems.size());
        assertThat(page.getContent()).isEqualTo(resultItems);
    }

    @Test
    void testFetchPopularGithubRepositoriesFailWhenFilterIsEmpty() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .language("Java")
                .createdFrom(LocalDate.now())
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        final String queryQualifier = String.join(" ", QueryQualifier.LANGUAGE.getValue() + filter.language(), QueryQualifier.CREATED_FROM.getValue() + filter.createdFrom().toString());
        when(githubRepositoryClient.fetchRepositories(
                queryQualifier,
                Sort.STARS.getValue(),
                Order.DESC.getValue(),
                pagingRequest.getPage(),
                pagingRequest.getSize())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> service.fetchPopularGithubRepositories(filter, pagingRequest));
    }

    @Test
    void testFetchPopularGithubRepositoriesFailWhenGithubClientThrowsException() {
        final GithubRepositoryFilter filter = GithubRepositoryFilter.builder()
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.fetchPopularGithubRepositories(filter, pagingRequest));
    }

    private GithubRepository toGithubRepository(final GithubInternalRepository source) {
        if (source == null) {
            return null;
        }
        return GithubRepository.builder()
                .url(source.htmlUrl())
                .starsCount(source.stargazersCount())
                .forksCount(source.forksCount())
                .owner(source.owner() != null ? source.owner().login() : null)
                .name(source.name())
                .fullName(source.fullName())
                .createdAt(source.createdAt())
                .updatedAt(source.updatedAt())
                .description(source.description())
                .id(source.id())
                .language(source.language())
                .build();
    }

    private static GithubInternalRepository createGithubInternalRepository(long stargazersCount, long forksCount, LocalDateTime updatedAt) {
        return GithubInternalRepository.builder()
                .id(1L)
                .owner(RepositoryOwner.builder().login("login").avatarUrl("avatarUrl").id(1L).build())
                .name("name")
                .fullName("full name")
                .createdAt(LocalDateTime.now())
                .updatedAt(updatedAt)
                .description("description")
                .forksCount(forksCount)
                .stargazersCount(stargazersCount)
                .htmlUrl("htmlUrl")
                .build();
    }

}
