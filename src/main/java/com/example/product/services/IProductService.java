package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    public Product getSingleProduct(Long id) throws InvalidProductIdException;

    List<Product> getAllProducts();

    Product addProduct(ProductRequestDto productRequestDto);

    Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductIdException;

    public boolean deleteProduct(Long id);
}
