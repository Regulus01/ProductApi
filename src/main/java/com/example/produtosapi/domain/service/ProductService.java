package com.example.produtosapi.domain.service;

import com.example.produtosapi.controllers.ProductController;
import com.example.produtosapi.domain.entity.ProductModel;
import com.example.produtosapi.domain.repository.ProductRepository;
import com.example.produtosapi.domain.dto.ProductRecordDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Transactional
public class ProductService {

    @Autowired // Utilizado para injeção de dependencias automaticas, sem a necessidade do construtor
    ProductRepository productRepository;

    public ProductModel CreateProduct(ProductRecordDto productRecordDto) {

        var productModel = new ProductModel();

        BeanUtils.copyProperties(productRecordDto, productModel);

        return productRepository.save(productModel);
    }

    public List<ProductModel> getAllProducts() {
        var productsList = productRepository.findAll();

        if (!productsList.isEmpty()) {
            for (ProductModel productModel : productsList) {
                var id = productModel.getIdProduct();

                productModel.add(linkTo(methodOn(ProductController.class)
                            .getProductById(id))
                            .withSelfRel());
            }
        }

        return productsList;
    }

    public Optional<ProductModel> getProductById(UUID id) {

        var productModel = productRepository.findById(id);

        if (productModel.isEmpty()) {
            return Optional.empty();
        }

        productModel.get().add(linkTo(methodOn(ProductController.class)
                          .getAllProducts())
                          .withRel("Products list"));

        return productModel;
    }

    public Optional<Object> updateProduct(UUID id, ProductRecordDto productRecordDto) {

        var productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return Optional.empty();
        }

        //Quando é optional sempre usa o .get para obter o seu valor.
        var productModel = productOptional.get();

        BeanUtils.copyProperties(productRecordDto, productModel);

        return Optional.of(productRepository.save(productModel));
    }

    public Optional<Object> deleteProduct(UUID id) {

        var productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return Optional.empty();
        }

        var productModel = productOptional.get();

        productRepository.delete(productModel);

        return Optional.of("Product delete sucessfully.");
    }
}
