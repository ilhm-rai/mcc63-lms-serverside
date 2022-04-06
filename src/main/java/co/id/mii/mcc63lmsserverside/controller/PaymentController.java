package co.id.mii.mcc63lmsserverside.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.id.mii.mcc63lmsserverside.model.Payment;
import co.id.mii.mcc63lmsserverside.model.dto.request.PaymentRequest;
import co.id.mii.mcc63lmsserverside.service.PaymentService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("payment")
@AllArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Payment pay(@ModelAttribute PaymentRequest request) {
    return paymentService.pay(request);
  }

  @PostMapping("{paymentId}/confirm")
  public String confirm(@PathVariable("paymentId") Long paymentId) {
    return paymentService.paid(paymentId);
  }
}
