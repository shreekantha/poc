package in.emagna.cachepoc.service;

import in.emagna.cachepoc.domain.Product;

import java.util.List;

public interface ProductService {

    Product create(Product product);
    Product update(Product product);
    Product findById(Long id);
    List<Product> findAll();


}
