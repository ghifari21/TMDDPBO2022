/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : ExperienceTable.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package model for accessing texperiences table
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Ghifari Octaverin
 */
public class ExperienceTable extends DB {
    // constructor
    public ExperienceTable() throws Exception, SQLException {
        super();
    }
    
    public void getData() {
        try {
            // getting all data from table
            String query = "SELECT * FROM texperiences";
            createQuery(query);
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public void getDetailData(String username) {
        try {
            // getting specific data from table
            String query = "SELECT * FROM texperiences WHERE username='" + username + "'"; 
            createQuery(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public void insertData(Experience exp) {
        try {
            // input data to database
            String query = "INSERT INTO texperiences VALUES ('" + exp.getUsername() + "', '" + exp.getAdapt() + "', '" + exp.getFall() + "')";
            createUpdate(query);
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public void updateData(Experience exp) {
        try {
            // update data
            String query = "UPDATE texperiences SET adapt=" + exp.getAdapt() + ", fall=" + exp.getFall() + " WHERE username='" + exp.getUsername() + "'";
            createUpdate(query);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
