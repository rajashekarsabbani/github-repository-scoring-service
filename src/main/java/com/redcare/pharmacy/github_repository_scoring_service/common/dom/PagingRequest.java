package com.redcare.pharmacy.github_repository_scoring_service.common.dom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic class for paging-requests with page and size.
 * Page represents the number of the paginated results.
 * Size represents the number of results per page.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagingRequest {

    @Schema(defaultValue = "1", description = "Page number of the results, defaults to 1, max " + Integer.MAX_VALUE)
    @Builder.Default
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private int page = 1;

    @Schema(defaultValue = "10", description = "Page size is the number of results per page, defaults to 10, max 100")
    @Builder.Default
    @Max(100)
    private int size = 10;
}
