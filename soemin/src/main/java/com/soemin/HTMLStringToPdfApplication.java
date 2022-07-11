package com.soemin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.itextpdf.html2pdf.HtmlConverter;

public class HTMLStringToPdfApplication {
	public static void main(String[] args) {
		try {
			System.out.println("Start");
			String htmlString = "<html><body><h1>Testing</h1><div><span style='color: red;'>This is Testing</span></div></body></html>";
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			HtmlConverter.convertToPdf(htmlString, buffer);
			byte[] pdfArray = buffer.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(pdfArray);
			String pdfFilePath = "D:/sample_converted.pdf";
			FileUtils.copyInputStreamToFile(inputStream, new File(pdfFilePath));
			System.out.println("Done");
		} catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
}
