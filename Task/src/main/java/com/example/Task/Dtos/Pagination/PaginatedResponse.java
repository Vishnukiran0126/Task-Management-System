package com.example.Task.Dtos.Pagination;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedResponse<T> {
    private List<T> content; //list of dtos
    private Integer size;
    private Integer page;
    private Integer totalPages;
    private Long totalElements;
}
