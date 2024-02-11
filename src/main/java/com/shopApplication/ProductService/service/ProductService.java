package com.shopApplication.ProductService.service;

import com.shopApplication.ProductService.dao.ProductRequest;
import com.shopApplication.ProductService.dao.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
