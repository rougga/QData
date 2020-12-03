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
import main.handler.TitleHandler;
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

    public int exportGblExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

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
            List<ArrayList> gtable = tbl.generateGblTable(date1, date2,dbs);
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

    public int exportEmpExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

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
            List<ArrayList> gtable = tbl.generateEmpTable(date1, date2,dbs);
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

    public int exportEmpServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

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
            List<ArrayList> gtable = tbl.generateEmpServiceTable(date1, date2,dbs);
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

    public int exportGchExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

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
            List<ArrayList> gtable = tbl.generateGchTable(date1, date2,dbs);
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

    public int exportGchServiceExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

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
            List<ArrayList> gtable = tbl.generateGchServiceTable(date1, date2,dbs);
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

    public int exportNdtExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: "+new TitleHandler(request).getNdtTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtTable(date1, date2,dbs);
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

    public int exportNdttExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: "+new TitleHandler(request).getNdtTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdttTable(date1, date2,dbs);
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

    public int exportNdtaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: "+new TitleHandler(request).getNdtaTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtaTable(date1, date2,dbs);
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

    public int exportNdtsaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {
        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            //data here
            data.put("title", "Tranche horaire: "+new TitleHandler(request).getNdtsaTitle() + " Du " + date1 + " Au " + date2);

            TableGenerator tbl = new TableGenerator();
            List<ArrayList> gtable = tbl.generateNdtsaTable(date1, date2,dbs);
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

    public int exportGlaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {
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
            List<ArrayList> gtable = tbl.generateGlaTable(date1, date2,dbs);
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

    public int exportGltExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,String[] dbs) {
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
            List<ArrayList> gtable = tbl.generateGltTable(date1, date2,dbs);
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
