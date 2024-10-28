package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.PaymentDetail;


public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String> {

    boolean existsByCardNameIgnoreCase(String cardName);

}
