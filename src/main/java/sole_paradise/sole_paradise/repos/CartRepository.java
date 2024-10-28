package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Cart;
import sole_paradise.sole_paradise.domain.Product;
import sole_paradise.sole_paradise.domain.User;


public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findFirstByProduct(Product product);

    Cart findFirstByUser(User user);

}
