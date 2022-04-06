package co.id.mii.mcc63lmsserverside.service;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.model.Enrollment;
import co.id.mii.mcc63lmsserverside.model.Payment;
import co.id.mii.mcc63lmsserverside.model.dto.request.PaymentRequest;
import co.id.mii.mcc63lmsserverside.repository.PaymentRepository;
import co.id.mii.mcc63lmsserverside.util.FileUpload;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@Service
@AllArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final EnrollmentService enrollmentService;

  public Payment pay(PaymentRequest request) {

    Long id = request.getEnrollmentId();

    Enrollment enrollment = enrollmentService.getById(id);

    String paymentSlip = RandomString.make(20) + "."
        + FilenameUtils.getExtension(request.getPaymentSlip().getOriginalFilename());

    FileUpload.store("upload/payment", paymentSlip, request.getPaymentSlip());

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
