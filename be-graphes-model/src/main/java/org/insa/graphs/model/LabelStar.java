package org.insa.graphs.model;

public class LabelStar extends Label {
    private final double distFin;

    public LabelStar(Node n,Node fin) {
        super(n);
        distFin = n.getPoint().distanceTo(fin.getPoint());
    }
    public double getTotalCost() {
        return (this.cost + this.distFin);
    }
}