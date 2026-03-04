package com.arshad.smart_pos_system.controller;

import com.arshad.smart_pos_system.dto.ProductDto;
import com.arshad.smart_pos_system.model.Product;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ============================
    // CREATE PRODUCT (ADMIN)
    // ============================
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            @AuthenticationPrincipal User user) {

        ProductDto createdProduct = productService.createProduct(productDto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // ============================
    // UPDATE PRODUCT (ADMIN)
    // ============================
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto,
            @AuthenticationPrincipal User user) {

        ProductDto updatedProduct = productService.updateProduct(productDto, id, user);

        return ResponseEntity.ok(updatedProduct);
    }

    // ============================
    // DELETE PRODUCT (ADMIN)
    // ============================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

    // ============================
    // GET PRODUCT BY ID
    // ============================
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        Product product = productService.findProductById(id);

        return ResponseEntity.ok(product);
    }

    // ============================
    // GET ALL PRODUCTS (SUPER_ADMIN)
    // ============================
    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        List<ProductDto> products = productService.findAllProducts();

        return ResponseEntity.ok(products);
    }

    // ============================
    // GET PRODUCTS BY STORE
    // ============================
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<ProductDto>> getProductsByStore(@PathVariable Long storeId) {

        List<ProductDto> products = productService.findProductsByStoreId(storeId);

        return ResponseEntity.ok(products);
    }

    // ============================
    // SEARCH PRODUCTS BY KEYWORD
    // ============================
    @GetMapping("/store/{storeId}/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @PathVariable Long storeId,
            @RequestParam String keyword) {

        List<ProductDto> products = productService.searchByKeyword(storeId, keyword);

        return ResponseEntity.ok(products);
    }

    // ============================
    // SEARCH PRODUCTS BY NAME
    // ============================
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<ProductDto>> searchProductsByName(
            @RequestParam String name) {

        List<ProductDto> products = productService.findAllProductsByName(name);

        return ResponseEntity.ok(products);
    }

    // ============================
    // STORE + NAME SEARCH
    // ============================
    @GetMapping("/store/{storeId}/name")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<ProductDto>> getProductsByStoreAndName(
            @PathVariable Long storeId,
            @RequestParam String name) {

        List<ProductDto> products =
                productService.findAllProductsByStoreIdAndName(storeId, name);

        return ResponseEntity.ok(products);
    }
}