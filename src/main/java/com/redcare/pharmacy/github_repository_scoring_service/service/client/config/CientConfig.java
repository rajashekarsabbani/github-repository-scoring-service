package com.redcare.pharmacy.github_repository_scoring_service.service.client.config;

import com.redcare.pharmacy.github_repository_scoring_service.service.client.GithubRepositoryClient;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

/**
 * Client configuration for the http communication using {@link WebClient}.
 */
@Configuration
public class CientConfig {

    @Bean
    GithubRepositoryClient githubRepositoryClient(
            @Value("${com.redcare.pharmacy.github.repository.client.base-url}") final String baseUrl,
            @Value("${com.redcare.pharmacy.github.repository.client.api-version}") final String githubApiVersion,
            @Value("${com.redcare.pharmacy.github.repository.client.connection-timeout-millis}") final int connectionTimeoutMillis,
            @Value("${com.redcare.pharmacy.github.repository.client.read-timeout-millis}") final int readTimeoutMillis
    ) {
        final HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeoutMillis)
                .responseTimeout(Duration.ofMillis(readTimeoutMillis));
        final WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-GitHub-Api-Version", githubApiVersion)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                // http response buffer size configuration 5MB
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))
                .build();
        final HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return factory.createClient(GithubRepositoryClient.class);
    }
}
