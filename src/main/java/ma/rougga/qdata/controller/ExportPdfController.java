package ma.rougga.qdata.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import java.text.Bidi;
import java.util.Map;

import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.report.GblTableController;
import ma.rougga.qdata.modal.GblRow;

public class ExportPdfController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportPdfController.class);
    TitleController tc = new TitleController();
    AgenceController ac = new AgenceController();

    public ExportPdfController() {

    }

   

    // PDF
    public boolean exportGblPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        logger.info("Printing PDF...");
        try {
            // Initialize variables
            String filename = CfgHandler.getRandomName() + ".pdf";
            GblTableController gbl = new GblTableController();
            String tableName = tc.getTitleByType("gbl") == null ? "Rapport" : tc.getTitleByType("gbl").getValue();
            String title = tableName + " Du " + date1 + " Au " + date2;
            List<Map> gblTable = new GblTableController().getTableAsList(date1, date2, dbs);

            if (gblTable.isEmpty()) {
                return false;
            } else {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                ServletOutputStream outStream = response.getOutputStream();

                PdfWriter writer = new PdfWriter(outStream);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Load Arabic font
                PdfFont font = PdfFontFactory.createFont("resources/Amiri-Regular.ttf",  PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

                // Enable Arabic text shaping (important for connected letters)
                //document.setProperty(1, new PdfCalligraph());

                // Set PDF metadata
                pdf.getDocumentInfo().setTitle(title);
                pdf.getDocumentInfo().setAuthor(CfgHandler.COMPANY);
                pdf.getDocumentInfo().setSubject(title);
                pdf.getDocumentInfo().setCreator(CfgHandler.APP + " " + CfgHandler.VERSION);

                // Add title
                Paragraph preface = new Paragraph(title)
                        .setFont(font)
                        .setFontSize(20)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold();
                document.add(preface);

                // Define table structure
                String[] cols = gbl.getGblCols();
                float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
                Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

                // Add table headers
                for (String col : cols) {
                    table.addHeaderCell(new Cell()
                            .add(new Paragraph(col).setFont(font).setBold())
                            .setTextAlignment(TextAlignment.CENTER)
                            .setBackgroundColor(new DeviceRgb(173, 216, 230)) // Light blue color
                            .setBorder(new SolidBorder(1))
                            .setVerticalAlignment(VerticalAlignment.MIDDLE));
                }

                // Loop through agencies
                for (int agenceIndex = 0; agenceIndex < gblTable.size(); agenceIndex++) {
                    Map agence = gblTable.get(agenceIndex);
                    List<GblRow> services = (ArrayList<GblRow>) agence.get("services");

                    for (int i = 0; i < services.size(); i++) {
                        GblRow service = services.get(i);
                        Cell cell = new Cell();

                        if (i == 0) {
                            cell.setBackgroundColor(new DeviceRgb(211, 211, 211)) // Light gray
                                    .setTextAlignment(TextAlignment.CENTER)
                                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
                        }
                        if (i == services.size() - 1) {
                            cell.setBackgroundColor(new DeviceRgb(255, 255, 153)); // Yellow
                        }

                        table = gbl.getPdfTable(service, table, font, cell);
                    }
                }

                // Add table to document
                document.add(table);

                // Add footer with date
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Paragraph footer = new Paragraph(sdf.format(new Date()) + " - " + CfgHandler.APP + " v" + CfgHandler.VERSION)
                        .setFont(font)
                        .setFontSize(7)
                        .setItalic()
                        .setTextAlignment(TextAlignment.RIGHT);
                document.add(footer);

                document.close();
                outStream.flush();
                outStream.close();
                return true;
            }

        } catch (Exception e) {
            logger.error("exportGBL: {}", e.getMessage());
            return false;
        }

    }
}
