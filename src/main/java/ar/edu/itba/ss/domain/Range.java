package ar.edu.itba.ss.domain;

/**
 * Es un intetvalo cerra-abierto
 * [,)
 */
public class Range {
    private Double lowest;
    private Double highest;

    public Range(Double lowest, Double highest) {
        this.lowest = lowest;
        this.highest = highest;
    }

    public Double getLowest() {
        return lowest;
    }

    public Double getHighest() {
        return highest;
    }

    public boolean isInRange(Double value){
        return lowest <= value && value < highest;
    }

    @Override
    public String toString() {
        return String.format("[%f,%f)",lowest, highest);
    }
}
