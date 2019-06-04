package game;

import java.awt.*;

public abstract class GameEffect {

    private Sprite effect;
    private int x;
    private int y;
    private double velX;
    private double velY;
    private double age;

    public Sprite getEffect() {
        return effect;
    }

    public void setEffect(Sprite effect) {
        this.effect = effect;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void move() {
        age += .09;
        effect.tx.translate(velX / 25, velY / 25);

        effect.x = effect.tx.getTranslateX();
        effect.y = effect.tx.getTranslateY();
        System.out.println("x: " + effect.x + " y: " + effect.y + " velX: " + velX + " velY: " + velY);
    }

    public void paint(Graphics g) {
        effect.paint(g);
    }

    public boolean isDone() {
        return age >= 1;
    }
}