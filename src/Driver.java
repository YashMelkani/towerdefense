import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.image.*;
import java.awt.geom.AffineTransform;

public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    int numBalloons = 10;
    int screen_width = 1500;
    int screen_height = 1000;
    Balloon b;
    ArrayList<Balloon> bs = new ArrayList<Balloon>();
    Level one;

    Background bg;

    int pHealth = 100; //example

    Sprite dartTowerSelector;
    int my_variable = 0; // example

    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    ArrayList<Particle> particles = new ArrayList<Particle>();
    public static ArrayList<DartTower> towers = new ArrayList<>();
    public static ArrayList<Balloon> balloons = new ArrayList<>();
    public static ArrayList<Balloon> attackedBalloons = new ArrayList<>();
    private Sprite player;
    private boolean placingTower;
    boolean pressed = false;
    int mouseX;
    int mouseY;
    Tower tempTower;

    // fonts
    Font font = new Font("Courier New", 1, 50);
    Font font2 = new Font("Courier New", 1, 30);

    public void paint(Graphics g) {
        super.paintComponent(g);
        bg.paint(g);
        dartTowerSelector.paint(g);
        g.setFont(font);

        g.setColor(Color.RED);
        g.drawString(("Health:") + Integer.toString(pHealth), 1100, 870);
        g.setFont(font2);
        g.setColor(Color.CYAN);

        //paint sprite
//		if(b.isAlive) {
//			b.paint(g);
//		}
//		else {
//			deleteBalloon(b);
//		}

        //System.out.println(bs.size());

        for (Balloon b : bs) {
            if (b.isAlive) {
                b.paint(g);
            } else {
                deleteBalloon(b);
            }
//			System.out.print(b.x + " ");
        }
        System.out.println();
        g.drawString(("my_variable:") + Integer.toString(my_variable), 0, 870);
        g.setFont(font2);
        g.setColor(Color.CYAN);

        // paint sprite
        b.paint(g);

        g.setColor(Color.BLACK);
        for (Particle p : particles) {
            p.paint(g);
        }
        for (Balloon b : balloons) {
            g.drawOval(b.x, b.y, 30, 30);
        }
        for (Tower t : towers) {
            t.paint(g);
            g.drawOval(t.x, t.y, 30, 30);
            // for debugging
            g.drawOval(t.x - 120, t.y + -100, (int) t.attackRadius, (int) t.attackRadius);
        }


    }


    public void update() {
        placeTower();
        for (Tower t : towers) {
            t.findTarget(bs);
            Sprite target = t.getTarget();
            if (target != null) {
                attackedBalloons.add((Balloon) target);
            }
        }
        if (!attackedBalloons.isEmpty()) {
            for (Balloon b : attackedBalloons) {
                b.takeDamage(1);
            }
        }
        for (int i = 0; i < bs.size(); i++) {
            Balloon b = bs.get(i);
            b.move();
            if (b.isFinished()) {
                int damage = b.damage;
                bs.remove(i);
//				System.out.println("Num Balloons " + bs.size());
                pHealth -= damage;
            }
        }


        // System.out.println("move");

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        update();
        repaint();
    }

    public static void main(String[] arg) {
        Driver d = new Driver();
    }

    public Driver() {
        JFrame f = new JFrame();
        f.setTitle("Frogger");

        f.setSize(screen_width, screen_height);
        f.setResizable(false);
        f.addKeyListener(this);
        f.addMouseMotionListener(this);
        f.addMouseListener(this);
        placingTower = false;
        bg = new Background("hqdefault.jpg", 0, 0);
        dartTowerSelector = new Sprite("weirdPixelMonkey.png", 1200, 100);

        // sprite instantiation
        b = new Balloon(3);
        b.addMouseListener(this);

        for (int i = 0; i < numBalloons; i++) {
            int r = (int) (Math.random() * 3) + 1;
            //System.out.println(r);
            bs.add(new Balloon(r, -75 * i, 405));
            bs.get(i).addMouseListener(this);
        }

        //particles
        particles.add(new Particle(50, 50));

        // particles
        particles.add(new Particle(50, 50));
        balloons.add(b);

        f.add(this);

        // drawing timer
        t = new Timer(17, this);
        t.start();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void placeTower() {
        if (pressed && mouseX > 1195 && mouseX < 1300 && mouseY > 125 && mouseY < 213) {
            placingTower = true;
            // System.out.println(placingTower);
        }
        if (pressed && placingTower == true && mouseX < 1100) {
            DartTower tower = new DartTower(mouseX - 20, mouseY - 75);
            towers.add(tower);
            placingTower = false;
        }
    }


    Timer t;

    public void deleteBalloon(Balloon b) {
        b = null;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // System.out.println("key press " + e.getKeyCode());
        if (e.getKeyCode() == 38) {
            //up
            //b.moveTo(0,0);
            if (bs.size() != 0) {
                bs.get(0).takeDamage(1);
                if (!bs.get(0).isAlive) {
                    bs.remove(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

        //System.out.println("key press "+e.getKeyCode());
        if (e.getKeyCode() == 38) {
            //up

            // System.out.println("key press "+e.getKeyCode());
            if (e.getKeyCode() == 38) {
                // up
                b.deletePath();

            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println(e.getX() + " " + e.getY());
        pressed = false;
        if (e.getComponent().getClass() == Sprite.class) {
            // System.out.println("clicked on a sprite");
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void reset() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // System.out.println("WAY");
        int mouseX = e.getX();
        int mouseY = e.getY();
        pressed = true;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        pressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        pressed = false;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // TODO Auto-generated method stub

    }

}



