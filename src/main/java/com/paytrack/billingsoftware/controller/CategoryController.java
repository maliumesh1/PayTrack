package com.paytrack.billingsoftware.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;

import com.paytrack.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file") MultipartFile file) {

        ObjectMapper mapper = new ObjectMapper();
        CategoryRequest request = null;

        try{
           request =  mapper.readValue(categoryString, CategoryRequest.class);
           return categoryService.add(request,file);
        }catch (JsonProcessingException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occured while parsing category JSON request"+e.getMessage());
        }
    }


    @GetMapping
    public List<CategoryResponse> fetchCategories() {
        return categoryService.read();
    }

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
