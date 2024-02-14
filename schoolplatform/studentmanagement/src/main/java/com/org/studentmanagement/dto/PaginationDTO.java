package com.org.studentmanagement.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationDTO {
    private int pageNumber = 1;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
