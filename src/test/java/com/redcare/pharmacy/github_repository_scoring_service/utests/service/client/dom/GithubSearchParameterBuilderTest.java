package com.redcare.pharmacy.github_repository_scoring_service.utests.service.client.dom;

import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubSearchParameterBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GithubSearchParameterBuilderTest {

    private final GithubSearchParameterBuilder service = new GithubSearchParameterBuilder();

    @Test
    void testAddWithKeyAndValueNull() {
        service.add(null, null);
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyAndValueEmpty() {
        service.add("", "");
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyNullAndValueEmpty() {
        service.add(null, "");
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyNullAndValueBlank() {
        service.add(null, " ");
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyEmptyAndValueNull() {
        service.add("", null);
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyBlankAndValueNull() {
        service.add(" ", null);
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyBlankAndValueBlank() {
        service.add(" ", " ");
        assertThat(service.build()).isEmpty();
    }

    @Test
    void testAddWithKeyAndValue() {
        service.add("key:", "value");
        assertThat(service.build()).isEqualTo("key:value");
    }

}
