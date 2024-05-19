package com.example.product;

import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductApplicationTests {
	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void getData(){
		Optional<Product> opt_product = productRepository.findById(1l);
		System.out.println(opt_product.isPresent());
		Product product = opt_product.get();
		System.out.println(product.getName() + " "+product.getPrice());
	}

	@Test
	public void getListData(){
		List<Product> productList = productRepository.findTop10DistinctProductsByName("IPhone");
		for(Product product:productList){
			System.out.println(product.getId()+" "+product.getPrice());
		}
	}

}
