package com.ecommerce.backend.infrastructure.adapter;

import com.ecommerce.backend.domain.model.Product;
import com.ecommerce.backend.domain.port.IProductRepository;
import com.ecommerce.backend.infrastructure.entity.ProductEntity;
import com.ecommerce.backend.infrastructure.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductCrudRepositoryImpl implements IProductRepository {

    private final IProductCrudRepository iProductCrudRepository;
    private final ProductMapper productMapper;

    public ProductCrudRepositoryImpl(IProductCrudRepository iProductCrudRepository, ProductMapper productMapper) {
        this.iProductCrudRepository = iProductCrudRepository;
        this.productMapper = productMapper;
    }


    @Override
    public Product save(Product product) {
        return productMapper.toProduct( iProductCrudRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public Iterable<Product> findAll() {
        return productMapper.toProductList(iProductCrudRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        Optional<ProductEntity> productEntityOptional = iProductCrudRepository.findById(id);
        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            return productMapper.toProduct(productEntity);
        } else {
            throw new RuntimeException("Producto con id: " + id + " no se encuentra");
        }
    }

    @Override
    public void deleteById(Integer id) {
        Optional<ProductEntity> productOptional = iProductCrudRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Producto con id: " + id + " no se encuentra");
        }
        iProductCrudRepository.deleteById(id);
    }

}
