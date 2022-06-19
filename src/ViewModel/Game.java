/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Filename     : Game.java
 * Programmer   : Ghifari Octaverin
 * Email        : ghifariocta@upi.edu
 * Desc         : Package viewmodel, this class is the most important thing in this game, containt gameloop
 */

/*
 * Saya Ghifari Octaverin (2000952) mengerjakan Tugas Masa Depan
 * dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk
 * keberkahanNya maka saya tidak melakukan kecurangan seperti 
 * yang telah dispesifikasikan. Aamiin.
 */

package ViewModel;

import Model.Floor;
import Model.GameObject;
import Model.ID;
import Model.Obstacle;
import Model.Player;
import View.Menu;
import View.Window;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 *
 * @author Ghifari Octaverin
 * @Background Image by: https://www.gameart2d.com/free-platformer-game-tileset.html
 */
public class Game extends Canvas implements Runnable {
    // Window
    private Window window;

    // Screen Size
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Thread
    private Thread thread;
    private boolean running = false;

    // Player Data
    private String username;
    private int adapt;
    private int fall;
    private int collideWithObstacles = 0;
    private int collideWithFloor = 0;

    // Game Var
    private int FPS;
    private int elapsed = 0;

    // BGM Clip
    private Clip clip;

    // Object Handler
    private Handler handler;

    // Background
    private BufferedImage bg;

    // Enum State in This Game
    public enum STATE {
        Play,
        GameOver
    }

    // Initial State
    public STATE state = STATE.Play;

    // Constructor
    public Game() {
        // Call Window
        window = new Window(Game.WIDTH, Game.HEIGHT, "The Survive Hop", this);

        // Call Handler
        handler = new Handler();

        // KeyListener For Player Keyboard Input
        this.addKeyListener(new KeyInput(handler, this));

        // Read Background Image
        try {
            bg =  ImageIO.read(new File("resources/BG.png").getAbsoluteFile());
            // Background image is taken from https://www.gameart2d.com/free-platformer-game-tileset.html
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (state == STATE.Play) {
            // Add Player Object
            handler.addObject(new Player(500, 100, ID.Player));
            // Add Floor Object
            handler.addObject(new Floor(0, Game.HEIGHT-70, ID.Floor));
            
            // Add Obstacle for Early Game
            int tempX = Game.WIDTH;
            int idx;
            int prexIdx = -1;
            for (int i = 0; i < 16; i++) {
                if (RNG(0, 3) != 1) {
                    idx = RNG(0, 9);
                    if (prexIdx == idx) {
                        idx = RNG(0, 9);
                    }
                    handler.addObject(new Obstacle(tempX, Game.HEIGHT - RNG(100, 400), ID.Obstacle, idx));
                    prexIdx = idx;
                }
                tempX -= 50;
            }
        }
    }

    // Username Setter
    public void setUsername(String username) {
        this.username = username;
    }

    // Adapt Setter
    public void setAdapt(int adapt) {
        this.adapt = adapt;
    }

    // Fall Setter
    public void setFall(int fall) {
        this.fall = fall;
    }

    // Thread Start
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    // Thread Stop
    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        BGM bgm = new BGM();
        int idx = 0;
        int prevIdx = -1;
        int tempX = -50;
        
        // Play BGM
        clip = bgm.playSound(this.clip, "TinyRPGTown.wav");
        
        // Game Loop
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                render();
                frames++;
                delta--;
            }

            if (state == STATE.GameOver) {
                bgm.stopSound(this.clip);
            }

