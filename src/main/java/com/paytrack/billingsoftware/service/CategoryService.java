package com.paytrack.billingsoftware.service;

import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;

import java.util.List;

public interface CategoryService {

    //Adding Category
    CategoryResponse add(CategoryRequest categoryRequest);

    //get all categories
    List<CategoryResponse> read ();


    //delete categorie by id
    void delete (String categoryId);

}
