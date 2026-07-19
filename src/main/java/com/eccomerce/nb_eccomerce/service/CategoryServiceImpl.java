package com.eccomerce.nb_eccomerce.service;

import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.repositories.CategoryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

   // private final List<Category> categories=new ArrayList<>();
    @Autowired
    private CategoryRespository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category>categories=categoryRepository.findAll();
        Category category=categories.stream()
                        .filter(c->c.getCategoryId().equals(categoryId))
                                .findFirst().get();
        categoryRepository.delete(category);
        return "deleted successfully";
    }
}
