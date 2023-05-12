package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.s9api.*;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.InputSource;

public class Main {
  public static final String RESOURCES_DIR;
  public static final String OUTPUT_DIR;

  static {
    RESOURCES_DIR = "src//main//resources//";
    OUTPUT_DIR = "src//main//resources//output//";
  }

  public static void main(String[] args) {
    try {
      //            convertToPDF();
      //      convertToXslt();
      convertToXsltV2();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void convertToPDF() throws IOException, FOPException, TransformerException {
    File xsltFile = new File("/home/clement/IdeaProjects/XSL_FO/src/main/resources/test.xml");
    StreamSource xmlSource =
        new StreamSource(new File("/home/clement/IdeaProjects/XSL_FO/src/main/resources/temp.xml"));
    FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
    OutputStream out;
    out =
        new java.io.FileOutputStream(
            "/home/clement/IdeaProjects/XSL_FO/src/main/resources/output.pdf");

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
      TransformerFactory tFactory = TransformerFactory.newDefaultInstance();
      Source xslDoc =
          new StreamSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/xsltforms.xsl");
      Source xmlDoc =
          new StreamSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/hello.xml");
      String outputFileName = "/home/clement/IdeaProjects/XSL_FO/src/main/resources/output.html";
      OutputStream htmlFile = new FileOutputStream(outputFileName);
      Transformer trasform = tFactory.newTransformer(xslDoc);
      trasform.transform(xmlDoc, new StreamResult(htmlFile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void convertToXsltV2() {
    try {
      Processor processor = new Processor(false);

      InputSource docsrc =
          new InputSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/hello.xml");
      InputSource xslsrc =
          new InputSource("/home/clement/IdeaProjects/XSL_FO/src/main/resources/xsltforms.xsl");

      XsltCompiler compiler = processor.newXsltCompiler();
      XsltExecutable exec = compiler.compile(new SAXSource(xslsrc));
      Xslt30Transformer transformer = exec.load30();
      Serializer out =
          processor.newSerializer(
              new File("/home/clement/IdeaProjects/XSL_FO/src/main/resources/output.html"));
      out.setOutputProperty(Serializer.Property.METHOD, "html");
      out.setOutputProperty(Serializer.Property.INDENT, "yes");
      out.setOutputProperty(Serializer.Property.INCLUDE_CONTENT_TYPE, "yes");
      transformer.transform(new SAXSource(docsrc), out);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
