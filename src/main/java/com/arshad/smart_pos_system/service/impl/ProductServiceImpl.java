package com.arshad.smart_pos_system.service.impl;

import com.arshad.smart_pos_system.dto.ProductDto;
import com.arshad.smart_pos_system.mapper.ProductMapper;
import com.arshad.smart_pos_system.model.Product;
import com.arshad.smart_pos_system.model.Store;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.repository.ProductRepository;
import com.arshad.smart_pos_system.repository.StoreRepository;
import com.arshad.smart_pos_system.repository.UserRepository;
import com.arshad.smart_pos_system.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, User user)
    {
        Store store = storeRepository.findById(productDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Product product = ProductMapper.productDtoToEntity(productDto, store);

        Product savedProduct = productRepository.save(product);

        return ProductMapper.productToProductDto(savedProduct);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id, User user)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Authorization check
        if(!product.getStore().getStoreAdmin().getId().equals(user.getId()))
        {
            throw new RuntimeException("Unauthorized");
        }

        ProductMapper.updateProductFromDto(productDto, product);

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.productToProductDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> findAllProducts()
    {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products)
        {
            productDtos.add(ProductMapper.productToProductDto(product));
        }

        return productDtos;
    }

    @Override
    public Product findProductById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductDto> searchByKeyword(Long storeId, String keyword)
    {
        List<Product> products = productRepository.searchByKeyword(storeId, keyword);
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products)
        {
            productDtos.add(ProductMapper.productToProductDto(product));
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> findAllProductsByName(String name)
    {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products)
        {
            productDtos.add(ProductMapper.productToProductDto(product));
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> findProductsByStoreId(Long storeId)
    {
        List<Product> products = productRepository.findByStoreId(storeId);
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products)
        {
            productDtos.add(ProductMapper.productToProductDto(product));
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> findAllProductsByStoreIdAndName(Long storeId, String name)
    {
        List<Product> products = productRepository
                .findByStoreIdAndNameContainingIgnoreCase(storeId, name);

        List<ProductDto> productDtos = new ArrayList<>();

        for(Product product : products)
        {
            productDtos.add(ProductMapper.productToProductDto(product));
        }

        return productDtos;
    }

}