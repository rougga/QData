package ma.rougga.qdata;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.controller.UpdateController;
import ma.rougga.qdata.modal.Agence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stats {

    private static final Logger logger = LoggerFactory.getLogger(Stats.class);
    private String date1;
    private String date2;

    public Stats() {
    }

    public String getFormatedTime(Float Sec) {
        int hours = (int) (Sec / 3600);
        int minutes = (int) ((Sec % 3600) / 60);
        int seconds = (int) (Sec % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public long getTotalTicket() {
        long nb = 0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT SUM(nb_t)  "
                    + " FROM rougga_gbl_table "
                    + " WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getLong(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public long getDealTicket() {
        long nb = 0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT SUM(nb_tt) "
                    + "FROM rougga_gbl_table  "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getLong(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public long getAbsentTicket() {
        long nb = 0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT SUM(nb_a)  "
                    + "FROM rougga_gbl_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getLong(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public long getWaitingTicket() {
        long waitingtickets = 0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT SUM(nb_sa) "
                    + "FROM rougga_gbl_table  "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                waitingtickets = r.getLong(1);
            }
            con.close();
            return waitingtickets;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return waitingtickets;
        }
    }

    public Double getMaxWaitTime() {
        Double nb = 0.0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT MAX(avgSec_A)  "
                    + "FROM rougga_gbl_table  "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getDouble(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public Double getAvgWaitTime() {
        Double nb = 0.0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT AVG(avgSec_A)  "
                    + "FROM rougga_gbl_table  "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getDouble(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public Double getMaxDealTime() {
        Double nb = 0.0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT MAX(avgSec_T) "
                    + "FROM rougga_gbl_table "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getDouble(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public Double getAvgDealTime() {
        Double nb = 0.0;
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "SELECT AVG(avgSec_T)  "
                    + "FROM rougga_gbl_table  "
                    + "WHERE TO_DATE(TO_CHAR(date, 'YYYY-MM-DD'), 'YYYY-MM-DD') = CURRENT_DATE;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            if (r.next()) {
                nb = r.getDouble(1);
            }
            con.close();
            return nb;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return nb;
        }
    }

    public List getWaitingTicketsByService(String d1, String d2, HttpServletResponse res) throws IOException {
        List<ArrayList> table = new ArrayList<>();
        try {
            setDate1(d1);
            setDate2(d2);
            PgConnection con = new PgConnection();
            String SQL = "select "
                    + "b.name,"
                    + "(select count(*) from t_ticket t where t.biz_type_id=b.id and t.status=0 and to_date(to_char(t.ticket_time,'YYYY-MM-DD'),'YYYY-MM-DD')  BETWEEN TO_DATE(?,'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD') )as nb_t"
                    + " from "
                    + " t_biz_type b;";
            PreparedStatement s = con.getStatement().getConnection().prepareStatement(SQL);
            s.setString(1, getDate1());
            s.setString(2, getDate2());
            ResultSet r = s.executeQuery();
            con.closeConnection();
            while (r.next()) {
                ArrayList row = new ArrayList();
                row.add(r.getString("name"));
                row.add(r.getLong("nb_t"));
                table.add(row);
            }

        } catch (Exception e) {
            res.sendRedirect("./home.jsp?err=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
        return table;
    }

    public void setDate1(String date1) {
        if (Objects.equals(date1, null)) {
            this.date1 = CfgHandler.format.format(new Date());
        } else {
            this.date1 = date1;
        }

    }

    public void setDate2(String date2) {
        if (Objects.equals(date2, null)) {
            this.date2 = CfgHandler.format.format(new Date());
        } else {
            this.date2 = date2;
        }

    }

    public SimpleDateFormat getFormat() {
        return CfgHandler.format;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public Map getTotalDealChart() {
        Map chart = new HashMap();
        String lables = "[";
        String data = "[";
        try {
            Connection con = new CPConnection().getConnection();
            String SQL = "select  "
                    + "a.id, "
                    + "a.name, "
                    + "sum(gbl.nb_t) as nb_t "
                    + "from rougga_gbl_table gbl,rougga_agences a "
                    + "where gbl.id_agence=a.id  "
                    + "GROUP by a.id,a.name;";
            ResultSet r = con.createStatement().executeQuery(SQL);
            while (r.next()) {
                lables += "'" + r.getString("name") + "',";
                data += r.getLong("nb_t") + ",";
            }

            con.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        lables += "]";
        data += "]";
        chart.put("lables", lables);
        chart.put("data", data);
        return chart;
    }
}
