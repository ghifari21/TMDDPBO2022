/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : Floor.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package model for floor object
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package Model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import ViewModel.Game;

/**
 *
 * @author Ghifari Octaverin
 */
public class Floor extends GameObject{
    private BufferedImage img;  // Buffer Image

    // Constructor
    public Floor(int x, int y, ID id) {
        super(x, y, id);
        try {
            // Read Image
            img = ImageIO.read(new File("resources/floor.png").getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Update Floor per Tick
    @Override
    public void tick() {
        x += vel_x;
        y += vel_y;
    }
    
    // Render Floor
    @Override
    public void render(Graphics g) {
        g.drawImage(img, x, y, Game.WIDTH, 50, null);
    }
}
