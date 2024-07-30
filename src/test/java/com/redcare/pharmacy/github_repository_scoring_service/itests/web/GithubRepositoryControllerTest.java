package com.redcare.pharmacy.github_repository_scoring_service.itests.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepositoryFilter;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;
import com.redcare.pharmacy.github_repository_scoring_service.itests.BaseIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GithubRepositoryControllerTest extends BaseIntegrationTest {

    private static final String GITHUB_REPOSITORY_PATH = "/v1/github/repositories";
    private static final String GITHUB_MEDIA_TYPE = "application/vnd.github+json";
    private static final int TOTAL_ITEMS = 60;

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccess() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=language%3AJava%20created%3A%3E%3D2024-07-26&sort=stars&order=desc&page=1&per_page=2",
                "/github-jsons/search-repositories-success-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositories(githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        final List<GithubRepository> items = getGithubRepositoryList();
        assertThat(pageResponse.getContent()).isNotEmpty().hasSize(items.size());
        assertThat(pageResponse.getContent()).isEqualTo(items);
        assertThat(pageResponse.getPaging()).isEqualTo(getPagingInfo(pagingRequest, items.size()));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccessWhenCreatedFromIsNull() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=language%3AJava&sort=stars&order=desc&page=1&per_page=2",
                "/github-jsons/search-repositories-success-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositoriesWithoutCreatedFrom(githubRepositoryFilter.language(), pagingRequest.getPage(), pagingRequest.getSize());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        final List<GithubRepository> items = getGithubRepositoryList();
        assertThat(pageResponse.getContent()).isNotEmpty().hasSize(items.size());
        assertThat(pageResponse.getContent()).isEqualTo(items);
        assertThat(pageResponse.getPaging()).isEqualTo(getPagingInfo(pagingRequest, items.size()));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccessWhenLanguageIsNull() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=created%3A%3E%3D2024-07-26&sort=stars&order=desc&page=1&per_page=2",
                "/github-jsons/search-repositories-success-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositoriesWithoutLanguage(githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        final List<GithubRepository> items = getGithubRepositoryList();
        assertThat(pageResponse.getContent()).isNotEmpty().hasSize(items.size());
        assertThat(pageResponse.getContent()).isEqualTo(items);
        assertThat(pageResponse.getPaging()).isEqualTo(getPagingInfo(pagingRequest, items.size()));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccessWhenPageIsNull() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .size(2)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=language%3AJava%20created%3A%3E%3D2024-07-26&sort=stars&order=desc&page=1&per_page=2",
                "/github-jsons/search-repositories-success-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositoriesWithoutPage(githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getSize());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        final List<GithubRepository> items = getGithubRepositoryList();
        assertThat(pageResponse.getContent()).isNotEmpty().hasSize(items.size());
        assertThat(pageResponse.getContent()).isEqualTo(items);
        pagingRequest.setPage(1);
        assertThat(pageResponse.getPaging()).isEqualTo(getPagingInfo(pagingRequest, items.size()));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccessWhenPageSizeIsNull() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=language%3AJava%20created%3A%3E%3D2024-07-26&sort=stars&order=desc&page=1&per_page=10",
                "/github-jsons/search-repositories-success-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositoriesWithoutPageSize(githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        final List<GithubRepository> items = getGithubRepositoryList();
        assertThat(pageResponse.getContent()).isNotEmpty().hasSize(items.size());
        assertThat(pageResponse.getContent()).isEqualTo(items);
        pagingRequest.setSize(10);
        assertThat(pageResponse.getPaging()).isEqualTo(getPagingInfo(pagingRequest, items.size()));
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenPageIsInvalid() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(-1)
                .size(2)
                .build();
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}&size={s}",
                        githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenPageSizeIsInvalid() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(10000)
                .build();
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}&size={s}",
                        githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenCreatedFromIsInvalid() {
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(10000)
                .build();
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}&size={s}",
                        "Java", "2024-19-19", pagingRequest.getPage(), pagingRequest.getSize())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenSearchFiltersAreNotProvided() {
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular-repositories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenResourceNotFound() {
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    @SneakyThrows
    void testGetPopularRepositoriesThrowsErrorWhenGithubServiceNotAvailable() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        mockMvc.perform(get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}&size={s}",
                        githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = ROLE_USER)
    void testGetPopularRepositoriesSuccessWithEmptyItems() {
        final GithubRepositoryFilter githubRepositoryFilter = GithubRepositoryFilter.builder()
                .createdFrom(LocalDate.of(2024, 7, 26))
                .language("Java")
                .build();
        final PagingRequest pagingRequest = PagingRequest.builder()
                .page(1)
                .size(2)
                .build();
        mockGithubSearchRepositories(
                "/search/repositories?q=language%3AJava%20created%3A%3E%3D2024-07-26&sort=stars&order=desc&page=1&per_page=2",
                "/github-jsons/search-repositories-success-empty-items.json"
        );
        final PageResponse<GithubRepository> pageResponse = getPopularRepositories(githubRepositoryFilter.language(), githubRepositoryFilter.createdFrom(), pagingRequest.getPage(), pagingRequest.getSize());
        assertThat(pageResponse).isNotNull();
        assertThat(pageResponse.getPaging()).isNotNull();
        assertThat(pageResponse.getContent()).isEmpty();
        assertThat(pageResponse.getPaging()).isEqualTo(PageResponse.PagingInfo.builder()
                .page(pagingRequest.getPage())
                .size(pagingRequest.getSize())
                .itemCount(0)
                .totalItemCount(0)
                .totalPageCount(0)
                .build());
    }

    private void mockGithubSearchRepositories(final String url, final String fileName) {
        WM.stubFor(WireMock.get(url)
                .willReturn(WireMock.ok(getFileContentAsString(fileName))
                        .withHeader("accept", GITHUB_MEDIA_TYPE)
                        .withHeader("Content-Type", GITHUB_MEDIA_TYPE)));
    }

    private GithubRepository createGithubRepository(
            final long id,
            final String name,
            final String fullName,
            final String owner,
            final String url,
            final String language,
            final String description,
            final long forksCount,
            final long starsCount,
            final String createAt,
            final String updateAt
    ) {
        return GithubRepository.builder()
                .id(id)
                .name(name)
                .fullName(fullName)
                .owner(owner)
                .url(url)
                .language(language)
                .createdAt(LocalDateTime.parse(createAt))
                .updatedAt(LocalDateTime.parse(updateAt))
                .description(description)
                .forksCount(forksCount)
                .starsCount(starsCount)
                .build();
    }

    private PageResponse<GithubRepository> getPopularRepositories(final String language, final LocalDate createdFrom, final Integer page, final Integer size) {
        return getPopularRepositories(
                () -> get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}&size={s}",
                        language, createdFrom, page, size)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    private PageResponse<GithubRepository> getPopularRepositoriesWithoutPage(final String language, final LocalDate createdFrom, final Integer size) {
        return getPopularRepositories(
                () -> get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&size={s}",
                        language, createdFrom, size)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    private PageResponse<GithubRepository> getPopularRepositoriesWithoutPageSize(final String language, final LocalDate createdFrom, final Integer page) {
        return getPopularRepositories(
                () -> get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&createdFrom={c}&page={p}",
                        language, createdFrom, page)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    private PageResponse<GithubRepository> getPopularRepositoriesWithoutLanguage(final LocalDate createdFrom, final Integer page, final Integer size) {
        return getPopularRepositories(
                () -> get(GITHUB_REPOSITORY_PATH + "/popular-repositories?createdFrom={c}&page={p}&size={s}",
                        createdFrom, page, size)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    private PageResponse<GithubRepository> getPopularRepositoriesWithoutCreatedFrom(final String language, final Integer page, final Integer size) {
        return getPopularRepositories(
                () -> get(GITHUB_REPOSITORY_PATH + "/popular-repositories?language={l}&page={p}&size={s}",
                        language, page, size)
                        .accept(MediaType.APPLICATION_JSON));
    }

    @SneakyThrows
    private PageResponse<GithubRepository> getPopularRepositories(final Callable<RequestBuilder> call) {
        final MvcResult result = mockMvc.perform(call.call()).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        return objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
    }

    private List<GithubRepository> getGithubRepositoryList() {
        return List.of(
                createGithubRepository(834655314L, "hackathon-card-authentication-ms", "lamantovani/hackathon-card-authentication-ms", "lamantovani", "https://github.com/lamantovani/hackathon-card-authentication-ms", "Java", "Projeto do Hackathon da FIAP referente ao Mico ServiÃ§o para AutenticaÃ§Ã£o para Pagamentos com CartÃµes", 0L, 1L, "2024-07-26T00:12:19", "2024-07-26T00:21:32"),
                createGithubRepository(834655307L, "MemeUnitBot", "iSeitan/MemeUnitBot", "iSeitan", "https://github.com/iSeitan/MemeUnitBot", "Java", "MemeUnitBot", 0L, 1L, "2024-07-26T00:12:17", "2024-07-26T00:20:42")
        );
    }

    private static PageResponse.PagingInfo getPagingInfo(final PagingRequest pagingRequest, final int expectedItemCount) {
        return PageResponse.PagingInfo.builder()
                .page(pagingRequest.getPage())
                .size(pagingRequest.getSize())
                .itemCount(expectedItemCount)
                .totalItemCount(TOTAL_ITEMS)
                .totalPageCount(TOTAL_ITEMS / pagingRequest.getSize())
                .build();
    }
}
