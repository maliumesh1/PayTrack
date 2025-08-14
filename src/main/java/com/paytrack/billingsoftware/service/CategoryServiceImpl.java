package com.paytrack.billingsoftware.service;


import com.paytrack.billingsoftware.entity.CategoryEntity;
import com.paytrack.billingsoftware.io.CategoryRequest;
import com.paytrack.billingsoftware.io.CategoryResponse;
import com.paytrack.billingsoftware.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.paytrack.billingsoftware.repository.CategoryRepository.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private  final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;




    //get all Categories
    @Override
    public List<CategoryResponse> read() {
       return categoryRepository.findAll()
        .stream()
        .map(categoryEntity -> convertToResponse(categoryEntity))
        .collect(Collectors.toList());
    }



    public CategoryResponse add(CategoryRequest request, MultipartFile file)
    {
        String imgUrl = fileUploadService.uploadFile(file);

        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public void delete(String categoryId) {  // <-- No 'static' keyword
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found: " + categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
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
