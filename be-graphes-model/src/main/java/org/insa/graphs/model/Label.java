package org.insa.graphs.model;

import java.util.ArrayList;

public class Label implements Comparable<Label> {
    private Node currentNode;
    private boolean mark;
    private double cost;
    private Arc fatherPath;



    public Label(Node n)
    {
        this.currentNode = n;
        this.mark = false;
        this.cost = 9999999;
        this.fatherPath = null;
    }

    public double getCost() {
        return cost;
    }

    public boolean getMark() { return mark; }

    public Arc getFatherPath() {
        return fatherPath;
    }

    public void setCost(double cost) {this.cost = cost;}

    public boolean equals(Label other) {
        return (other.cost == this.cost);
    }

    public int compareTo(Label other){return (Double.compare(this.cost, other.cost));}

    public void setMark(boolean b) { this.mark = b; }

    public void setFatherPath(Arc fatherPath) {
        this.fatherPath = fatherPath;
    }

    public Node getCurrentNode() {return this.currentNode;}
}
