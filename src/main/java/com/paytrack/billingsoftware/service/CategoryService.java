package com.paytrack.billingsoftware.service;

import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    //Adding Category
    CategoryResponse add(CategoryRequest categoryRequest, MultipartFile file);

    //get all categories
    List<CategoryResponse> read ();


    //delete categorie by id
    void delete (String categoryId);

}
