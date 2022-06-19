/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : Experience.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package model for store all experience data
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package Model;

/**
 *
 * @author Ghifari Octaverin
 */
public class Experience {
    private String username;    // username
    private int adapt;          // adaptation score
    private int fall;           // fall score
    
    public Experience() {
        //
    }
    
    // Setter and Getter
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setAdapt(int adapt) {
        this.adapt = adapt;
    }
    
    public int getAdapt() {
        return this.adapt;
    }
    
    public void setFall(int fall) {
        this.fall = fall;
    }
    
    public int getFall() {
        return this.fall;
    }
}
