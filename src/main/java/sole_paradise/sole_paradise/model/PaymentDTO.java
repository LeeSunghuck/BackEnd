package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentDTO {

    private Integer merchantUid;

    @NotNull
    private Boolean pg;

    @NotNull
    private Integer payAmount;

    @NotNull
    private Boolean payMethod;

    @NotNull
    private Boolean payName;

    private Boolean buyerEmail;

    @NotNull
    private Boolean buyerName;

    @NotNull
    private Boolean buyerTel;

    @NotNull
    private Boolean buyerAddr;

    @NotNull
    private Boolean buyerPostcode;

    @NotNull
    private Integer pgStatus;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;

    @NotNull
    private Integer order;

}
