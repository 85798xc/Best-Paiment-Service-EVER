package org.example.paymentderviceaplicationii.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.paymentderviceaplicationii.model.dto.PaymentTransactionDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    private final TransformationService transformationService;

    public String sendEmail(PaymentTransactionDTO request) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(request.getUserPaymentEmail());
            helper.setSubject("Payment Transaction");
            helper.setText(
                    "<h1>" + "Your transaction for ("
                            + request.getDescription() +  ") !</h1>", true);

            transformationService.transformToXml(new PaymentTransactionDTO(
                    request.getPaymentProvider(),
                    request.getAmount(),
                    request.getCurrency(),
                    request.getStatus(),
                    request.getDescription(),
                    request.getUserPaymentEmail()
            ), "src/main/resources/payment_transaction.xml");

            File pdfFile = transformationService.transformToPdf(
                    "src/main/resources/paymentTransaction-to-pdf.xslt",
                    "src/main/resources/payment_transaction.xml",
                    "src/main/resources/payment_transaction.pdf");
            FileSystemResource pdf = new FileSystemResource(pdfFile);
            helper.addAttachment("Payment_transaction.pdf", pdf);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "Mail delivered successfully";
    }
}
