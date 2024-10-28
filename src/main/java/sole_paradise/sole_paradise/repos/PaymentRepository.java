package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findFirstByOrder(Order order);

}
