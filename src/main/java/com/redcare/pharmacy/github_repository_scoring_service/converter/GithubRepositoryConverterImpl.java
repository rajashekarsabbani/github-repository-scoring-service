package com.redcare.pharmacy.github_repository_scoring_service.converter;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubInternalRepository;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.RepositoryOwner;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of {@link GithubRepositoryConverter}
 */
@Component
public class GithubRepositoryConverterImpl implements GithubRepositoryConverter {

    @Override
    public GithubRepository convert(final @NotNull GithubInternalRepository source) {
        return GithubRepository.builder()
                .id(source.id())
                .createdAt(source.createdAt())
                .updatedAt(source.updatedAt())
                .description(source.description())
                .name(source.name())
                .language(source.language())
                .fullName(source.fullName())
                .starsCount(source.stargazersCount())
                .forksCount(source.forksCount())
                .url(source.htmlUrl())
                .owner(Optional.ofNullable(source.owner()).map(RepositoryOwner::login).orElse(null))
                .build();
    }
}