            if (System.currentTimeMillis() - timer > 1000) {
                elapsed++;
                timer += 1000;
                FPS = frames;

                // Adding More Obstacle Every Second As Long The Game Still Running
                for (int i = 0; i < 16; i++) {
                    if (RNG(0, 3) != 1) {
                        idx = RNG(0, 9);
                        if (prevIdx == idx) {
                            idx = RNG(0, 9);
                        }
                        handler.addObject(new Obstacle(tempX, Game.HEIGHT - RNG(100, 400), ID.Obstacle, idx));
                        prevIdx = idx;
                    }
                    tempX -= 50;
                }

                // Moving All Obstacles to Right
                GameObject obs = null;
                for (int i = 0; i < handler.countObject(); i++) {
                    if (handler.obj.get(i).getId() == ID.Obstacle) {
                        obs = handler.obj.get(i);
                        double velocity = 1;
                        obs.setVel_x(velocity);
                    }
                }

                frames = 0;
            }
        }
        stop();
    }

    // Updating Object Every Tick
    private void tick() {
        handler.tick();

        if (state == STATE.Play) {
            GameObject player = null;
            GameObject obs = null;
            GameObject floor = null;

            // Fetching Player and Floor Objects
            for (int i = 0; i < handler.countObject(); i++) {
                if (handler.obj.get(i).getId() == ID.Player) {
                    player = handler.obj.get(i);
                }
                if (handler.obj.get(i).getId() == ID.Floor) {
                    floor = handler.obj.get(i);
                }
                if (player != null && floor != null) {
                    break;
                }
            }

            // When Player and Floor are not null
            // Then Checking Player Collision with Floor and Obstacles
            if (player != null && floor != null) {
                for (int i = 0; i < handler.countObject(); i++) {
                    if (handler.obj.get(i).getId() == ID.Obstacle) {
                        player.setFalling(true);
                        collideWithObstacles = 0;
                        collideWithFloor = 0;
                        // When Player is Colliding with Obstacles or Floor
                        if (isCollide(player, (Obstacle)handler.obj.get(i), (Floor)floor)) {
                            // When Player Touch Top Side of Obstacle
                            // Then Increase Adapt Value by 1
                            if (collideWithObstacles == 1) {
                                adapt++;
                            }

                            // When Player Touch Top Side of Floor
                            // Then Increase Fall Value by 1
                            if (collideWithFloor == 1) {
                                fall++;
                            }

                            break;
                        }
                    }
                }
            }

            // When Obstacles Already Out of Screen (Right Side)
            // Then Remove Obstacles From List of Objects
            for (int i = 0; i < handler.countObject(); i++) {
                if (handler.obj.get(i).getId() == ID.Obstacle) {
                    obs = handler.obj.get(i);
                    if (obs.getX() >= Game.WIDTH) {
                        handler.removeObject(obs);
                        i--;
                    }
                }
            }
        }
    }

    // Checking Collision
    public boolean isCollide(GameObject player, Obstacle obstacle, Floor floor) {
        boolean result = false;

        // GameObject Var
        int playerSize = 30;
        int obstacleWidth = 50;
        int obstacleHeight = 250;

        // Player Sides
        int playerLeft = player.x;
        int playerRight = player.x + playerSize;
        int playerTop = player.y;
        int playerBottom = player.y + playerSize;

        // Obstacle Sides
        int obstacleLeft = obstacle.x;
        int obstacleRight = obstacle.x + obstacleWidth;
        int obstacleTop = obstacle.y;

        // Floor Sides
        int floorTop = floor.y;

        // When Player Intersects with Obstacle
        if (new Rectangle(player.x, player.y, playerSize, playerSize).intersects(obstacle.x, obstacle.y, obstacleWidth, obstacleHeight)) {
            // When Player Fall on Top of Obstacle
            // Then Set Falling Value to False
            // And When Player Y Axis Velocity Still Positive (AKA Falling Because of Gravity)
            // Then Will Set Y Axis Velocity to Zero
            if (playerBottom <= obstacleTop + 10) {
                player.setFalling(false);
                if (player.getVel_y() > 0) {
                    player.setVel_y(0);

                    // When Player is Not Falling Anymore
                    // Then Add collideWithObstacles Value by 1
                    if (!player.getFalling()) {
                        collideWithObstacles++;
                    }
                }

                // If Player X Axis Velocity is Zero When Player on Top of Obstacle
                // Then Set Player X Axis Velocity Same as Obstacle X Axis Velocity (So Player Will Also Move to Right)
                if (player.getVel_x() == 0) {
                    player.setVel_x(obstacle.getVel_x());
                }
            } 
            
            // When Player is Collide with Left Side of Obstacle
            // Then Set Player Movement to Right False and Position X Axis to Left Side of Obstacle
            if (playerRight >= obstacleLeft && playerRight <= obstacleRight && playerTop >= obstacleTop) {
                player.setRight(false);
                player.setX(obstacleLeft - playerSize + 1);
            }

            // When Player is Collide with Right Side of Obstacle
            // Then Set Player Movement to Left False and Position X Axis to Right Side of Obstacle
            if (playerLeft <= obstacleRight && playerLeft >= obstacleLeft && playerTop >= obstacleTop) {
                player.setLeft(false);
                player.setX(obstacleRight++);
                
                // If Player Collide With Right Side of Obstacle and Screen
                // Then Will Change Game State to Game Over and Store the Score
                if (playerRight >= Game.WIDTH) {
                    state = STATE.GameOver;
                    storeData();
                    new Menu().setVisible(true);
                    close();
                }
            }

            // When Player Jump
            // Then Set Player Falling Value to true and Reset collideWithObstacles
            if (player.getVel_y() < 0 && playerBottom < obstacleTop) {
                player.setFalling(true);
                collideWithObstacles = 0;
            }

            result = true;
        }

        // When Player Intersects with Floor
        if (new Rectangle(player.x, player.y, playerSize, playerSize).intersects(floor.x, floor.y, Game.WIDTH, 50)) {
            // When Player Fall on Top of Floor
            // Then Set Falling Value to False
            // And When Player Y Axis Velocity Still Positive (AKA Falling Because of Gravity)
            // Then Will Set Y Axis Velocity to Zero
            if (playerBottom <= floorTop + 10) {
                player.setFalling(false);
                if (player.getVel_y() > 0) {
                    player.setVel_y(0);

                    // When Player is Not Falling Anymore
                    // Then Add collideWithFloor Value by 1
                    if (!player.getFalling()) {
                        collideWithFloor++;
                    }
                }
            }

            // Checking is Player Collide with Obstacle After Fall on Top of Floor
            for (int i = 0; i < handler.countObject(); i++) {
                // Get Obstacle
                if (handler.obj.get(i).getId() == ID.Obstacle) {
                    obstacleLeft = handler.obj.get(i).getX();
                    obstacleRight = handler.obj.get(i).getX() + obstacleWidth;
                    obstacleTop = handler.obj.get(i).getY();

                    // When Player is Collide with Left Side of Obstacle
                    // Then Set Player Movement to Right False and Position X Axis to Left Side of Obstacle
                    if (playerRight >= obstacleLeft && playerRight <= obstacleRight && playerTop >= obstacleTop) {
                        player.setRight(false);
                        player.setX(obstacleLeft - playerSize + 1);
                        break;
                    }
        
                    // When Player is Collide with Right Side of Obstacle
                    // Then Set Player Movement to Left False and Position X Axis to Right Side of Obstacle
                    if (playerLeft <= obstacleRight && playerLeft >= obstacleLeft && playerTop >= obstacleTop) {
                        player.setLeft(false);
                        player.setX(obstacleRight++);

                        // If Player Collide With Right Side of Obstacle and Screen
                        // Then Will Change Game State to Game Over and Store the Score
                        if (playerRight >= Game.WIDTH) {
                            state = STATE.GameOver;
                            storeData();
                            new Menu().setVisible(true);
                            close();
                        }

                        break;
                    }
                }
            }

            // When Player Jump
            // Then Set Player Falling Value to true and Reset collideWithFloor
            if (player.getVel_y() < 0 && playerBottom < floorTop) {
                player.setFalling(true);
                collideWithFloor = 0;
            }

            result = true;
        }

        return result;
    }

    // Random Number Generator
    public static int RNG(int min, int max) {
        Random random = new Random();
        int result = random.nextInt((max - min) + 1) + min;

        return result;
    }

    // All Object Rendered Here
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // Draw Background Image to The Screen
        g.drawImage(bg, 0, 0, Game.WIDTH, Game.HEIGHT, null);

        // Render Game Objects
        if (state == STATE.Play) {
            handler.render(g);

            g.setColor(Color.BLACK);
            g.setFont(new Font("pixelmix", 1, 14));
            g.drawString("FPS: " + Integer.toString(FPS), 10, 20);
            g.drawString("Elapsed Time: " + Integer.toString(elapsed) + "s", 10, 40);
            g.drawString("Adapt: " + Integer.toString(adapt), 10, 60);
            g.drawString("Fail: " + Integer.toString(fall), 10, 80);
        }
        g.dispose();
        bs.show();  
    }
    
    // Insert Data to Database
    public void storeData() {
        try {
            ExperienceProcessing process = new ExperienceProcessing();
            process.storeData(username, adapt, fall);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Username: " + this.username + "\nAdapt: " + this.adapt + "\nFall: " + this.fall, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
    }

    // Close Game
    public void close() {
        window.closeWindow();
    }
}
