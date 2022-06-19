/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : Obstacle.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package model obstacle object
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package Model;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Ghifari Octaverin
 */
public class Obstacle extends GameObject {
    private int idx;
    private String[] colors = {
        "#3B44F6",
        "#4CACBC",
        "#C7D36F",
        "#FF5B00",
        "#A760FF",
        "#EF9F9F",
        "#97C4B8",
        "#C4DDFF",
        "#A64B2A",
        "#EBD671"
    };

    // Constructor
    public Obstacle(int x, int y, ID id, int idx) {
        super(x, y, id);
        this.idx = idx;
    }
    
    // Update Obstacle per Tick
    @Override
    public void tick() {
        x += vel_x;
    }

    // Render Obstacle
    @Override
    public void render(Graphics g) {
        g.setColor(Color.decode(colors[idx]));
        g.fillRect(x, y, 50, 500);
    }
}
