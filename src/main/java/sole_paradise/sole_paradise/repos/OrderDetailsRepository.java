package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.OrderDetails;
import sole_paradise.sole_paradise.domain.Product;


public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

    OrderDetails findFirstByOrder(Order order);

    OrderDetails findFirstByProduct(Product product);

}
