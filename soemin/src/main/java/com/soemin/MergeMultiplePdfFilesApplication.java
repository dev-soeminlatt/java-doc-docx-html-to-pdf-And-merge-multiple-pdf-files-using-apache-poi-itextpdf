package com.soemin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class MergeMultiplePdfFilesApplication {

	public static void main(String[] args) {
		try {
			String pdfFilePath1 = "D:/sample1.pdf";
			String pdfFilePath2 = "D:/sample2.pdf";
			String mergedFilePath = "D:/marged.pdf";

			List<InputStream> inputPdfList = new ArrayList<InputStream>();

			FileInputStream pdf1InputStream = new FileInputStream(new File(pdfFilePath1));
			FileInputStream pdf2InputStream = new FileInputStream(new File(pdfFilePath2));

			inputPdfList.add(pdf1InputStream);
			inputPdfList.add(pdf2InputStream);

			FileOutputStream outputStream = new FileOutputStream(new File(mergedFilePath));
			// call method to merge pdf files.
			mergePdfFiles(inputPdfList, outputStream);

			System.out.println("Done");

		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		}
	}

	public static void mergePdfFiles(List<InputStream> inputPdfList, OutputStream outputStream) throws Exception {

		try {
			// Create document and pdfReader objects.
			Document document = new Document();
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;

			// Create pdf Iterator object using inputPdfList.
			Iterator<InputStream> pdfIterator = inputPdfList.iterator();

			// Create reader list for the input pdf files.
			while (pdfIterator.hasNext()) {
				InputStream pdf = pdfIterator.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				totalPages = totalPages + pdfReader.getNumberOfPages();
			}

			// Create writer for the outputStream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			// Open document.
			document.open();

			// Contain the pdf data.
			PdfContentByte pageContentByte = writer.getDirectContent();

			PdfImportedPage pdfImportedPage;
			int currentPdfReaderPage = 1;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			// Iterate and process the reader list.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				// Create page and add content.
				while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
					document.newPage();
					pdfImportedPage = writer.getImportedPage(pdfReader, currentPdfReaderPage);
					pageContentByte.addTemplate(pdfImportedPage, 0, 0);
					currentPdfReaderPage++;
				}
				currentPdfReaderPage = 1;
			}

			// Close document and outputStream.
			outputStream.flush();
			document.close();
			outputStream.close();

			System.out.println("Pdf files merged successfully.");
		} catch (Exception ex) {

		}
	}
}
