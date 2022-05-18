package org.insa.graphs.model;

public class LabelStar extends Label {
    private final double distFin;


    public LabelStar(Node n, double dist) {
        super(n);
        distFin = dist;
    }
    public double getTotalCost() {
        return (this.cost + this.distFin);
    }
}