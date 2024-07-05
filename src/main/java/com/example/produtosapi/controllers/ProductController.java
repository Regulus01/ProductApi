package com.example.produtosapi.controllers;

import com.example.produtosapi.domain.dto.ProductRecordDto;
import com.example.produtosapi.domain.entity.ProductModel;
import com.example.produtosapi.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductController {

    // Melhorar api
    // incluir paginação
    // filtros
    // customizações de erros
    // novas camadas beans @service
    @Autowired
    private ProductService _service;

    //Niveis de maturidade de richardson
    @ApiResponses(value = @ApiResponse(responseCode = "201", description = "created"))
    @Operation(summary = "create product")
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {

        var response = _service.CreateProduct(productRecordDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {

        var response = _service.getAllProducts();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable(value = "id") UUID id) {

        var response = _service.getProductById(id);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                                @RequestBody ProductRecordDto productRecordDto) {

        var response = _service.updateProduct(id, productRecordDto);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {

        var response = _service.deleteProduct(id);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Product delete sucessfully.");
    }

}
