package main;

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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.handler.TitleHandler;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

public class Export {

    public Export() {
    }

    public String getRandomName() {
        return (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()) + (new Random()).nextInt(100000);
    }

    public int exportGblExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGblTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", new TitleHandler(request).getGblTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateGblTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                stream.close();
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGblPDF(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        System.err.println("Printing PDF...");
        try {
            String filename = getRandomName() + ".pdf";
            TableGenerator tbl = new TableGenerator();
            String title = new TitleHandler(request).getGblTitle() + " Du " + date1 + " Au " + date2;
            List<ArrayList> gtable = tbl.generateGblTable(date1, date2, dbs);

            if (gtable != null && gtable.size() > 0) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, byteArrayOutputStream);
                //fonts
                Font H1 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font tableHeader = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                Font tableCell = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
                Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);

                document.open();

                //metadata
                document.addAuthor("ROUGGA");
                document.addTitle(title);
                document.addCreator("QData");
                document.addSubject(title);

                //title
                Paragraph preface = new Paragraph(title, H1);
                preface.setAlignment(Element.ALIGN_CENTER);
                document.add(preface);

                //table
                String[] cols = tbl.getGblCols();
                float[] columnWidths = {6, 7, 2, 2, 2, 2, 2, 3, 3, 3, 4, 2, 3, 4, 2, 3};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.setSpacingBefore(25f);

                table.getDefaultCell().setUseAscender(true);
                table.getDefaultCell().setUseDescender(true);

                //filling table with headers
                for (int i = 0; i < cols.length; i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(cols[i], tableHeader));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    c1.setBackgroundColor(BaseColor.CYAN);
                    table.addCell(c1);
                }
                table.setHeaderRows(1);

                //filling table with data
                int rowCount = 0;
                for (int i = 0; i < gtable.size(); i++) {
                    if (Objects.equals(gtable.get(i).get(3), "Sous-Totale")) {
//                        PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(2)), tableCell));
//                        c.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                        //c.setRowspan(rowCount+1);
//                        rowCount=0;
//                        table.addCell(c);
                        for (int j = 2; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            table.addCell(c);
                        }
                    } else if (Objects.equals(gtable.get(i).get(3), "Totale")) {
                        for (int j = 2; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            c.setBackgroundColor(BaseColor.YELLOW);
                            table.addCell(c);
                        }
                    } else {
                        rowCount++;
                        for (int j = 2; j < gtable.get(i).size(); j++) {
                            PdfPCell c = new PdfPCell(new Phrase(String.valueOf(gtable.get(i).get(j)), tableCell));
                            table.addCell(c);
                        }
                    }

                }
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                document.add(table);

                //footer
                preface = new Paragraph(new SimpleDateFormat("dd-MM-yyyy").format(new Date()), footer);
                preface.setAlignment(Element.ALIGN_RIGHT);
                document.add(preface);

                document.close();

                //sending pdf
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

    public int exportEmpExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here //data here
            data.put("title", new TitleHandler(request).getEmpTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateEmpTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportEmpServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here //data here
            data.put("title", new TitleHandler(request).getEmpSerTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateEmpServiceTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGchExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here //data here
            data.put("title", new TitleHandler(request).getGchTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateGchTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGchServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", new TitleHandler(request).getGchServTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateGchServiceTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: " + new TitleHandler(request).getNdtTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdttExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: " + new TitleHandler(request).getNdtTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdttTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: " + new TitleHandler(request).getNdtaTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtaTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportNdtsaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: " + new TitleHandler(request).getNdtsaTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtsaTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGlaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGlaTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", new TitleHandler(request).getGlaTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateGlaTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    public int exportGltExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2, String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGltTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", new TitleHandler(request).getGltTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateGltTable(date1, date2, dbs);
            List table = new ArrayList();

            if (gtable != null && gtable.size() > 0) {
                for (int i = 0; i < gtable.size(); i++) {
                    Map row = new HashMap();
                    for (int j = 0; j < gtable.get(i).size(); j++) {
                        row.put("d" + j, gtable.get(i).get(j));
                    }
                    table.add(row);
                }

                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return 1;
            } else {
                return 0;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

}
