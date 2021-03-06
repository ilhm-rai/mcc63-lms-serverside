package co.id.mii.mcc63lmsserverside.controller;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import co.id.mii.mcc63lmsserverside.model.Payment;
import co.id.mii.mcc63lmsserverside.model.dto.request.PaymentRequest;
import co.id.mii.mcc63lmsserverside.service.PaymentService;
import co.id.mii.mcc63lmsserverside.util.StorageService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("payment")
@AllArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @GetMapping
  @ResponseBody
  public ResponseEntity<List<Payment>> getAll() {
    return ResponseEntity.ok().body(paymentService.getAll());
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseBody
  public ResponseEntity<Payment> pay(@ModelAttribute PaymentRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.pay(request));
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) {
    Resource file = StorageService.loadAsResource("upload/payment", filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
}
