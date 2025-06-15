package org.example.paymentderviceaplicationii.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransformationService {
    private final ObjectMapper mapper;

    public void transformToXml(Object object, String xmlFileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, new File(xmlFileName));
        } catch (JAXBException e) {
            log.error("Error while marshalling object", e);
            throw new RuntimeException(e);
        }
    }

    public File transformToPdf(String xsltFileName, String xmlFileName, String pdfFileName) {
        File xsltFile = new File(xsltFileName);

        StreamSource xslStreamSource = new StreamSource(new File(xmlFileName));

        FopFactory factory = FopFactory.newInstance(new File(".").toURI());

        FOUserAgent foUserAgent = factory.newFOUserAgent();

        try(OutputStream out = new FileOutputStream(pdfFileName)) {
            Fop fop = factory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltFile));

            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xslStreamSource, result);
        } catch (FOPException | TransformerException | IOException e) {
            log.error("Error while transforming xslt file {} to PDF", xsltFileName, e);
            throw new RuntimeException(e);
        }

        return new File(pdfFileName);
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Json serialization error.", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            log.error("Json deserializing exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
