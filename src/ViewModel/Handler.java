/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : Handler.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package viewmodel for handling objects
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package ViewModel;

import Model.GameObject;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Ghifari Octaverin
 */
public class Handler {
    LinkedList<GameObject> obj = new LinkedList<>();
    
    // Update Game Object Per Tick
    public void tick() {
        for (int i = 0; i < obj.size(); i++) {
            GameObject tmpObj = obj.get(i);
            tmpObj.tick();
        }
    }
    
    // Render Game Object
    public void render(Graphics g) {
        for (int i = 0; i < obj.size(); i++) {
            GameObject tmpObj = obj.get(i);
            tmpObj.render(g);
        }
    }
    
    // Add Object To List
    public void addObject(GameObject obj) {
        this.obj.add(obj);
    }
    
    // Remove Object From List
    public void removeObject(GameObject obj) {
        this.obj.remove(obj);
    }
    
    // Count List Size
    public int countObject() {
        return this.obj.size();
    }
}
