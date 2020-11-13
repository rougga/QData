package main;

import com.google.common.collect.HashBiMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.xml.sax.SAXException;

public class Export {

    public Export() {
    }

    public String getRandomName() {
        return (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()) + (new Random()).nextInt(100000);
    }

    public Map exportGblExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGblTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGblTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportEmpExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateEmpTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("emp", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("emp", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportEmpServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateEmpServiceTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("emp", gtable.get(i).get(0));
                    row.put("service", gtable.get(i).get(1));
                    row.put("nb_t", gtable.get(i).get(2));
                    row.put("nb_tt", gtable.get(i).get(3));
                    row.put("nb_ta", gtable.get(i).get(4));
                    row.put("nb_ttl1", gtable.get(i).get(5));
                    row.put("nb_tsa", gtable.get(i).get(6));
                    row.put("perapt", gtable.get(i).get(7));
                    row.put("perttl1pt", gtable.get(i).get(8));
                    row.put("persapt", gtable.get(i).get(9));
                    row.put("avga", gtable.get(i).get(10));
                    row.put("nb_ca", gtable.get(i).get(11));
                    row.put("perca", gtable.get(i).get(12));
                    row.put("avgt", gtable.get(i).get(13));
                    row.put("nb_ct", gtable.get(i).get(14));
                    row.put("perct", gtable.get(i).get(15));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("emp", gtable.get(gtable.size() - 1).get(0));
                subT.put("service", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(5));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(6));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(7));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(8));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(9));
                subT.put("avga", gtable.get(gtable.size() - 1).get(10));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(11));
                subT.put("perca", gtable.get(gtable.size() - 1).get(12));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(13));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(14));
                subT.put("perct", gtable.get(gtable.size() - 1).get(15));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./report.jsp?err=" + e.getMessage());
        }
        return null;
    }

    public Map exportGchExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGchTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("gch", gtable.get(i).get(0));
                    row.put("nb_t", gtable.get(i).get(1));
                    row.put("nb_tt", gtable.get(i).get(2));
                    row.put("nb_ta", gtable.get(i).get(3));
                    row.put("nb_ttl1", gtable.get(i).get(4));
                    row.put("nb_tsa", gtable.get(i).get(5));
                    row.put("perapt", gtable.get(i).get(6));
                    row.put("perttl1pt", gtable.get(i).get(7));
                    row.put("persapt", gtable.get(i).get(8));
                    row.put("avga", gtable.get(i).get(9));
                    row.put("nb_ca", gtable.get(i).get(10));
                    row.put("perca", gtable.get(i).get(11));
                    row.put("avgt", gtable.get(i).get(12));
                    row.put("nb_ct", gtable.get(i).get(13));
                    row.put("perct", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("gch", gtable.get(gtable.size() - 1).get(0));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(5));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(6));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(7));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(8));
                subT.put("avga", gtable.get(gtable.size() - 1).get(9));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(10));
                subT.put("perca", gtable.get(gtable.size() - 1).get(11));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(12));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(13));
                subT.put("perct", gtable.get(gtable.size() - 1).get(14));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGchServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGchServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("date1", date1);
            data.put("date2", date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGchServiceTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("gch", gtable.get(i).get(0));
                    row.put("service", gtable.get(i).get(1));
                    row.put("nb_t", gtable.get(i).get(2));
                    row.put("nb_tt", gtable.get(i).get(3));
                    row.put("nb_ta", gtable.get(i).get(4));
                    row.put("nb_ttl1", gtable.get(i).get(5));
                    row.put("nb_tsa", gtable.get(i).get(6));
                    row.put("perapt", gtable.get(i).get(7));
                    row.put("perttl1pt", gtable.get(i).get(8));
                    row.put("persapt", gtable.get(i).get(9));
                    row.put("avga", gtable.get(i).get(10));
                    row.put("nb_ca", gtable.get(i).get(11));
                    row.put("perca", gtable.get(i).get(12));
                    row.put("avgt", gtable.get(i).get(13));
                    row.put("nb_ct", gtable.get(i).get(14));
                    row.put("perct", gtable.get(i).get(15));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("gch", gtable.get(gtable.size() - 1).get(0));
                subT.put("service", gtable.get(gtable.size() - 1).get(1));
                subT.put("nb_t", gtable.get(gtable.size() - 1).get(2));
                subT.put("nb_tt", gtable.get(gtable.size() - 1).get(3));
                subT.put("nb_ta", gtable.get(gtable.size() - 1).get(4));
                subT.put("nb_ttl1", gtable.get(gtable.size() - 1).get(5));
                subT.put("nb_tsa", gtable.get(gtable.size() - 1).get(6));
                subT.put("perapt", gtable.get(gtable.size() - 1).get(7));
                subT.put("perttl1pt", gtable.get(gtable.size() - 1).get(8));
                subT.put("persapt", gtable.get(gtable.size() - 1).get(9));
                subT.put("avga", gtable.get(gtable.size() - 1).get(10));
                subT.put("nb_ca", gtable.get(gtable.size() - 1).get(11));
                subT.put("perca", gtable.get(gtable.size() - 1).get(12));
                subT.put("avgt", gtable.get(gtable.size() - 1).get(13));
                subT.put("nb_ct", gtable.get(gtable.size() - 1).get(14));
                subT.put("perct", gtable.get(gtable.size() - 1).get(15));

                data.put("table", table);
                data.put("subt", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de transaction Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {
                Map row = new HashMap();
                for (int i = 0; i < gtable.get(0).size(); i++) {
                    row.put("h" + i, gtable.get(0).get(i));
                }
                table.add(row);
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdttExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets traités Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdttTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {
                Map row = new HashMap();
                for (int i = 0; i < gtable.get(0).size(); i++) {
                    row.put("h" + i, gtable.get(0).get(i));
                }
                table.add(row);
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets absents Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtaTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {
                Map row = new HashMap();
                for (int i = 0; i < gtable.get(0).size(); i++) {
                    row.put("h" + i, gtable.get(0).get(i));
                }
                table.add(row);
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportNdtsaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Nombre de tickets sans affectation Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateNdtsaTable(request, date1, date2, db);

            List table = new ArrayList();
            if (gtable.size() > 0) {
                Map row = new HashMap();
                for (int i = 0; i < gtable.get(0).size(); i++) {
                    row.put("h" + i, gtable.get(0).get(i));
                }
                table.add(row);
                data.put("table", table);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGlaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGlaTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Grille d'attente Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGlaTable(request, date1, date2, db);
            List table = new ArrayList();
            if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("t0_15", gtable.get(i).get(1));
                    row.put("t15_30", gtable.get(i).get(2));
                    row.put("t30_1", gtable.get(i).get(3));
                    row.put("t1_130", gtable.get(i).get(4));
                    row.put("t130_2", gtable.get(i).get(5));
                    row.put("t2_5", gtable.get(i).get(6));
                    row.put("t5_10", gtable.get(i).get(8));
                    row.put("t10_20", gtable.get(i).get(9));
                    row.put("t20_30", gtable.get(i).get(10));
                    row.put("t30_45", gtable.get(i).get(11));
                    row.put("t45_50", gtable.get(i).get(12));
                    row.put("t50", gtable.get(i).get(13));
                    row.put("total", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("t0_15", gtable.get(gtable.size() - 1).get(1));
                subT.put("t15_30", gtable.get(gtable.size() - 1).get(2));
                subT.put("t30_1", gtable.get(gtable.size() - 1).get(3));
                subT.put("t1_130", gtable.get(gtable.size() - 1).get(4));
                subT.put("t130_2", gtable.get(gtable.size() - 1).get(5));
                subT.put("t2_5", gtable.get(gtable.size() - 1).get(6));
                subT.put("t5_10", gtable.get(gtable.size() - 1).get(8));
                subT.put("t10_20", gtable.get(gtable.size() - 1).get(9));
                subT.put("t20_30", gtable.get(gtable.size() - 1).get(10));
                subT.put("t30_45", gtable.get(gtable.size() - 1).get(11));
                subT.put("t45_50", gtable.get(gtable.size() - 1).get(12));
                subT.put("t50", gtable.get(gtable.size() - 1).get(13));
                subT.put("total", gtable.get(gtable.size() - 1).get(14));
                
                data.put("table", table);
                data.put("t", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

    public Map exportGltExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2) throws IOException, ClassNotFoundException, FileNotFoundException, ParserConfigurationException, SAXException, Exception {

        XLSTransformer transformer = new XLSTransformer();
        try {
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGltTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Grille traitement Du " + date1 + " Au " + date2);
            String db = request.getSession().getAttribute("db") + "";
            data.put("db", db);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList<String>> gtable = tbl.generateGltTable(request, date1, date2, db);
            List table = new ArrayList();
             if (gtable.size() > 0) {
                for (int i = 0; i < gtable.size() - 1; i++) {
                    Map row = new HashMap();
                    row.put("service", gtable.get(i).get(0));
                    row.put("t0_15", gtable.get(i).get(1));
                    row.put("t15_30", gtable.get(i).get(2));
                    row.put("t30_1", gtable.get(i).get(3));
                    row.put("t1_130", gtable.get(i).get(4));
                    row.put("t130_2", gtable.get(i).get(5));
                    row.put("t2_5", gtable.get(i).get(6));
                    row.put("t5_10", gtable.get(i).get(8));
                    row.put("t10_20", gtable.get(i).get(9));
                    row.put("t20_30", gtable.get(i).get(10));
                    row.put("t30_45", gtable.get(i).get(11));
                    row.put("t45_50", gtable.get(i).get(12));
                    row.put("t50", gtable.get(i).get(13));
                    row.put("total", gtable.get(i).get(14));
                    table.add(row);
                }
                Map subT = new HashMap();
                subT.put("service", gtable.get(gtable.size() - 1).get(0));
                subT.put("t0_15", gtable.get(gtable.size() - 1).get(1));
                subT.put("t15_30", gtable.get(gtable.size() - 1).get(2));
                subT.put("t30_1", gtable.get(gtable.size() - 1).get(3));
                subT.put("t1_130", gtable.get(gtable.size() - 1).get(4));
                subT.put("t130_2", gtable.get(gtable.size() - 1).get(5));
                subT.put("t2_5", gtable.get(gtable.size() - 1).get(6));
                subT.put("t5_10", gtable.get(gtable.size() - 1).get(8));
                subT.put("t10_20", gtable.get(gtable.size() - 1).get(9));
                subT.put("t20_30", gtable.get(gtable.size() - 1).get(10));
                subT.put("t30_45", gtable.get(gtable.size() - 1).get(11));
                subT.put("t45_50", gtable.get(gtable.size() - 1).get(12));
                subT.put("t50", gtable.get(gtable.size() - 1).get(13));
                subT.put("total", gtable.get(gtable.size() - 1).get(14));
                
                data.put("table", table);
                data.put("t", subT);
                beans.put("data", data);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
            } else {
                return null;
            }
            stream.close();
        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, e);
            response.sendRedirect("./print.jsp?err=Erreur!");
        }
        return null;
    }

}
