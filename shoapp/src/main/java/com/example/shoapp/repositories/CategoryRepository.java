package com.example.shoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoapp.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
