package com.eccomerce.nb_eccomerce.service.impl;


import com.eccomerce.nb_eccomerce.dto.request.ProductRequest;
import com.eccomerce.nb_eccomerce.dto.request.ProductUpdateRequest;
import com.eccomerce.nb_eccomerce.dto.response.ProductResponse;
import com.eccomerce.nb_eccomerce.entity.Category;
import com.eccomerce.nb_eccomerce.entity.Product;
import com.eccomerce.nb_eccomerce.entity.User;
import com.eccomerce.nb_eccomerce.enums.ProductStatus;
import com.eccomerce.nb_eccomerce.exception.DuplicateResourceException;
import com.eccomerce.nb_eccomerce.exception.ResourceNotFoundException;
import com.eccomerce.nb_eccomerce.mapper.ProductMapper;
import com.eccomerce.nb_eccomerce.repository.ProductRepository;
import com.eccomerce.nb_eccomerce.repository.UserRepository;
import com.eccomerce.nb_eccomerce.service.helper.CategoryHelper;
import com.eccomerce.nb_eccomerce.service.helper.CommonHelper;
import com.eccomerce.nb_eccomerce.service.helper.ProductHelper;
import com.eccomerce.nb_eccomerce.service.helper.UserHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private CommonHelper commonHelper;

    @Mock
    private ProductHelper productHelper;

    @Mock
    private UserHelper userHelper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryHelper categoryHelper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_ShouldCreateProductSuccessfully(){


        //===================================
        //Arrange
        //===================================
        ProductRequest request=new ProductRequest();
        request.setProductName(" Wireless Headphones ");
        request.setPrice(new BigDecimal("6000.00"));
        request.setStock(20);
        request.setCategoryId(1L);

        String normalizedName="Wireless Headphones";
        Long userId= 1L;

        User seller=new User();
        seller.setUserId(userId);

        Product product=new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        Product savedProduct=new Product();

        Category category=new Category();
        category.setCategoryId(1L);

        ProductResponse expectedResponse=new ProductResponse(
                1L,
                "Wireless Headphones",
                new BigDecimal("6000.00"),
                20,
                ProductStatus.ACTIVE,
                1L,
                "Electronics"
        );

        when(commonHelper.normalize(request.getProductName())).thenReturn(normalizedName);

        when(userHelper.getAuthenticatedUserId()).thenReturn(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(seller));

        when(productMapper.toEntity(request)).thenReturn(product);

        when(categoryHelper.getCategoryOrThrow(request.getCategoryId())).thenReturn(category);

        when(productRepository.save(product)).thenReturn(savedProduct);

        when(productMapper.toResponse(savedProduct)).thenReturn(expectedResponse);

        //=============================
        //Act
        //=============================
        ProductResponse actualResponse=productService.createProduct(request);

        //=============================
        //Assert
        //=============================
        assertNotNull(actualResponse);
        assertSame(expectedResponse,actualResponse);
        assertEquals(normalizedName,product.getProductName());
        assertEquals(request.getPrice(),product.getPrice());
        assertEquals(request.getStock(),product.getStock());
        assertSame(seller,product.getSeller());
        assertSame(category,product.getCategory());

        //=============================
        //Verify
        //=============================
        verify(commonHelper).normalize(request.getProductName());
        verify(productHelper).validateDuplicateName(normalizedName,null);
        verify(userHelper).getAuthenticatedUserId();
        verify(userRepository).findById(userId);
        verify(productMapper).toEntity(request);
        verify(categoryHelper).getCategoryOrThrow(request.getCategoryId());
        verify(productRepository).save(product);
        verify(productMapper).toResponse(savedProduct);
    }

    @Test
    void createProduct_shouldThrowException_whenSellerNotFound() {
        ProductRequest request = new ProductRequest();
        request.setProductName("Wireless Headphones");

        String normalizedName = "Wireless Headphones";
        Long userId = 1L;

        when(commonHelper.normalize(request.getProductName())).thenReturn(normalizedName);
        when(userHelper.getAuthenticatedUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //Act + assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.createProduct(request));

        assertEquals("User not found with userId: 1", exception.getMessage());

        //verify
        verify(userHelper).getAuthenticatedUserId();
        verify(userRepository).findById(userId);
        verify(productRepository,never()).save(any());
    }

    @Test
    void createProduct_shouldThrowException_whenDuplicateName(){
        ProductRequest request=new ProductRequest();
        request.setProductName("Wireless Headphones");

        String normalizedName="Wireless Headphones";

        when(commonHelper.normalize(request.getProductName())).thenReturn(normalizedName);

        doThrow(new DuplicateResourceException(
                "Product already exists with productName: " + normalizedName
        )).when(productHelper).validateDuplicateName(normalizedName,null);

        //Assert + act
        DuplicateResourceException exception=assertThrows(DuplicateResourceException.class,()-> productService.createProduct(request));

        assertNotNull(exception);

        //verify
        verify(commonHelper).normalize(request.getProductName());
        verify(productHelper).validateDuplicateName(normalizedName, null);

        verify(productRepository, never()).save(any());

    }

    @Test
    void createProduct_shouldThrowException_whenCategoryNotFound(){
         ProductRequest request=new ProductRequest();
         request.setProductName("Wireless Headphones");
         request.setCategoryId(1L);

         String normalizedName="Wireless Headphones";
         Long userId=5L;

         User seller=new User();
         seller.setUserId(userId);

         Product product=new Product();

         when(commonHelper.normalize(request.getProductName())).thenReturn(normalizedName);
         when(userHelper.getAuthenticatedUserId()).thenReturn(userId);
         when(userRepository.findById(userId)).thenReturn(Optional.of(seller));
         when(productMapper.toEntity(request)).thenReturn(product);

         doThrow(new ResourceNotFoundException("Category","categoryId",request.getCategoryId())).when(categoryHelper).getCategoryOrThrow(request.getCategoryId());

         //Act + Assert
        ResourceNotFoundException exception=assertThrows(ResourceNotFoundException.class,()->productService.createProduct(request));

        assertEquals(
                "Category not found with categoryId: 1",
                exception.getMessage()
        );

        //verify
        verify(categoryHelper).getCategoryOrThrow(request.getCategoryId());
        verify(productRepository,never()).save(any());
    }

    @Test
    void updateProductById_shouldUpdateProductSuccessfully(){
        //Arrange
        ProductUpdateRequest request=new ProductUpdateRequest();
        request.setProductName("Wireless Headphones");
        request.setPrice(new BigDecimal("6000.00"));
        request.setStock(20);
        request.setCategoryId(1L);

        Long productId=1L;
        Long userId=5L;

        User user=new User();
        user.setUserId(userId);

    }
}
