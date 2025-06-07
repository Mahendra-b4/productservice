package com.example.product.controllers;

import com.example.product.dtos.ErrorResponseDto;
import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductWrapper;
import com.example.product.models.Product;
import com.example.product.services.IProductService;
import com.example.product.exceptions.InvalidProductIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    @Qualifier("selfProductService")
    private IProductService productService;

    // Get all the products
    @GetMapping("/products")
    public List<Product> getAllProducts(){
        System.out.println("Hi in Controller");
        return productService.getAllProducts();
    }

    // Get a product with id
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductWrapper> getSingleProduct(@PathVariable("id") Long id) throws InvalidProductIdException {
        ResponseEntity<ProductWrapper> response;
//        try {
            Product newProduct = productService.getSingleProduct(id);
            ProductWrapper productWrapper = new ProductWrapper(newProduct, "Successfully retreved the data");
            response = new ResponseEntity<>(productWrapper, HttpStatus.OK);
//        } catch (InvalidProductIdException e) {
//            ProductWrapper productWrapper = new ProductWrapper(null, "Product is not present");
//            response = new ResponseEntity<>(productWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return response;
    }

//    @ExceptionHandler(InvalidProductIdException.class)
//    public ResponseEntity<ErrorResponseDto> handleInvalidProduct(){
//        return new ResponseEntity<>(new ErrorResponseDto("Invalid Product from Controller"), HttpStatus.NOT_FOUND);
//    }


    @GetMapping("/products/search/")
    public Page<Product> getProductsByName(@RequestParam("name") String name,
                                           @RequestParam("pagesize") int pageSize,
                                           @RequestParam("startingElementIndex") int st_index){
        return productService.getProductsByName(name, pageSize, st_index);
    }
    @PostMapping("/products")
    public Product addProduct(@RequestBody ProductRequestDto productRequestDto){
        return productService.addProduct(productRequestDto);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductWrapper> updateProduct(@PathVariable("id") Long id,
                                 @RequestBody ProductRequestDto productRequestDto){
        ResponseEntity<ProductWrapper> response;
        try {
            Product updatedProduct = productService.updateProduct(id, productRequestDto);
            ProductWrapper productWrapper = new ProductWrapper(updatedProduct, "Successfully Product updated");
            response = new ResponseEntity<>(productWrapper, HttpStatus.OK);
        } catch (InvalidProductIdException e) {
            ProductWrapper productWrapper = new ProductWrapper(null, "Product id not found");
            response = new ResponseEntity<>(productWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping("/products/{id}")
    public boolean deleteProduct(@PathVariable("id") Long id){
        return productService.deleteProduct(id);
    }

//
////    @Qualifier("IProductService")
//    @Autowired
//    private IProductService productService;
//
////    Get All Products
//    @GetMapping("/products")
//    public List<Product> getAllProducts(){
//        return new ArrayList<>();
//    }
//
//
//// Get a product with Id
//    @GetMapping("/products/{id}")
//    public Product getSingleProduct(@PathVariable("id") Long id){
//        return productService.getSingleProduct(id);
//    }
//
////    Add Product
//    @PostMapping("/products")
//    public Product addProduct(@RequestBody ProductRequestDto productRequestDto){
//        return new Product();
//    }
//
//    @PutMapping("/products/{id}")
//    public Product updateProduct(@PathVariable("id") Long id,
//                                 @RequestBody ProductRequestDto productRequestDto){
//        return new Product();
//    }
//
//    @DeleteMapping("/products/{id}")
//    public boolean deleteProduct(@PathVariable("id") Long id){
//        return true;
//    }
}
