package in.emagna.cachepoc.web.rest;

import in.emagna.cachepoc.domain.Product;
import in.emagna.cachepoc.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return productService.create(product);
    }
    @PutMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        return productService.update(product);
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable Long id){
        return productService.findById(id);
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.findAll();
    }

}
