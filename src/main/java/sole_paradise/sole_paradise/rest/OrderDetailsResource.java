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
import sole_paradise.sole_paradise.model.OrderDetailsDTO;
import sole_paradise.sole_paradise.service.OrderDetailsService;


@RestController
@RequestMapping(value = "/api/orderDetailss", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderDetailsResource {

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsResource(final OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetailss() {
        return ResponseEntity.ok(orderDetailsService.findAll());
    }

    @GetMapping("/{orderDetailsNo}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(
            @PathVariable(name = "orderDetailsNo") final Integer orderDetailsNo) {
        return ResponseEntity.ok(orderDetailsService.get(orderDetailsNo));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createOrderDetails(
            @RequestBody @Valid final OrderDetailsDTO orderDetailsDTO) {
        final Integer createdOrderDetailsNo = orderDetailsService.create(orderDetailsDTO);
        return new ResponseEntity<>(createdOrderDetailsNo, HttpStatus.CREATED);
    }

    @PutMapping("/{orderDetailsNo}")
    public ResponseEntity<Integer> updateOrderDetails(
            @PathVariable(name = "orderDetailsNo") final Integer orderDetailsNo,
            @RequestBody @Valid final OrderDetailsDTO orderDetailsDTO) {
        orderDetailsService.update(orderDetailsNo, orderDetailsDTO);
        return ResponseEntity.ok(orderDetailsNo);
    }

    @DeleteMapping("/{orderDetailsNo}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrderDetails(
            @PathVariable(name = "orderDetailsNo") final Integer orderDetailsNo) {
        orderDetailsService.delete(orderDetailsNo);
        return ResponseEntity.noContent().build();
    }

}
