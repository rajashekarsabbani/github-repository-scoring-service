package com.redcare.pharmacy.github_repository_scoring_service.service.client.dom;

import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * GitHub query search parameter builder.
 * Prepares the query qualifier with the name value pairs and joins them using ' ' delimiter.
 */
public class GithubSearchParameterBuilder {

    private final List<String> queryQualifiers = new ArrayList<>();

    public static GithubSearchParameterBuilder builder() {
        return new GithubSearchParameterBuilder();
    }

    /**
     * Adds the query qualifier parameter with given name and value pair.
     *
     * @param name name of the qualifier
     * @param value value of the qualifier
     * @return {@link GithubSearchParameterBuilder}
     */
    public GithubSearchParameterBuilder add(final String name, final Object value) {
        if (StringUtils.isNotBlank(name) && value != null) {
            queryQualifiers.add(name + value);
        }
        return this;
    }

    public String build() {
        return queryQualifiers.isEmpty() ? "" : String.join(" ", queryQualifiers);
    }
}
