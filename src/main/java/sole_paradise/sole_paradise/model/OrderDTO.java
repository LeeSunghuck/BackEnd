package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Integer orderId;

    @NotNull
    private OffsetDateTime orderDate;

    @NotNull
    private Integer totalAmount;

    @Size(max = 100)
    private String field;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    @Size(max = 100)
    private String orderStatus;

    @NotNull
    private Integer user;

}
