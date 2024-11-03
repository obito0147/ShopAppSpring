package com.example.shoapp.services;

import java.util.List;

import com.example.shoapp.dtos.CategoryDTO;
import com.example.shoapp.models.Category;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    Category getCategory(long id);

    List<Category> getAllCategories();

    Category updateCategory(long id, CategoryDTO categoryDTO);

    void deleteCategory(long id);
}
