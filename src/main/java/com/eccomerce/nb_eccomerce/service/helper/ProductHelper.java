package com.eccomerce.nb_eccomerce.service.helper;

import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.exception.DuplicateResourceException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductHelper {
    private final ProductRepository productRepository;


    public void validateDuplicateName(String name,Long excludeId){
      boolean exists=(excludeId==null)
                      ? productRepository.existsByProductNameIgnoreCase(name)
                      : productRepository.existsByProductNameIgnoreCaseAndProductIdNot(name,excludeId);

      if(exists){
       throw new DuplicateResourceException("Product name already exist");
      }
  }

    public Product getProductOrThrow(Long productId){
       return productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));
    }

  }
