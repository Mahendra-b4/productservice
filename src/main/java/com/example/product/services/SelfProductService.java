package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import com.example.product.repositories.CategoryRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("selfProductService")
public class SelfProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException();
        }
        Product product = optionalProduct.get();

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product addProduct(ProductRequestDto productRequestDto) {
        Product addingProduct = getProductFromDto(productRequestDto);
        Optional<Category> optionalCategory = categoryRepository.findByName(productRequestDto.getCategory());
        if(optionalCategory.isEmpty()){
            addingProduct.setCategory(categoryRepository.save(addingProduct.getCategory()));
        }
        else{
            addingProduct.setCategory(optionalCategory.get());
        }

        return productRepository.save(addingProduct);
    }

    private Product getProductFromDto(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getTitle());
        product.setPrice(productRequestDto.getPrice());
        product.setImage(productRequestDto.getImage());
        product.setDescription(productRequestDto.getDescription());
        product.setCategory(new Category());
        product.getCategory().setName(productRequestDto.getCategory());

        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductIdException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new InvalidProductIdException();
        }
        Product existingProduct = optionalProduct.get();
        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName(
                productRequestDto.getTitle() != null ?
                        productRequestDto.getTitle() :
                        existingProduct.getName()
        );
        updatedProduct.setDescription(
                productRequestDto.getDescription() != null ?
                        productRequestDto.getDescription():
                        existingProduct.getDescription()
        );
        updatedProduct.setPrice(
                productRequestDto.getPrice() > 0 ?
                        productRequestDto.getPrice():
                        existingProduct.getPrice()
        );
        updatedProduct.setImage(
                productRequestDto.getImage() != null ?
                        productRequestDto.getImage() :
                        existingProduct.getImage()
        );
        Optional<Category> optionalCategory = categoryRepository.findByName(productRequestDto.getCategory());
        if(optionalCategory.isEmpty()){
            updatedProduct.setCategory(categoryRepository.save(existingProduct.getCategory()));
        }
        else{
            updatedProduct.setCategory(optionalCategory.get());
        }

        return productRepository.save(updatedProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }
}
