package com.example.Task.utils;

import com.example.Task.Dtos.Pagination.PaginationRequest;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
public class PaginationUtils {
    public static Pageable getPageable(PaginationRequest pr){
        //returns Pageable obj type
        return PageRequest.of(pr.getPage(),pr.getSize(), Sort.by(pr.getDirection(), pr.getSortField()));
    }
}
