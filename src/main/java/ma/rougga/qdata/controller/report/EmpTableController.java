package ma.rougga.qdata.controller.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import ma.rougga.qdata.CPConnection;
import ma.rougga.qdata.CfgHandler;
import ma.rougga.qdata.PgConnection;
import ma.rougga.qdata.controller.AgenceController;
import ma.rougga.qdata.modal.Agence;
import ma.rougga.qdata.modal.report.EmpRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpTableController {

    private static final Logger logger = LoggerFactory.getLogger(EmpTableController.class);
    private final AgenceController ac = new AgenceController();

    public EmpTableController() {
    }

    public boolean addRow(EmpRow row) {
        try {
            PgConnection con = new PgConnection();
            String sql = "INSERT INTO rougga_emp_table ("
                    + "id, id_emp, emp_name, nb_t, nb_tt, nb_a, nb_tl1, nb_sa, "
                    + "perApT, PERTL1pt, perSApT, avgSec_A, nb_ca, percapt, "
                    + "avgSec_T, nb_ct, perctpt, date, id_agence) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
            pstmt.setString(1, row.getId().toString());
            pstmt.setString(2, row.getUserId());
            pstmt.setString(3, row.getUserName());
            pstmt.setLong(4, row.getNbT());
            pstmt.setLong(5, row.getNbTt());
            pstmt.setLong(6, row.getNbA());
            pstmt.setLong(7, row.getNbTl1());
            pstmt.setLong(8, row.getNbSa());
            pstmt.setDouble(9, row.getPerApT());
            pstmt.setDouble(10, row.getPerTl1Pt());
            pstmt.setDouble(11, row.getPerSaPt());
            pstmt.setDouble(12, row.getAvgSecA());
            pstmt.setLong(13, row.getNbCa());
            pstmt.setDouble(14, row.getPerCapt());
            pstmt.setDouble(15, row.getAvgSecT());
            pstmt.setLong(16, row.getNbCt());
            pstmt.setDouble(17, row.getPerCtPt());
            pstmt.setString(18, row.getDate());
            pstmt.setString(19, row.getAgenceId());

            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean updateRow(EmpRow row) {
        try {
            PgConnection con = new PgConnection();
            String sql = "UPDATE rougga_emp_table SET "
                    + "id_emp = ?, emp_name = ?, nb_t = ?, nb_tt = ?, nb_a = ?, nb_tl1 = ?, "
                    + "nb_sa = ?, perApT = ?, PERTL1pt = ?, perSApT = ?, avgSec_A = ?, "
                    + "nb_ca = ?, percapt = ?, avgSec_T = ?, nb_ct = ?, perctpt = ?, "
                    + "date = ?, id_agence = ? "
                    + "WHERE id = ?";

            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
            pstmt.setString(1, row.getUserId());
            pstmt.setString(2, row.getUserName());
            pstmt.setLong(3, row.getNbT());
            pstmt.setLong(4, row.getNbTt());
            pstmt.setLong(5, row.getNbA());
            pstmt.setLong(6, row.getNbTl1());
            pstmt.setLong(7, row.getNbSa());
            pstmt.setDouble(8, row.getPerApT());
            pstmt.setDouble(9, row.getPerTl1Pt());
            pstmt.setDouble(10, row.getPerSaPt());
            pstmt.setDouble(11, row.getAvgSecA());
            pstmt.setLong(12, row.getNbCa());
            pstmt.setDouble(13, row.getPerCapt());
            pstmt.setDouble(14, row.getAvgSecT());
            pstmt.setLong(15, row.getNbCt());
            pstmt.setDouble(16, row.getPerCtPt());
            pstmt.setString(17, row.getDate());
            pstmt.setString(18, row.getAgenceId());
            pstmt.setString(19, row.getId().toString());

            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public EmpRow getRowById(String id) {
        EmpRow row = null;
        try {
            PgConnection con = new PgConnection();
            String sql = "SELECT * FROM rougga_emp_table WHERE id = ?";
            PreparedStatement pstmt = con.getStatement().getConnection().prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                row = new EmpRow(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("id_emp"),
                        rs.getString("emp_name"),
                        rs.getString("id_agence"),
                        rs.getString("date"),
                        rs.getLong("nb_t"),
                        rs.getLong("nb_tt"),
                        rs.getLong("nb_a"),
                        rs.getLong("nb_tl1"),
                        rs.getLong("nb_sa"),
                        rs.getDouble("perApT"),
                        rs.getDouble("PERTL1pt"),
                        rs.getDouble("perSApT"),
                        rs.getDouble("avgSec_A"),
                        rs.getLong("nb_ca"),
                        rs.getDouble("percapt"),
                        rs.getDouble("avgSec_T"),
                        rs.getLong("nb_ct"),
                        rs.getDouble("perctpt")
                );
            }

        } catch (ClassNotFoundException | SQLException e) {
            logger.error(e.getMessage());
        }
        return row;
    }

}
