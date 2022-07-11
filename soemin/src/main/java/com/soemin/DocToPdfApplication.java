package com.soemin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.w3c.dom.Document;

import com.itextpdf.html2pdf.HtmlConverter;

public class DocToPdfApplication {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Start");
		InputStream docFile = new FileInputStream(new File("D:/sample.doc"));
		HWPFDocument doc = new HWPFDocument(docFile);
		Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(newDocument);
		wordToHtmlConverter.processDocument(doc);

		StringWriter stringWriter = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance().newTransformer();

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transformer.setOutputProperty(OutputKeys.METHOD, "html");
		transformer.transform(new DOMSource(wordToHtmlConverter.getDocument()), new StreamResult(stringWriter));

		String html = stringWriter.toString();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		HtmlConverter.convertToPdf(html, buffer);
		byte[] pdfArray = buffer.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(pdfArray);
		String pdfFilePath = "D:/sample_converted.pdf";
		try {
			FileUtils.copyInputStreamToFile(inputStream, new File(pdfFilePath));
			System.out.println("Done");
		} catch (IOException e) {
			System.out.println("Error:" + e.getMessage());
		}

	}

}
