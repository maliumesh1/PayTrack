package com.paytrack.billingsoftware.controller;


import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;

import com.paytrack.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.add(categoryRequest);
    }


    @GetMapping
    public List<CategoryResponse> fetchCategories() {

        return categoryService.read();
    }

//    @DeleteMapping("/{categoryId}")
//    public void remove(@PathVariable String categoryId)
//    {
//        try{
//            categoryService.delete(categoryId);
//        }catch(Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"+ e.getMessage());
//        }
//    }


    @DeleteMapping("/{categoryId}") // NOT "/categories/{categoryId}"
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }



}
