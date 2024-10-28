package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Payment {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer merchantUid;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean pg;

    @Column(nullable = false)
    private Integer payAmount;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean payMethod;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean payName;

    @Column(columnDefinition = "tinyint", length = 1)
    private Boolean buyerEmail;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean buyerName;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean buyerTel;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean buyerAddr;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean buyerPostcode;

    @Column(nullable = false)
    private Integer pgStatus;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}
