package com.ecom.product_service.serviceImpl;

import com.ecom.product_service.dto.ProductRequestDto;
import com.ecom.product_service.dto.ProductResponseDto;
import com.ecom.product_service.entity.Category;
import com.ecom.product_service.entity.Product;
import com.ecom.product_service.repository.CategoryRepository;
import com.ecom.product_service.repository.ProductRepository;
import com.ecom.product_service.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;


    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Category  category =  categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
   }

    @Override
    public ProductResponseDto getProductById(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));

        return convertToDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductResponseDto updateStock(String productId, Integer stockQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        product.setStockQuantity(product.getStockQuantity() + stockQuantity);
        return convertToDto(productRepository.save(product));
    }

    @Override
    public String deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        return "Product " + productId +" deleted successfully";
    }

    private ProductResponseDto convertToDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductId(product.getProductId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStockQuantity(product.getStockQuantity());
        productResponseDto.setInStock(product.getInStock());
        productResponseDto.setCategoryName(product.getCategory().getName());
        return productResponseDto;
    }
}
