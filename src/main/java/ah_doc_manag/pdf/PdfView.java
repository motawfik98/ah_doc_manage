package ah_doc_manag.pdf;


import ah_doc_manag.model.ReceivedLetter;
import ah_doc_manag.model.SentLetter;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

public class PdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String letterType = (String) model.get("type");
        String[] columns = (String[]) model.get("columns");
        int[] widths = (int[]) model.get("widths");
        int numberOfColumns = columns.length;

        PdfPTable table = new PdfPTable(numberOfColumns);
        table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        table.setWidthPercentage(100);
        table.setWidths(widths);


        String font = "E:/ah_doc_manag_java/src/main/resources/static/fonts/Tahoma.ttf";
        Font whiteArabicFont = FontFactory.getFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        whiteArabicFont.setColor(BaseColor.WHITE);
        Font blackArabicFont = FontFactory.getFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        cell.setPadding(5);

        for (String columnName : columns) {
            cell.setPhrase(new Phrase(columnName, whiteArabicFont));
            table.addCell(cell);
        }
        table.setHeaderRows(1);

        if (letterType.equals("sent")) {
            Iterable<SentLetter> sentLetters = (Iterable<SentLetter>) model.get("sentLetters");
            for (SentLetter letter : sentLetters) {
                table.addCell(new PdfPCell(new Paragraph(letter.getNumber() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getDate() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getYear() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getSubject() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getSentTo() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getMessage() + "", blackArabicFont)));
            }
        } else {
            Iterable<ReceivedLetter> receivedLetters = (Iterable<ReceivedLetter>) model.get("receivedLetters");
            for (ReceivedLetter letter : receivedLetters) {
                table.addCell(new PdfPCell(new Paragraph(letter.getNumber() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getDate() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getYear() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getSenderName() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getDepartmentSentFrom() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getSentLetterNumber() + "", blackArabicFont)));
                table.addCell(new PdfPCell(new Paragraph(letter.getMessage() + "", blackArabicFont)));
            }

        }


        document.add(table);
    }

//    @Override
//    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String fileName = (String) model.get("fileName");
//        response.setHeader("Content-Disposition", "inline");
//
//
//        String letterType = (String) model.get("type");
//        List<SentLetter> sentLetters = null;
//        List<ReceivedLetter> receivedLetters = null;
//
//        if(letterType.equalsIgnoreCase("sent")) {
//            sentLetters = (List<SentLetter>) model.get("letters");
//        } else {
//            receivedLetters = (List<ReceivedLetter>) model.get("letters");
//        }
//        String[] columns = (String[]) model.get("columns");
//
//        document.add(new Paragraph(fileName));
//
//        PdfPTable table = new PdfPTable(columns.length);
//        table.setWidthPercentage(100.0f);
//        table.setSpacingBefore(10);
//
//        // define font for the table header row
//        Font font = FontFactory.getFont(FontFactory.TIMES);
//        font.setColor(BaseColor.WHITE);
//
//
//        // define table header cell
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(BaseColor.DARK_GRAY);
//        cell.setPadding(5);
//
//        for(String column : columns) {
//            cell.setPhrase(new Phrase(column, font));
//            table.addCell(cell);
//        }
//
//        if(letterType.equalsIgnoreCase("sent")) {
//            for(SentLetter sentLetter : sentLetters) {
//                table.addCell(sentLetter.getDepartment().getName() + "");
//                table.addCell(sentLetter.getNumber() + "");
//                table.addCell(sentLetter.getDate().toString());
//                table.addCell(sentLetter.getSubject());
//                table.addCell(sentLetter.getSentTo());
//                table.addCell(sentLetter.getMessage());
//            }
//        } else {
//            for(ReceivedLetter receivedLetter : receivedLetters) {
//                table.addCell(receivedLetter.getDepartment().getName() + "");
//                table.addCell(receivedLetter.getNumber() + "");
//                table.addCell(receivedLetter.getDate().toString());
//                table.addCell(receivedLetter.getSubject());
//                table.addCell(receivedLetter.getSenderName());
//                table.addCell(receivedLetter.getMessage());
//            }
//
//        }
//
//        document.add(table);
//    }
}
