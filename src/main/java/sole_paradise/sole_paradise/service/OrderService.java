package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.OrderDetails;
import sole_paradise.sole_paradise.domain.Payment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.OrderDTO;
import sole_paradise.sole_paradise.repos.OrderDetailsRepository;
import sole_paradise.sole_paradise.repos.OrderRepository;
import sole_paradise.sole_paradise.repos.PaymentRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository,
            final PaymentRepository paymentRepository,
            final OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Integer orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOrderId();
    }

    public void update(final Integer orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setField(order.getField());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getUserId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setField(orderDTO.getField());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setUpdatedAt(orderDTO.getUpdatedAt());
        order.setOrderStatus(orderDTO.getOrderStatus());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        return order;
    }

    public ReferencedWarning getReferencedWarning(final Integer orderId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        final Payment orderPayment = paymentRepository.findFirstByOrder(order);
        if (orderPayment != null) {
            referencedWarning.setKey("order.payment.order.referenced");
            referencedWarning.addParam(orderPayment.getMerchantUid());
            return referencedWarning;
        }
        final OrderDetails orderOrderDetails = orderDetailsRepository.findFirstByOrder(order);
        if (orderOrderDetails != null) {
            referencedWarning.setKey("order.orderDetails.order.referenced");
            referencedWarning.addParam(orderOrderDetails.getOrderDetailsNo());
            return referencedWarning;
        }
        return null;
    }

}
