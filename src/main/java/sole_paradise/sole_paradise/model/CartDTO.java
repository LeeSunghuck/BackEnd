package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {

    private Integer cartId;

    @NotNull
    private Integer quantity;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Integer product;

    @NotNull
    private Integer user;

}
