package com.redcare.pharmacy.github_repository_scoring_service.common.dom;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Generic response structure for GET endpoints that return a page of data.
 *
 * @param <T> the element type of this page
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    @Schema(description = "Items in this page response")
    private List<T> content;
    @Schema(description = "Paging details of this page response")
    private PagingInfo paging;

    /**
     * Contains details about the page, such as the number of items in the page.
     */
    @Data
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class PagingInfo {

        @Schema(description = "Number of items in this response")
        private int itemCount;
        @Schema(description = "Total number of items for the given filters, if available")
        private long totalItemCount;
        @Schema(description = "Page size")
        private int size;
        @Schema(description = "Current page number, if available")
        private int page;
        @Schema(description = "Total number of pages for the given filters, if available")
        private int totalPageCount;

    }
}
