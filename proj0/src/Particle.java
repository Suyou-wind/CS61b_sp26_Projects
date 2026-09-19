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
        /*lifespan = -1;*/
        if(flavor==ParticleFlavor.PLANT||flavor==ParticleFlavor.FLOWER||flavor==ParticleFlavor.FIRE)
            lifespan=LIFESPANS.get(flavor);
        else
            lifespan=-1;
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
//            case PLANT:
//                return new Color(0, 255, 0);
//            case FIRE:
//                return new Color(255,0,0);
//            case FLOWER:
//                return new Color(255,141,161);
//            because task 10
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
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
        int random=StdRandom.uniformInt(3);
        Particle left=neighbors.get(Direction.LEFT);
        Particle right=neighbors.get(Direction.RIGHT);

        switch (random){
            case 0:
                return;
            case 1:
                if(left.flavor==ParticleFlavor.EMPTY)
                    moveInto(left);
                break;
            case 2:
                if(right.flavor==ParticleFlavor.EMPTY)
                    moveInto(right);
                break;
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int random=StdRandom.uniformInt(10);
        Particle target=null;

        switch (random){
            case 0:
                target=neighbors.get(Direction.LEFT);
                break;
            case 1:
                target=neighbors.get(Direction.RIGHT);
                break;
            case 2:
                target=neighbors.get(Direction.UP);
                break;
            default:
                return;
        }

        if(target.flavor==ParticleFlavor.EMPTY)
        {
            target.flavor=this.flavor;
            target.lifespan=LIFESPANS.get(this.flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void action(Map<Direction, Particle> neighbors) {
        if(flavor==ParticleFlavor.EMPTY)
            return;
        if(flavor!=ParticleFlavor.BARRIER)
            fall(neighbors);
        if(flavor==ParticleFlavor.WATER)
            flow(neighbors);
        if(flavor==ParticleFlavor.FLOWER||flavor==ParticleFlavor.PLANT)
            grow(neighbors);
    }

    public void decrementLifespan(){
        if(lifespan>0)
            lifespan-=1;
        if(lifespan==0){
            flavor=ParticleFlavor.EMPTY;
            lifespan=-1;
        }
    }
}
//test