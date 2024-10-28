package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.PaymentDetail;
import sole_paradise.sole_paradise.model.PaymentDetailDTO;
import sole_paradise.sole_paradise.repos.PaymentDetailRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class PaymentDetailService {

    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailService(final PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }

    public List<PaymentDetailDTO> findAll() {
        final List<PaymentDetail> paymentDetails = paymentDetailRepository.findAll(Sort.by("cardName"));
        return paymentDetails.stream()
                .map(paymentDetail -> mapToDTO(paymentDetail, new PaymentDetailDTO()))
                .toList();
    }

    public PaymentDetailDTO get(final String cardName) {
        return paymentDetailRepository.findById(cardName)
                .map(paymentDetail -> mapToDTO(paymentDetail, new PaymentDetailDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PaymentDetailDTO paymentDetailDTO) {
        final PaymentDetail paymentDetail = new PaymentDetail();
        mapToEntity(paymentDetailDTO, paymentDetail);
        paymentDetail.setCardName(paymentDetailDTO.getCardName());
        return paymentDetailRepository.save(paymentDetail).getCardName();
    }

    public void update(final String cardName, final PaymentDetailDTO paymentDetailDTO) {
        final PaymentDetail paymentDetail = paymentDetailRepository.findById(cardName)
                .orElseThrow(NotFoundException::new);
        mapToEntity(paymentDetailDTO, paymentDetail);
        paymentDetailRepository.save(paymentDetail);
    }

    public void delete(final String cardName) {
        paymentDetailRepository.deleteById(cardName);
    }

    private PaymentDetailDTO mapToDTO(final PaymentDetail paymentDetail,
            final PaymentDetailDTO paymentDetailDTO) {
        paymentDetailDTO.setCardName(paymentDetail.getCardName());
        paymentDetailDTO.setCardNumber(paymentDetail.getCardNumber());
        paymentDetailDTO.setInstallmentMonths(paymentDetail.getInstallmentMonths());
        paymentDetailDTO.setApprovalNumber(paymentDetail.getApprovalNumber());
        return paymentDetailDTO;
    }

    private PaymentDetail mapToEntity(final PaymentDetailDTO paymentDetailDTO,
            final PaymentDetail paymentDetail) {
        paymentDetail.setCardNumber(paymentDetailDTO.getCardNumber());
        paymentDetail.setInstallmentMonths(paymentDetailDTO.getInstallmentMonths());
        paymentDetail.setApprovalNumber(paymentDetailDTO.getApprovalNumber());
        return paymentDetail;
    }

    public boolean cardNameExists(final String cardName) {
        return paymentDetailRepository.existsByCardNameIgnoreCase(cardName);
    }

}
