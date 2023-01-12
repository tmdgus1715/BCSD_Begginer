package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domain.Category;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    public Category toEntity() {
        return new Category(this.id, this.name);
    }
}
