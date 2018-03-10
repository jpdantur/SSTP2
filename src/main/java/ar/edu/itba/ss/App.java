package ar.edu.itba.ss;
import org.apache.commons.math3.random.*;
import org.apache.commons.math3.util.FastMath;
import java.util.*;



public class App 
{
    public static final int N = 1000;
    public static final int L = 100;
    public static final double SPEED = 0.03;
    public static void main( String[] args ) {
        RandomDataGenerator rng = new RandomDataGenerator();
        List<Particle> particleList = new ArrayList<Particle>(N);
        for (int i=0; i<N;i++) {
            particleList.add(new Particle(rng.nextUniform(0,L,true),
                    rng.nextUniform(0,L,true),
                    SPEED,
                    rng.nextUniform(0,2*FastMath.PI,true)));
        }

        //TODO: Actualizar con el tiempo y escupir en un archivo

    }
}
