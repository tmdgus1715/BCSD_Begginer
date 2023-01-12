package org.example.repository;

import org.example.domain.Category;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CategoryMapper {
    public void createCategory(Category newCategory);

    public Category getCategory(Integer id);

    Category getCategoryByName(String categoryName);

    ArrayList<Category> getCategories();

    void updateCategoryName(UpdateParameterDto categoryUpdateParameterDto);

    void deleteCategory(Integer id);
}
