package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CategoryDto;
import org.example.dto.requestDto.UpdateRequestDto;
import org.example.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/categories", produces = "application/json; charset=utf8")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryDto newCategory) throws URISyntaxException {
        categoryService.createCategory(newCategory);
        return ResponseEntity.created(new URI("http://localhost:8080/categories/list")).build();
    }

    @PatchMapping("/{category_Name}")
    public ResponseEntity<Object> updateCategory(@PathVariable String category_Name, @RequestBody UpdateRequestDto updateRequestDto) {
        switch (updateRequestDto.getKey()) {
            case "name":
                categoryService.updateCategoryName(category_Name, updateRequestDto.getValue());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categoryDtos = categoryService.getCategories();
        return ResponseEntity.ok(categoryDtos);
    }

    @DeleteMapping("/{category_Name}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String category_Name) {
        categoryService.deleteCategory(category_Name);
        return ResponseEntity.ok().build();
    }
}
