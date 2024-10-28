package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class PaymentDetail {

    @Id
    @Column(nullable = false, updatable = false)
    private String cardName;

    @Column(nullable = false, length = 19)
    private String cardNumber;

    @Column(nullable = false)
    private Integer installmentMonths;

    @Column(nullable = false, length = 50)
    private String approvalNumber;

}
