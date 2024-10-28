package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.User;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByUser(User user);

}
