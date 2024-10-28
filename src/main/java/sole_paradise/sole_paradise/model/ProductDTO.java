package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Integer productId;

    @NotNull
    @Size(max = 255)
    private String productTitle;

    @NotNull
    private Integer productLprice;

    @Size(max = 255)
    private String productLink;

    @Size(max = 255)
    private String productImage;

    @Size(max = 255)
    private String productBrand;

    @Size(max = 50)
    private String productCategory1;

    @Size(max = 50)
    private String productCategory2;

    @Size(max = 50)
    private String productCategory3;

    @Size(max = 50)
    private String productCategory4;

    private Integer productType;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
