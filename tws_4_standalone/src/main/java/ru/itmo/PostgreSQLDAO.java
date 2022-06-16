package ru.itmo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {
    public List<Loyalty> getAll() {
        List<Loyalty> loyalties = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from spbso_loyalty");
            while (rs.next()) {

                int id = rs.getInt("id");
                String spbsoId = rs.getString("spbso");
                String fullName = rs.getString("name");
                String brigade = rs.getString("brigade");
                String event = rs.getString("event");
                String cash = rs.getString("cash");

                Loyalty loyalty = new Loyalty(id, spbsoId,fullName,event,cash,brigade);
                loyalties.add(loyalty);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return loyalties;
    }
    
    public String createLoyalty(int spbsoID, String name, String brigade, String event, int cash) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            System.out.println("INSERT INTO spbso_loyalty(spbso, name, event, cash, brigade) values ('" +
                    spbsoID + "', '" + name + "', " + event + ", " + cash + ", '" + brigade + "');");
            int rs = stmt.executeUpdate("INSERT INTO spbso_loyalty(spbso, name, event, cash, brigade) values ('" +
                    spbsoID + "', '" + name + "', '" + event + "', '" + cash + "', '" + brigade + "');");
            status = Integer.toString(rs);

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    } 
    
    public String deleteLoyalty(int rowId) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            System.out.println("DELETE FROM spbso_loyalty WHERE id='" + rowId + "';");
            int rs = stmt.executeUpdate("DELETE FROM spbso_loyalty WHERE id='" + rowId + "';");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }
    public String updateLoyalty(int rowId, List<String> updateArgs) {
        String status = "0";
        String updateFields = String.join(", ", updateArgs);

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE spbso_loyalty SET " + updateFields + " WHERE id=" + rowId + ";");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }
}
