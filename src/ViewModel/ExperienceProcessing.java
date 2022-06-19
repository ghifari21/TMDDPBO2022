/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : ExperienceProcessing.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package viewmodel for processing all data from database to table in view
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package ViewModel;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Model.ExperienceTable;
import Model.Experience;

/**
 *
 * @author Ghifari Octaverin
 */
public class ExperienceProcessing {
    private String error; // for storing error message
    private ExperienceTable table;  // a class for accessing query
    private ArrayList<Experience> data; // for storing result from query
    
    // Constructor
    public ExperienceProcessing() {
        try {
            // Initializing Table Object and List
            table = new ExperienceTable();
            data = new ArrayList<Experience>();
        } catch(Exception e) {
            error = e.toString();
        }
    }
    
    // Read Data From Database And Return It As DefaultTableModel
    public DefaultTableModel readData() {
        DefaultTableModel dataTbl = null;
        try {
            // Getting All Data From Experience Table
            Object[] column = {"Username", "Fall", "Adapt"};
            dataTbl = new DefaultTableModel(null, column);
            table.getData();
            while (table.getResult().next()) {
                // Taking All Query Result
                Experience exp = new Experience();
                exp.setUsername(table.getResult().getString(1));
                exp.setAdapt(table.getResult().getInt(2));
                exp.setFall(table.getResult().getInt(3));
                
                Object[] row = new Object[3];
                row[0] = exp.getUsername();
                row[1] = exp.getFall();
                row[2] = exp.getAdapt();
                
                // Add Data to List
                dataTbl.addRow(row);
                data.add(exp);
            }
            // Close Result
            table.closeResult();

            // Close Database Connection
            table.closeConnection();
        } catch(Exception e) {
            error = e.toString();
        }

        return dataTbl;
    }

    // Check Username is Exist in Database
    public boolean isDataExist(String username) {
        boolean result = false;
        try {
            table.getData();
            while (table.getResult().next()) {
                if (table.getResult().getString(1).equals(username)) {
                    result = true;
                    break;
                }
            }
        } catch (Exception e) {
            error = e.toString();
        }

        return result;
    }

    // Get Data
    public void getData(String username) {
        try {
            table.getDetailData(username);
            Experience exp = new Experience();
            table.getResult().next();
            exp.setUsername(table.getResult().getString(1));
            exp.setAdapt(table.getResult().getInt(2));
            exp.setFall(table.getResult().getInt(3));

            data.add(exp);
            
            table.closeResult();
            table.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Store Data
    public void storeData(String username, int adapt, int fall) {
        try {
            Experience exp = new Experience();
            exp.setUsername(username);
            exp.setAdapt(adapt);
            exp.setFall(fall);
            
            // Checking Is Username Already Exist in Database or Not
            if (isDataExist(username)) {
                table.updateData(exp);
            } else {
                table.insertData(exp);
            }
            table.closeConnection();
        } catch (Exception e) {
            error = e.toString();
        }
    }

    // Get Data
    public String getUsername(int i) {
        return data.get(i).getUsername();
    }

    public int getAdapt(int i) {
        return data.get(i).getAdapt();
    }

    public int getFall(int i) {
        return data.get(i).getFall();
    }

    public int getSize() {
        return data.size();
    }

    public String getError() {
        return this.error;
    }
}
