package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}
