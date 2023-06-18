package in.emagna.cachepoc.repository;

import in.emagna.cachepoc.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;


public interface ProductRepository extends JpaRepository<Product, Serializable> {

}
