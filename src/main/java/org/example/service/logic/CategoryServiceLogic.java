package org.example.service.logic;

import lombok.RequiredArgsConstructor;
import org.example.domain.Category;
import org.example.dto.CategoryDto;
import org.example.dto.parameterDto.UpdateParameterDto;
import org.example.repository.CategoryMapper;
import org.example.service.CategoryService;
import org.example.service.CheckValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryServiceLogic implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CheckValidationService check;

    @Override
    public void createCategory(CategoryDto newCategoryDto) {
        check.checkValidationCategory(newCategoryDto);

        Category newCategory = newCategoryDto.toEntity();

        categoryMapper.createCategory(newCategory);
    }

    @Override
    public void updateCategoryName(String categoryName, String newName) {
        check.checkValidationCategoryName(newName);

        Category category = categoryMapper.getCategoryByName(categoryName);
        category.checkValidation();

        UpdateParameterDto categoryUpdateParameterDto = UpdateParameterDto.builder().id(category.getId()).value(newName).build();

        categoryMapper.updateCategoryName(categoryUpdateParameterDto);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> list = categoryMapper.getCategories();
        List<CategoryDto> categories = new ArrayList<>();//
        for (int i = 0; i < list.size(); ++i) {
            categories.add(i, new CategoryDto(list.get(i)));//
        }

        return categories;
    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryMapper.getCategoryByName(categoryName);
        category.checkValidation();

        categoryMapper.deleteCategory(category.getId());
    }
}
