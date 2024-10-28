package sole_paradise.sole_paradise.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sole_paradise.sole_paradise.model.PaymentDetailDTO;
import sole_paradise.sole_paradise.service.PaymentDetailService;


@RestController
@RequestMapping(value = "/api/paymentDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentDetailResource {

    private final PaymentDetailService paymentDetailService;

    public PaymentDetailResource(final PaymentDetailService paymentDetailService) {
        this.paymentDetailService = paymentDetailService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDetailDTO>> getAllPaymentDetails() {
        return ResponseEntity.ok(paymentDetailService.findAll());
    }

    @GetMapping("/{cardName}")
    public ResponseEntity<PaymentDetailDTO> getPaymentDetail(
            @PathVariable(name = "cardName") final String cardName) {
        return ResponseEntity.ok(paymentDetailService.get(cardName));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createPaymentDetail(
            @RequestBody @Valid final PaymentDetailDTO paymentDetailDTO) {
        final String createdCardName = paymentDetailService.create(paymentDetailDTO);
        return new ResponseEntity<>('"' + createdCardName + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{cardName}")
    public ResponseEntity<String> updatePaymentDetail(
            @PathVariable(name = "cardName") final String cardName,
            @RequestBody @Valid final PaymentDetailDTO paymentDetailDTO) {
        paymentDetailService.update(cardName, paymentDetailDTO);
        return ResponseEntity.ok('"' + cardName + '"');
    }

    @DeleteMapping("/{cardName}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePaymentDetail(
            @PathVariable(name = "cardName") final String cardName) {
        paymentDetailService.delete(cardName);
        return ResponseEntity.noContent().build();
    }

}
