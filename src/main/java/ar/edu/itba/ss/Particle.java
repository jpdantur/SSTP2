package ar.edu.itba.ss;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;

public class Particle {
    private Vector2D location;
    private double speed;
    private double angle;

    public Particle (double x, double y, double speed, double angle){
        location = new Vector2D(x,y);
        this.speed = speed;
        this.angle = angle;
    }

    public void update() {
        location = location.add(speed, new Vector2D(FastMath.cos(angle),FastMath.sin(angle)));
        //TODO: Actualizar angulo
    }

    public String toString() {
        return location.getX()+"\t"+
                location.getY()+"\t"+
                speed*FastMath.cos(angle)+"\t"+
                speed*FastMath.sin(angle);
    }
}
