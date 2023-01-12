package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Category extends Entity {
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void checkValidation() {
        if (this == null) throw new NullPointerException("해당 카테고리는 존재하지 않습니다.");
    }
}
