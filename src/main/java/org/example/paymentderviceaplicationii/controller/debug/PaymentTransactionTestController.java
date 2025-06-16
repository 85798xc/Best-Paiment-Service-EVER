//package org.example.paymentderviceaplicationii.controller.debug;
//
//import lombok.RequiredArgsConstructor;
//import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
//import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionRequestDTO;
//import org.example.paymentderviceaplicationii.model.enums.PaymentProvider;
//import org.example.paymentderviceaplicationii.model.enums.Status;
//import org.example.paymentderviceaplicationii.service.PaymentTransactionService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/payment-transaction-test")
//@RequiredArgsConstructor
//public class PaymentTransactionTestController {
//
//    private final PaymentTransactionService paymentTransactionService;
//
//    @PostMapping
//    public String createPaymentTransaction() {
//        PaymentTransactionRequestDTO request = new PaymentTransactionRequestDTO();
//
//        request.setPaymentProvider(PaymentProvider.PAYPAL);
//        request.setUserPaymentEmail("imatveyadam@gmail.com");
//        request.setAmount(69L);
//        request.setCurrency("EUR");
//        request.setDescription("test transaction");
//
//        return paymentTransactionService.createPaymentTransaction(request);
//    }
//}
