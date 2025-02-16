//package ma.rougga.qdata.controller;
//
//import com.itextpdf.io.font.PdfEncodings;
//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
//import com.itextpdf.kernel.geom.PageSize;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.LoggerFactory;
//
//import com.itextpdf.kernel.pdf.*;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.*;
//import com.itextpdf.layout.properties.TextAlignment;
//
//import java.util.Map;
//
//import ma.rougga.qdata.CfgHandler;
//import ma.rougga.qdata.controller.report.GblTableController;
//
//public class ExportPdfController {
//
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportPdfController.class);
//    TitleController tc = new TitleController();
//    AgenceController ac = new AgenceController();
//
//    public ExportPdfController() {
//
//    }
//
//   
//
//    // PDF
//    public boolean exportGblPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
//        logger.info("Printing PDF...");
//        try {
//            // Initialize variables
//            String filename = CfgHandler.getRandomName() + ".pdf";
//            GblTableController gbl = new GblTableController();
//            String tableName = tc.getTitleByType("gbl") == null ? "Rapport" : tc.getTitleByType("gbl").getValue();
//            String title = tableName + " Du " + date1 + " Au " + date2;
//            List<Map> gblTable = new GblTableController().getTableAsList(date1, date2, dbs);
//
//            if (gblTable.isEmpty()) {
//                return false;
//            } else {
//                response.setContentType("application/pdf");
//                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//                ServletOutputStream outStream = response.getOutputStream();
//
//                PdfWriter writer = new PdfWriter(outStream);
//                PdfDocument pdf = new PdfDocument(writer);
//                Document document = new Document(pdf,PageSize.A4.rotate());
//                
//               // PdfFont font = PdfFontFactory.createFont("resources/Amiri-Regular.ttf",  PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
//                    
//                // meta 
//                pdf.getDocumentInfo().setTitle(title);
//                pdf.getDocumentInfo().setAuthor(CfgHandler.COMPANY);
//                pdf.getDocumentInfo().setSubject(title);
//                pdf.getDocumentInfo().setCreator(CfgHandler.APP + " " + CfgHandler.VERSION);
//                
//                
//                 PdfFont amiri = PdfFontFactory.createFont(ExportPdfController.class.getClassLoader().getResource("Amiri-Regular.ttf").getPath()
//                         , PdfEncodings.UTF8
//                         , EmbeddingStrategy.FORCE_EMBEDDED
//                 );
//                 
//                 
//                 String test = "IRM / الفحص بالصدى";
//                // Title
//                Paragraph pdfTitle = new Paragraph(test)
//                        .setTextAlignment(TextAlignment.CENTER)
//                        .setFontSize(20)
//                        .setFont(amiri);
//                document.add(pdfTitle);
//                
//                //Table
//                
//                
//                
//                
//                // Add footer with date
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                Paragraph footer = new Paragraph(sdf.format(new Date()) + " - " + CfgHandler.APP + " v" + CfgHandler.VERSION)
//                        .setFontSize(7)
//                        .setItalic()
//                        .setTextAlignment(TextAlignment.RIGHT);
//                document.add(footer);
//
//                document.close();
//                outStream.flush();
//                outStream.close();
//                return true;
//            }
//
//        } catch (Exception e) {
//            logger.error("exportGBL: {}", e.getMessage());
//            return false;
//        }
//
//    }
//}
