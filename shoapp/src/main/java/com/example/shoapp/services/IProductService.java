package com.example.shoapp.services;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.shoapp.dtos.ProductDTO;
import com.example.shoapp.dtos.ProductImageDTO;
import com.example.shoapp.exceptions.DataNotFoundException;
import com.example.shoapp.models.Product;
import com.example.shoapp.models.ProductImage;
import com.example.shoapp.responses.ProductResponse;

@Service
public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(long id) throws Exception;

    Page<ProductResponse> getAllProducts(PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;
}
