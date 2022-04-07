package co.id.mii.mcc63lmsserverside.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import co.id.mii.mcc63lmsserverside.controller.PaymentController;
import co.id.mii.mcc63lmsserverside.model.Enrollment;
import co.id.mii.mcc63lmsserverside.model.Payment;
import co.id.mii.mcc63lmsserverside.model.dto.request.PaymentRequest;
import co.id.mii.mcc63lmsserverside.repository.PaymentRepository;
import co.id.mii.mcc63lmsserverside.util.StorageService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@AllArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final EnrollmentService enrollmentService;

  public List<Payment> getAll() {
    return paymentRepository.findAll().stream()
        .map(p -> new Payment(
            p.getId(),
            p.getEnrollment(),
            MvcUriComponentsBuilder
                .fromMethodName(PaymentController.class, "getFile", p.getPaymentSlip())
                .build().toUri().toString(),
            p.getUploadedAt(),
            p.getConfirmedAt()))
        .collect(Collectors.toList());
  }

  public Payment pay(PaymentRequest request) {

    Long id = request.getEnrollmentId();

    Enrollment enrollment = enrollmentService.getById(id);

    String paymentSlip = RandomString.make(20) + "."
        + FilenameUtils.getExtension(request.getPaymentSlip().getOriginalFilename());

    StorageService.store("upload/payment", paymentSlip, request.getPaymentSlip());

    Payment payment = new Payment(
        paymentSlip,
        enrollment,
        LocalDateTime.now());

    payment.setId(id);

    return paymentRepository.save(payment);
  }

  @Transactional
  public String paid(Long id) {
    Payment payment = paymentRepository.getById(id);
    Enrollment enrollment = enrollmentService.getById(id);

    enrollment.setPaid(true);
    payment.setConfirmedAt(LocalDateTime.now());

    return "Payment confirmed. Good luck and have fun learning!";
  }

}
