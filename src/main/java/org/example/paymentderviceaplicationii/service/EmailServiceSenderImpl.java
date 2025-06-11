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
public class EmailServiceSenderImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    private final TransformationService transformationService;

    @Override
    public String sendEmail(PaymentTransactionDTO paymentTransaction) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(paymentTransaction.getDestination());
            helper.setSubject("Payment Transaction");
            helper.setText(
                    "<h1>" + "Your transaction for ("
                            + paymentTransaction.getDescription() +  ") !</h1>", true);

            transformationService.transformToXml(new PaymentTransactionDTO(
                    paymentTransaction.getAmount(),
                    paymentTransaction.getCurrency(),
                    paymentTransaction.getStatus(),
                    paymentTransaction.getDescription(),
                    paymentTransaction.getDestination()
            ), "/home/pc/IdeaProjects/Add-Mail-Sender/src/main/resources/payment_transaction.xml");

            File pdfFile = transformationService.transformToPdf(
                    "/home/pc/IdeaProjects/Add-Mail-Sender/src/main/resources/paymentTransaction-to-pdf.xslt",
                    "/home/pc/IdeaProjects/Add-Mail-Sender/src/main/resources/payment_transaction.xml",
                    "/home/pc/IdeaProjects/Add-Mail-Sender/src/main/resources/payment_transaction.pdf");
            FileSystemResource pdf = new FileSystemResource(pdfFile);
            helper.addAttachment("Payment_transaction.pdf", pdf);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "Mail delivered successfully";
    }
}
