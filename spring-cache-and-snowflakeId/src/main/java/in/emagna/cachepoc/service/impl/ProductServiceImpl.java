package in.emagna.cachepoc.service.impl;

import in.emagna.cachepoc.domain.Product;
import in.emagna.cachepoc.repository.ProductRepository;
import in.emagna.cachepoc.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CacheManager cacheManager;
    public ProductServiceImpl(ProductRepository productRepository, CacheManager cacheManager) {
        this.productRepository = productRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * @param product
     * @return
     */
    @CachePut(value = "products",key = "#product.id")
    @Override
    public Product create(Product product) {
        Product saved = productRepository.save(product);
        clearCache();
//        updateProductCache(saved);
        return saved;
    }

    /**
     * @param product
     * @return
     */
    @CachePut(value = "products", key = "#product.id")
    @Override
    public Product update(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException("Product is not available to update"));
        product.setId(existingProduct.getId());
        return productRepository.save(product);
    }

    /**
     * @param id
     * @return
     */
    @Cacheable(value = "products", key = "#id")
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product not found"));
    }

    /**
     * @return
     */
    @Cacheable(value = "products",key = "'allProducts'")
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void updateProductCache(Product product) {
        Cache cache = cacheManager.getCache("products");
        if (cache != null) {
            List<Product> productList = cache.get("allProducts", List.class);
            if (productList != null) {
                productList.add(product);
                cache.put("allProducts", productList);
            }
        }
    }

    @CacheEvict(value = "products", key = "'allProducts'")
    public void clearCache() {
        // Evict the cache for findAll method
        log.info("Evicting findAll cache...");
    }
}
