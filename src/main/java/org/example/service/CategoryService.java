package org.example.service;

import org.example.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryDto newCategory);

    void updateCategoryName(String category_name, String values);

    List<CategoryDto> getCategories();

    void deleteCategory(String category_name);
}
