package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentDetailDTO {

    @Size(max = 255)
    @PaymentDetailCardNameValid
    private String cardName;

    @NotNull
    @Size(max = 19)
    private String cardNumber;

    @NotNull
    private Integer installmentMonths;

    @NotNull
    @Size(max = 50)
    private String approvalNumber;

}
