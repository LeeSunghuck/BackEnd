package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDetailsDTO {

    private Integer orderDetailsNo;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer order;

    @NotNull
    private Integer product;

}
