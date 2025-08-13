package com.paytrack.billingsoftware.io;

import lombok.Builder;
import lombok.Data;

import java.security.Timestamp;
import java.time.LocalDateTime;


@Builder
@Data
public class CategoryResponse {

    private String categoryId;
    private String name;
    private String description;
    private String bgColor;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String imageUrl;
}
