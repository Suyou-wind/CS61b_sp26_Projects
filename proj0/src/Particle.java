import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        lifespan = -1;
    }

    public Color color() {
//        if (flavor == ParticleFlavor.EMPTY) {
//            return Color.BLACK;
//        }
//        return Color.GRAY;
//不注释的话下面无法访问
// task1

        switch(flavor){
            case EMPTY:
                return Color.BLACK;
            case SAND:
                return Color.YELLOW;
            case BARRIER:
                return Color.GRAY;
            case WATER:
                return Color.BLUE;
            case FOUNTAIN:
                return Color.CYAN;
            case PLANT:
                return new Color(0, 255, 0);
            case FIRE:
                return new Color(255,0,0);
            case FLOWER:
                return new Color(255,141,161);
        }
    return null;
    }

    public void moveInto(Particle other) {
        other.flavor=flavor;
        other.lifespan=lifespan;
        flavor=ParticleFlavor.EMPTY;
        lifespan=-1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle below=neighbors.get(Direction.DOWN);
        if(below.flavor==ParticleFlavor.EMPTY)
            moveInto(below);
    }

    public void flow(Map<Direction, Particle> neighbors) {
    }

    public void grow(Map<Direction, Particle> neighbors) {
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void action(Map<Direction, Particle> neighbors) {
        if(flavor==ParticleFlavor.EMPTY)
            return;
        if(flavor!=ParticleFlavor.BARRIER)
            fall(neighbors);
    }
}
//test