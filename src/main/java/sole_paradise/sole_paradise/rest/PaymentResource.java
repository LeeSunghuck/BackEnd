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
import sole_paradise.sole_paradise.model.PaymentDTO;
import sole_paradise.sole_paradise.service.PaymentService;


@RestController
@RequestMapping(value = "/api/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{merchantUid}")
    public ResponseEntity<PaymentDTO> getPayment(
            @PathVariable(name = "merchantUid") final Integer merchantUid) {
        return ResponseEntity.ok(paymentService.get(merchantUid));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid final PaymentDTO paymentDTO) {
        final Integer createdMerchantUid = paymentService.create(paymentDTO);
        return new ResponseEntity<>(createdMerchantUid, HttpStatus.CREATED);
    }

    @PutMapping("/{merchantUid}")
    public ResponseEntity<Integer> updatePayment(
            @PathVariable(name = "merchantUid") final Integer merchantUid,
            @RequestBody @Valid final PaymentDTO paymentDTO) {
        paymentService.update(merchantUid, paymentDTO);
        return ResponseEntity.ok(merchantUid);
    }

    @DeleteMapping("/{merchantUid}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePayment(
            @PathVariable(name = "merchantUid") final Integer merchantUid) {
        paymentService.delete(merchantUid);
        return ResponseEntity.noContent().build();
    }

}
