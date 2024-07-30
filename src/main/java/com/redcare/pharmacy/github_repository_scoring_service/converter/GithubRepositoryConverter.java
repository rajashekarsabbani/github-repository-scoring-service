package com.redcare.pharmacy.github_repository_scoring_service.converter;

import com.redcare.pharmacy.github_repository_scoring_service.api.dom.GithubRepository;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubInternalRepository;
import org.springframework.core.convert.converter.Converter;

/**
 * Converter interface for {@link GithubInternalRepository}/{@link GithubRepository} conversions
 */
public interface GithubRepositoryConverter extends Converter<GithubInternalRepository, GithubRepository> {
}
