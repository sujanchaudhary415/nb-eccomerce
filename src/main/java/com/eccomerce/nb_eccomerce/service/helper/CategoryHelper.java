package com.eccomerce.nb_eccomerce.service.helper;

import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.exception.DuplicateResourceException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryHelper {

    private final CategoryRepository categoryRepository;

    public void validateDuplicateName (String name, Long excludeId)
    {
       boolean exists=(excludeId==null)
                       ?categoryRepository.existsByCategoryNameIgnoreCase(name)
                       :categoryRepository.existsByCategoryNameIgnoreCaseAndCategoryIdNot(name,excludeId);

       if(exists)
       {
           throw new DuplicateResourceException("Category already Exist");
       }
    }

   public Category getCategoryOrThrow (Long categoryId){
     return  categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
   }
}
