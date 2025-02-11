package ma.rougga.qdata.controller;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ma.rougga.qdata.TableGenerator;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.Export;
import ma.rougga.qdata.Tools;
import ma.rougga.qdata.handler.TitleHandler;

public class ExportPdfController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportPdfController.class);

    public ExportPdfController() {

    }

    // PDF
    public int exportGblPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGblTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGblTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getGblCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportEmpPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getEmpTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateEmpTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getEmpCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportEmpServicePDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getEmpSerTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateEmpServiceTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getEmpServiceCols();
                float[] columnWidths = { 6, 7, 7, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGchPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGchTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGchTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getGchCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGchServicePDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGchServTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGchServiceTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getGchServiceCols();
                float[] columnWidths = { 6, 5, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setBackgroundColor(BaseColor.YELLOW);
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                table.addCell(c);
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getNdtTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateNdtTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getNdtCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if ((i == 0) || (i == 1) || ((i > 8) && (i < 22))) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdttPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getNdttTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateNdttTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getNdtCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if ((i == 0) || (i == 1) || ((i > 8) && (i < 22))) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getNdtaTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateNdtaTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getNdtCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if ((i == 0) || (i == 1) || ((i > 8) && (i < 22))) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtsaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getNdtsaTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateNdtsaTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor(CfgHandler.COMPANY);
                document.addTitle(title);
                document.addCreator(CfgHandler.APP);
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getNdtCols();
                float[] columnWidths = { 6, 7, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    if ((i == 0) || (i == 1) || ((i > 8) && (i < 22))) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                if ((j == 2) || (j == 3) || ((j > 10) && (j < 24))) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGlaPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGlaTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGlaTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getGlaCols();
                float[] columnWidths = { 7, 7, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {

                    // if colon is 0-5min
                    if (i != 8) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {

                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {

                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGltPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = CfgHandler.getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGltTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGltTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                // fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                // metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                // title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                // table
                String[] cols = tbl.getGltCols();
                float[] columnWidths = { 7, 7, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 };
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                // filling table with headers
                for (int i = 0; i < cols.length; i++) {

                    // if colon is 0-5min
                    if (i != 8) {
                        PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                        c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        c1.setBackgroundColor(BaseColor.CYAN);
                        table.addCell(c1);
                    }
                }
                table.setHeaderRows(1);

                // filling table with data
                List<List<ArrayList>> st = Tools.splitAgences(gtable, 3);
                // agence loop
                for (int a = 0; a < st.size(); a++) {

                    PdfPCell c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(0).get(2)), tableCell));
                    c.setRowspan(st.get(a).size());
                    c.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(c);
                    // row loop
                    for (int i = 0; i < st.get(a).size(); i++) {
                        if (Objects.equals(st.get(a).get(i).get(3), "Sous-Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {

                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else if (Objects.equals(st.get(a).get(i).get(3), "Totale")) {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {

                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setBackgroundColor(BaseColor.YELLOW);
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        } else {
                            for (int j = 3; j < st.get(a).get(i).size(); j++) {
                                // if colon is 0-5min
                                if (j != 10) {
                                    c = new PdfPCell(new Phrase(String.valueOf(st.get(a).get(i).get(j)), tableCell));
                                    c.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                    table.addCell(c);
                                }
                            }
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                // footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + " - " + CfgHandler.APP
                        + " v" + CfgHandler.VERSION, footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                // sending pdf
                byte[] bytes = byteArrayOutputStream.toByteArray();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                response.setContentLength(bytes.length);
                ServletOutputStream outStream = response.getOutputStream();
                outStream.write(bytes, 0, bytes.length);
                outStream.flush();
                outStream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

}
