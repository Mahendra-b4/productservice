package com.example.product.services;

import com.example.product.dtos.ProductRequestDto;
import com.example.product.dtos.ProductResponseDto;
import com.example.product.exceptions.InvalidProductIdException;
import com.example.product.models.Category;
import com.example.product.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements IProductService{

    @Autowired
    RestTemplate restTemplate;

    public Product getProductFromResponseDto(ProductResponseDto responseDto){
        Product product = new Product();
        product.setId(responseDto.getId());
        product.setName(responseDto.getTitle());
        product.setPrice(responseDto.getPrice());
        product.setDescription(responseDto.getDescription());
        product.setImage(responseDto.getImage());

        Category category = new Category();
        category.setName(responseDto.getCategory());

        product.setCategory(category);
        return product;
    }

    @Override
    public Product getSingleProduct(Long id) throws InvalidProductIdException {

        // I should pass this 'id' to fakestore and get the details of this product.
        // "https://fakestoreapi.com/products/1"

        if(id > 20){
            throw new InvalidProductIdException();
        }

        ProductResponseDto response = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                ProductResponseDto.class);

        return getProductFromResponseDto(response);
    }

    @Override
    public List<Product> getAllProducts() {
        ProductResponseDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products",
                ProductResponseDto[].class);
        List<Product> output = new ArrayList<>();
        for(ProductResponseDto productResponseDto:response){
            output.add(getProductFromResponseDto(productResponseDto));
        }
        return output;
    }

    @Override
    public Product addProduct(ProductRequestDto productRequestDto) {
        ProductResponseDto response = restTemplate.postForObject("https://fakestoreapi.com/products",
                productRequestDto, ProductResponseDto.class);
        return getProductFromResponseDto(response);
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDto productRequestDto) throws InvalidProductIdException {
        if(id > 20){
            throw new InvalidProductIdException();
        }

        RequestCallback requestCallback = restTemplate.httpEntityCallback(productRequestDto, ProductResponseDto.class);
        HttpMessageConverterExtractor<ProductResponseDto> responseExtractor =
                new HttpMessageConverterExtractor<>(ProductResponseDto.class, restTemplate.getMessageConverters());
        ProductResponseDto productResponseDto = restTemplate.execute("https://fakestoreapi.com/products" + id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return getProductFromResponseDto(productResponseDto);

        /*
        Here PUT method is returning void, but we need to return.
        making 2 API calls for the single request is not good.

        So, we went inside the GetForObject method and checked its working and took its implementation and changed it according to our usage

        Whenever if we don't have direct methods go and check deeply for the existing methods and find the suitable one for us
         */

//        RequestCallback requestCallback = restTemplate.httpEntityCallback(productRequestDto, ProductResponseDto.class);
//        HttpMessageConverterExtractor<ProductResponseDto> responseExtractor =
//                new HttpMessageConverterExtractor(ProductResponseDto.class, restTemplate.getMessageConverters());
//        ProductResponseDto responseDto = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT,
//                requestCallback, responseExtractor);
//
//        return getProductFromResponseDto(responseDto);


//        ProductResponseDto response = restTemplate.execute("https://fakestoreapi.com/products/"+id,
//                HttpMethod.PUT, ProductResponseDto.class, productRequestDto);

    }
    @Override
    public boolean deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return false;
    }


}
