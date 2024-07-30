package com.redcare.pharmacy.github_repository_scoring_service.common.util;

import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PageResponse;
import com.redcare.pharmacy.github_repository_scoring_service.common.dom.PagingRequest;
import com.redcare.pharmacy.github_repository_scoring_service.service.client.dom.GithubApiPageResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Helper class for the paginated results conversion.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PagingHelper {

    /**
     * Converts the {@link GithubApiPageResponse} to {@link PageResponse} with converting the source type to target type using a mapper.
     * Returns the {@link GithubApiPageResponse} with paging and content.
     *
     * @param pageRequest Actual paging request with page and size.
     * @param page requested page of items to be converted.
     * @param comparator comparator to sort the source items list
     * @param mapper mapper function to convert the source type to a target type.
     * @return the {@link GithubApiPageResponse} with paging and content.
     * @param <T> Target element type
     * @param <S> Source element type
     */
    public static <T, S> PageResponse<T> toPageResonse(
            @NotNull final PagingRequest pageRequest,
            @NotNull final GithubApiPageResponse<S> page,
            @NotNull final Comparator<S> comparator,
            @NotNull final Function<S, T> mapper
    ) {
        return PageResponse.<T>builder()
                .content(page.items().stream().sorted(comparator).map(mapper).toList())
                .paging(PageResponse.PagingInfo.builder()
                        .page(pageRequest.getPage())
                        .size(pageRequest.getSize())
                        .itemCount(page.items().size())
                        .totalItemCount(page.totalCount())
                        .totalPageCount((int) Math.ceil((double) page.totalCount() / pageRequest.getSize()))
                        .build())
                .build();
    }

}
