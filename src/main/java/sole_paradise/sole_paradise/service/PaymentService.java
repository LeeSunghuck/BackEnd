package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.Payment;
import sole_paradise.sole_paradise.model.PaymentDTO;
import sole_paradise.sole_paradise.repos.OrderRepository;
import sole_paradise.sole_paradise.repos.PaymentRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(final PaymentRepository paymentRepository,
            final OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public List<PaymentDTO> findAll() {
        final List<Payment> payments = paymentRepository.findAll(Sort.by("merchantUid"));
        return payments.stream()
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .toList();
    }

    public PaymentDTO get(final Integer merchantUid) {
        return paymentRepository.findById(merchantUid)
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        mapToEntity(paymentDTO, payment);
        return paymentRepository.save(payment).getMerchantUid();
    }

    public void update(final Integer merchantUid, final PaymentDTO paymentDTO) {
        final Payment payment = paymentRepository.findById(merchantUid)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentDTO, payment);
        paymentRepository.save(payment);
    }

    public void delete(final Integer merchantUid) {
        paymentRepository.deleteById(merchantUid);
    }

    private PaymentDTO mapToDTO(final Payment payment, final PaymentDTO paymentDTO) {
        paymentDTO.setMerchantUid(payment.getMerchantUid());
        paymentDTO.setPg(payment.getPg());
        paymentDTO.setPayAmount(payment.getPayAmount());
        paymentDTO.setPayMethod(payment.getPayMethod());
        paymentDTO.setPayName(payment.getPayName());
        paymentDTO.setBuyerEmail(payment.getBuyerEmail());
        paymentDTO.setBuyerName(payment.getBuyerName());
        paymentDTO.setBuyerTel(payment.getBuyerTel());
        paymentDTO.setBuyerAddr(payment.getBuyerAddr());
        paymentDTO.setBuyerPostcode(payment.getBuyerPostcode());
        paymentDTO.setPgStatus(payment.getPgStatus());
        paymentDTO.setCreatedAt(payment.getCreatedAt());
        paymentDTO.setUpdatedAt(payment.getUpdatedAt());
        paymentDTO.setOrder(payment.getOrder() == null ? null : payment.getOrder().getOrderId());
        return paymentDTO;
    }

    private Payment mapToEntity(final PaymentDTO paymentDTO, final Payment payment) {
        payment.setPg(paymentDTO.getPg());
        payment.setPayAmount(paymentDTO.getPayAmount());
        payment.setPayMethod(paymentDTO.getPayMethod());
        payment.setPayName(paymentDTO.getPayName());
        payment.setBuyerEmail(paymentDTO.getBuyerEmail());
        payment.setBuyerName(paymentDTO.getBuyerName());
        payment.setBuyerTel(paymentDTO.getBuyerTel());
        payment.setBuyerAddr(paymentDTO.getBuyerAddr());
        payment.setBuyerPostcode(paymentDTO.getBuyerPostcode());
        payment.setPgStatus(paymentDTO.getPgStatus());
        payment.setCreatedAt(paymentDTO.getCreatedAt());
        payment.setUpdatedAt(paymentDTO.getUpdatedAt());
        final Order order = paymentDTO.getOrder() == null ? null : orderRepository.findById(paymentDTO.getOrder())
                .orElseThrow(() -> new NotFoundException("order not found"));
        payment.setOrder(order);
        return payment;
    }

}
