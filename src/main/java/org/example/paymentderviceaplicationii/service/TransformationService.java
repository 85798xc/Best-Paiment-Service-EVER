package org.example.paymentderviceaplicationii.service;

import java.io.File;

public interface TransformationService {
    void transformToXml(Object object, String xmlFileName);

    File transformToPdf(String xsltFileName, String xmlFileName, String pdfFileName);
}
