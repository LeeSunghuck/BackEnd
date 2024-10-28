package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.OrderDetails;
import sole_paradise.sole_paradise.domain.Product;
import sole_paradise.sole_paradise.model.OrderDetailsDTO;
import sole_paradise.sole_paradise.repos.OrderDetailsRepository;
import sole_paradise.sole_paradise.repos.OrderRepository;
import sole_paradise.sole_paradise.repos.ProductRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailsService(final OrderDetailsRepository orderDetailsRepository,
            final OrderRepository orderRepository, final ProductRepository productRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDetailsDTO> findAll() {
        final List<OrderDetails> orderDetailses = orderDetailsRepository.findAll(Sort.by("orderDetailsNo"));
        return orderDetailses.stream()
                .map(orderDetails -> mapToDTO(orderDetails, new OrderDetailsDTO()))
                .toList();
    }

    public OrderDetailsDTO get(final Integer orderDetailsNo) {
        return orderDetailsRepository.findById(orderDetailsNo)
                .map(orderDetails -> mapToDTO(orderDetails, new OrderDetailsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDetailsDTO orderDetailsDTO) {
        final OrderDetails orderDetails = new OrderDetails();
        mapToEntity(orderDetailsDTO, orderDetails);
        return orderDetailsRepository.save(orderDetails).getOrderDetailsNo();
    }

    public void update(final Integer orderDetailsNo, final OrderDetailsDTO orderDetailsDTO) {
        final OrderDetails orderDetails = orderDetailsRepository.findById(orderDetailsNo)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDetailsDTO, orderDetails);
        orderDetailsRepository.save(orderDetails);
    }

    public void delete(final Integer orderDetailsNo) {
        orderDetailsRepository.deleteById(orderDetailsNo);
    }

    private OrderDetailsDTO mapToDTO(final OrderDetails orderDetails,
            final OrderDetailsDTO orderDetailsDTO) {
        orderDetailsDTO.setOrderDetailsNo(orderDetails.getOrderDetailsNo());
        orderDetailsDTO.setQuantity(orderDetails.getQuantity());
        orderDetailsDTO.setOrder(orderDetails.getOrder() == null ? null : orderDetails.getOrder().getOrderId());
        orderDetailsDTO.setProduct(orderDetails.getProduct() == null ? null : orderDetails.getProduct().getProductId());
        return orderDetailsDTO;
    }

    private OrderDetails mapToEntity(final OrderDetailsDTO orderDetailsDTO,
            final OrderDetails orderDetails) {
        orderDetails.setQuantity(orderDetailsDTO.getQuantity());
        final Order order = orderDetailsDTO.getOrder() == null ? null : orderRepository.findById(orderDetailsDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        orderDetails.setOrder(order);
        final Product product = orderDetailsDTO.getProduct() == null ? null : productRepository.findById(orderDetailsDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        orderDetails.setProduct(product);
        return orderDetails;
    }

}
