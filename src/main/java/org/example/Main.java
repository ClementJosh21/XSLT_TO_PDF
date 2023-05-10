package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class Main
{
    public static final String RESOURCES_DIR;
    public static final String OUTPUT_DIR;

    static {
        RESOURCES_DIR = "src//main//resources//";
        OUTPUT_DIR = "src//main//resources//output//";
    }

    public static void main( String[] args )
    {
        try {
//            convertToPDF();
            convertToXslt();
        } catch (FOPException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void convertToPDF() throws IOException, FOPException, TransformerException {
        File xsltFile = new File("/home/clement/IdeaProjects/XSL_FO/src/main/resources/test.xml");
        StreamSource xmlSource = new StreamSource(new File("/home/clement/IdeaProjects/XSL_FO/src/main/resources/temp.xml"));
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out;
        out = new java.io.FileOutputStream("/home/clement/IdeaProjects/XSL_FO/src/main/resources/output.pdf");

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }

    public static void convertToXslt() throws IOException, FOPException, TransformerException {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Source xslDoc = new StreamSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/xsltforms.xsl");
            Source xmlDoc = new StreamSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/hello.xml");
            String outputFileName = "/home/clement/IdeaProjects/XSL_FO/src/main/resources/output.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);
            Transformer trasform = tFactory.newTransformer(xslDoc);
            trasform.transform(xmlDoc, new StreamResult(htmlFile));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
