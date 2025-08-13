package com.paytrack.billingsoftware.service;


import com.paytrack.billingsoftware.entity.CategoryEntity;
import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;
import com.paytrack.billingsoftware.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.paytrack.billingsoftware.repository.CategoryRepository.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private  CategoryRepository categoryRepository;

    //get all Categories
    @Override
    public List<CategoryResponse> read() {
       return categoryRepository.findAll()
        .stream()
        .map(categoryEntity -> convertToResponse(categoryEntity))
        .collect(Collectors.toList());
    }

    //delete categorie by id
//    @Override
//    public void delete(String categoryId) {
//      CategoryEntity existingCategory = CategoryRepository.findByCategoryId(categoryId)
//        .orElseThrow(() -> new RuntimeException("Category Not F"+categoryId));
//        categoryRepository.delete(existingCategory);
//    }


    @Override
    public void delete(String categoryId) {  // <-- No 'static' keyword
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found: " + categoryId));
        categoryRepository.delete(existingCategory);
    }


    public CategoryResponse add(CategoryRequest request) {
        CategoryEntity newCategory = convertToEntity(request);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
       return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imageUrl(newCategory.getImgUrl())
                .createdDate(newCategory.getCreatedDate())
                .updatedDate(newCategory.getUpdatedDate())
                .build();

    }

        //convert req obj to Entity Object
        private CategoryEntity convertToEntity(CategoryRequest request) {
            return CategoryEntity.builder()
                    .categoryId(UUID.randomUUID().toString())
                    .name(request.getName())
                    .description(request.getDescription())
                    .bgColor(request.getBgColor())
                    .build();
        }


}
