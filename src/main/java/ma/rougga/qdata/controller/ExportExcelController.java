package ma.rougga.qdata.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;

import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.controller.report.EmpSerTableController;
import ma.rougga.qdata.controller.report.EmpTableController;
import ma.rougga.qdata.controller.report.GblTableController;
import ma.rougga.qdata.controller.report.GchSerTableController;
import ma.rougga.qdata.controller.report.GchTableController;
import ma.rougga.qdata.controller.report.GlaTableController;
import ma.rougga.qdata.controller.report.GltTableController;
import ma.rougga.qdata.controller.report.ThATableController;
import ma.rougga.qdata.controller.report.ThSATableController;
import ma.rougga.qdata.controller.report.ThTTTableController;
import ma.rougga.qdata.controller.report.ThTTableController;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ExportExcelController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ExportExcelController.class);
    AgenceController ac = new AgenceController();
    TitleController tc = new TitleController();

    public ExportExcelController() {

    }

    // EXCEL
    public boolean exportGblExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getGblTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            Map data = new HashMap();
            
            GblTableController gc = new GblTableController();
            List<Map> gblTable = gc.getTableAsList(date1, date2, dbs);
            
            
            if (!gblTable.isEmpty()) {
                beans.put("agences", gblTable);
                beans.put("title", tc.getTitleByType("gbl").getValue() + " Du " + date1 + " Au " + date2);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean exportEmpExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            EmpTableController emp = new EmpTableController();
            List<Map> Table = emp.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", tc.getTitleByType("emp").getValue() + " Du " + date1 + " Au " + date2);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
  
    public boolean exportEmpSerExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("empser") == null ?  "Rapport employée-service" : tc.getTitleByType("empser").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + ".xlsx";
            String path = new CfgHandler(request).getEmpServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            EmpSerTableController emp = new EmpSerTableController();
            List<Map> Table = emp.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
  
    
    public boolean exportGchExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("gch") == null ?  "Rapport guichet" : tc.getTitleByType("gch").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getGchTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            GchTableController Gch = new GchTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
  
    
    public boolean exportGchSerExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("gchser") == null ?  "Rapport guichet-service" : tc.getTitleByType("gchser").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getGchServTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            GchSerTableController Gch = new GchSerTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportGlaExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("gla") == null ?  "Rapport Grille d'attente" : tc.getTitleByType("gla").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getGlaTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            GlaTableController Gch = new GlaTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportGltExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("glt") == null ?  "Rapport Grille de traitement" : tc.getTitleByType("glt").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getGltTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            GltTableController Gch = new GltTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportThTExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("tht") == null ?  "Rapport Nombre de tickets edités" : tc.getTitleByType("tht").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            ThTTableController Gch = new ThTTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportThTTExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("thtt") == null ?  "Rapport Nombre de tickets traités" : tc.getTitleByType("thtt").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            ThTTTableController Gch = new ThTTTableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportThAExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("tha") == null ?  "Rapport Nombre de tickets absents" : tc.getTitleByType("tha").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            ThATableController Gch = new ThATableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
    public boolean exportThSAExcel(HttpServletResponse response, HttpServletRequest request, String date1, String date2,
            String[] dbs) {

        try {
            String rapportTitle = tc.getTitleByType("thsa") == null ?  "Rapport Nombre de tickets sans affectation" : tc.getTitleByType("thsa").getValue() ;
            String title = rapportTitle + " Du " + date1 + " Au " + date2;
            XLSTransformer transformer = new XLSTransformer();
            String filename = CfgHandler.getRandomName() + "-" +rapportTitle + ".xlsx";
            String path = new CfgHandler(request).getNdtTempExcel();
            InputStream stream = new FileInputStream(new File(path));
            Map beans = new HashMap();
            
            ThSATableController Gch = new ThSATableController();
            List<Map> Table = Gch.getTableAsList(date1, date2, dbs);
            
            
            if (!Table.isEmpty()) {
                beans.put("agences", Table);
                beans.put("title", title);

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
                Workbook workbook = transformer.transformXLS(stream, beans);
                OutputStream os = (OutputStream) response.getOutputStream();
                workbook.write(os);
                os.flush();
                os.close();
                stream.close();
                return true;
            } else {
                stream.close();
                return false;
            }

        } catch (IOException | ParsePropertyException | InvalidFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
    
}
