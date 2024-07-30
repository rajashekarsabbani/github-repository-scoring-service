package com.redcare.pharmacy.github_repository_scoring_service.utests.converter;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.converter.GithubRepositoryConverter;
import com.redcare.pharmacy.github_repository_scoring_service.converter.GithubRepositoryConverterImpl;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubInternalRepository;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.RepositoryOwner;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GithubRepositoryConverterTest {

    private final GithubRepositoryConverter converter = new GithubRepositoryConverterImpl();

    @Test
    void testConvert() {
        final GithubInternalRepository source = GithubInternalRepository.builder()
                .id(1L)
                .owner(RepositoryOwner.builder().login("login").avatarUrl("avatarUrl").id(1L).build())
                .name("name")
                .fullName("full name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .description("description")
                .forksCount(1L)
                .stargazersCount(1L)
                .htmlUrl("htmlUrl")
                .build();
        final GithubRepository result = converter.convert(source);
        assertNotNull(result);
        assertThat(result.id()).isEqualTo(source.id());
        assertThat(result.owner()).isEqualTo(source.owner().login());
        assertThat(result.name()).isEqualTo(source.name());
        assertThat(result.fullName()).isEqualTo(source.fullName());
        assertThat(result.createdAt()).isEqualTo(source.createdAt());
        assertThat(result.updatedAt()).isEqualTo(source.updatedAt());
        assertThat(result.description()).isEqualTo(source.description());
        assertThat(result.forksCount()).isEqualTo(source.forksCount());
        assertThat(result.starsCount()).isEqualTo(source.stargazersCount());
        assertThat(result.url()).isEqualTo(source.htmlUrl());
    }

    @Test
    void testConvertWhenOwnerIsNull() {
        final GithubInternalRepository source = GithubInternalRepository.builder()
                .id(1L)
                .owner(null)
                .name("name")
                .fullName("full name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .description("description")
                .forksCount(1L)
                .stargazersCount(1L)
                .htmlUrl("htmlUrl")
                .build();
        final GithubRepository result = converter.convert(source);
        assertNotNull(result);
        assertThat(result.id()).isEqualTo(source.id());
        assertThat(result.owner()).isNull();
        assertThat(result.name()).isEqualTo(source.name());
        assertThat(result.fullName()).isEqualTo(source.fullName());
        assertThat(result.createdAt()).isEqualTo(source.createdAt());
        assertThat(result.updatedAt()).isEqualTo(source.updatedAt());
        assertThat(result.description()).isEqualTo(source.description());
        assertThat(result.forksCount()).isEqualTo(source.forksCount());
        assertThat(result.starsCount()).isEqualTo(source.stargazersCount());
        assertThat(result.url()).isEqualTo(source.htmlUrl());
    }

    @Test
    void testConvertEmpty() {
        final GithubInternalRepository source = GithubInternalRepository.builder()
                .build();
        final GithubRepository result = converter.convert(source);
        assertNotNull(result);
        assertThat(result.id()).isEqualTo(source.id());
        assertThat(result.owner()).isNull();
        assertThat(result.name()).isEqualTo(source.name());
        assertThat(result.fullName()).isEqualTo(source.fullName());
        assertThat(result.createdAt()).isEqualTo(source.createdAt());
        assertThat(result.updatedAt()).isEqualTo(source.updatedAt());
        assertThat(result.description()).isEqualTo(source.description());
        assertThat(result.forksCount()).isEqualTo(source.forksCount());
        assertThat(result.starsCount()).isEqualTo(source.stargazersCount());
        assertThat(result.url()).isEqualTo(source.htmlUrl());
    }
}
