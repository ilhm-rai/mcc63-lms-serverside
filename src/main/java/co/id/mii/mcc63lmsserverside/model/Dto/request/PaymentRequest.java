package co.id.mii.mcc63lmsserverside.model.dto.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class PaymentRequest {

  private MultipartFile paymentSlip;
  private Long enrollmentId;
}
