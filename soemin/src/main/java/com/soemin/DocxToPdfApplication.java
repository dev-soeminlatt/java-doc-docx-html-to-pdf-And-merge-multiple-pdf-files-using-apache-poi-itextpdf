package com.soemin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHyperlink;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;


/**
 * @author Soe Min Latt
 *
 */
public class DocxToPdfApplication {
	
	public static void main(String[] args) {
		
		try {
			System.out.println("Start");
			//get input stream from file
			InputStream inputStream = new FileInputStream("D:/AnilPanwar.docx");
			//create document for docx file - XWPFDocument is for docx file, otherwise HWPFDocument is for doc file
			XWPFDocument document = new XWPFDocument(inputStream);
			removeHyperLinkInHeaderFooter(document);
			PdfOptions pdfOptions = PdfOptions.create();
			OutputStream out = new FileOutputStream(new File("D:/sample_converted.pdf"));
			document.createNumbering();
			//start converting docx to pdf files
			PdfConverter.getInstance().convert(document, out, pdfOptions);
			document.close();
			out.close();
			System.out.println("Done");

		} catch (Exception ex) {
			System.out.println("Error:" + ex.getMessage());
		}
	}
	
	static void removeHyperLinkInHeaderFooter(XWPFDocument document){
		
		XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();
		XWPFHeader header = policy.getDefaultHeader();
		XWPFFooter footer = policy.getDefaultFooter();
		
		//remove hyperlink and replace with text in paragraph in header 
		for (XWPFParagraph paragraph : header.getParagraphs()) {
			List<CTHyperlink> hyperlinks = paragraph.getCTP().getHyperlinkList();
			String tempHyperLinkValue = "";
			CTHyperlink[] hyperlinksarr = hyperlinks.toArray(new CTHyperlink[0]);
			for (int i = 0; i < hyperlinksarr.length; i++) {
				CTHyperlink hyperlink = hyperlinksarr[i];
				tempHyperLinkValue = hyperlink.getRArray(0).getTArray(0).getStringValue();
				paragraph.getCTP().removeHyperlink(0);
			}
			if (!tempHyperLinkValue.equalsIgnoreCase("")) {
				XWPFRun run = paragraph.createRun();
				run.setText(tempHyperLinkValue);
			}
		}
		
		//remove hyperlink and replace with text in table in header 
		for (XWPFTable tbl : header.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						List<CTHyperlink> hyperlinks = p.getCTP().getHyperlinkList();
						String tempHyperLinkValue = "";
						CTHyperlink[] hyperlinksarr = hyperlinks.toArray(new CTHyperlink[0]);
						for (int i = 0; i < hyperlinksarr.length; i++) {
							CTHyperlink hyperlink = hyperlinksarr[i];
							tempHyperLinkValue = hyperlink.getRArray(0).getTArray(0).getStringValue();
							p.getCTP().removeHyperlink(0);
						}
						if (!tempHyperLinkValue.equalsIgnoreCase("")) {
							XWPFRun run = p.createRun();
							run.setText(tempHyperLinkValue);
						}
					}
				}
			}
		}

		//remove hyperlink and replace with text in paragraph in footer
		for (XWPFParagraph paragraph : footer.getParagraphs()) {
			List<CTHyperlink> hyperlinks = paragraph.getCTP().getHyperlinkList();
			String tempHyperLinkValue = "";
			CTHyperlink[] hyperlinksarr = hyperlinks.toArray(new CTHyperlink[0]);
			for (int i = 0; i < hyperlinksarr.length; i++) {
				CTHyperlink hyperlink = hyperlinksarr[i];
				tempHyperLinkValue = hyperlink.getRArray(0).getTArray(0).getStringValue();
				paragraph.getCTP().removeHyperlink(0);
			}
			if (!tempHyperLinkValue.equalsIgnoreCase("")) {
				XWPFRun run = paragraph.createRun();
				run.setText(tempHyperLinkValue);
			}
		}
		
		//remove hyperlink and replace with text in table in footer
		for (XWPFTable tbl : footer.getTables()) {
			for (XWPFTableRow row : tbl.getRows()) {
				for (XWPFTableCell cell : row.getTableCells()) {
					for (XWPFParagraph p : cell.getParagraphs()) {
						List<CTHyperlink> hyperlinks = p.getCTP().getHyperlinkList();
						String tempHyperLinkValue = "";
						CTHyperlink[] hyperlinksarr = hyperlinks.toArray(new CTHyperlink[0]);
						for (int i = 0; i < hyperlinksarr.length; i++) {
							CTHyperlink hyperlink = hyperlinksarr[i];
							tempHyperLinkValue = hyperlink.getRArray(0).getTArray(0).getStringValue();
							p.getCTP().removeHyperlink(0);
						}
						if (!tempHyperLinkValue.equalsIgnoreCase("")) {
							XWPFRun run = p.createRun();
							run.setText(tempHyperLinkValue);
						}
					}
				}
			}
		}
	}
}
