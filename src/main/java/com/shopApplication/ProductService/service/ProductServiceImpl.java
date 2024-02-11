package com.shopApplication.ProductService.service;

import com.shopApplication.ProductService.dao.ProductRequest;
import com.shopApplication.ProductService.dao.ProductResponse;
import com.shopApplication.ProductService.entity.Product;
import com.shopApplication.ProductService.exception.ProductServiceCustomException;
import com.shopApplication.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product........");
        Product product=Product.builder()
                        .productName(productRequest.getName())
                        .price(productRequest.getPrice())
                        .quantity(productRequest.getQuantity()).build();
        productRepository.save(product);
        log.info("Product Added successfully completed......");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product by productId:"+productId);
        Optional<Product> byId = productRepository.findById(productId);
        Product product=byId.orElseThrow(()->new ProductServiceCustomException("Product not found with id no:"+productId,"PRODUCT_NOT_FOUND"));
//        return ProductResponse.builder()
//                .productId(product.getProductId())
//                .name(product.getProductName())
//                .price(product.getPrice())
//                .quantity(product.getQuantity())
//                .build();
        ProductResponse productResponse=new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity {} for id: {}",quantity,productId);
        Optional<Product> product = productRepository.findById(productId);
        Product product1= product.orElseThrow(()-> new ProductServiceCustomException("Product Not Found with id: "+productId, "PRODUCT_NOT_FOUND"));
        if(product1.getQuantity()<quantity){
            throw  new ProductServiceCustomException("Product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        product1.setQuantity(product1.getQuantity()-quantity);
        productRepository.save(product1);
        log.info("Product quantity updated successfully");
    }
}
