package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String productTitle;

    @Column(nullable = false)
    private Integer productLprice;

    @Column
    private String productLink;

    @Column
    private String productImage;

    @Column
    private String productBrand;

    @Column(length = 50)
    private String productCategory1;

    @Column(length = 50)
    private String productCategory2;

    @Column(length = 50)
    private String productCategory3;

    @Column(length = 50)
    private String productCategory4;

    @Column
    private Integer productType;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "product")
    private Set<Cart> productCarts;

    @OneToMany(mappedBy = "product")
    private Set<OrderDetails> productOrderDetailses;

}
