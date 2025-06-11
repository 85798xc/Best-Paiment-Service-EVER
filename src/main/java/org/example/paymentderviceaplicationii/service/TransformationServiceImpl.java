package org.example.paymentderviceaplicationii.service;


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
public class TransformationServiceImpl implements TransformationService {
    @Override
    public void transformToXml(Object object, String xmlFileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, new File(xmlFileName));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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
            throw new RuntimeException(e);
        }

        return new File(pdfFileName);
    }
}
