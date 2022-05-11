package org.insa.graphs.model;

import java.util.ArrayList;

public class Label implements Comparable<Label> {
    private Node currentNode;
    private boolean mark;
    protected double cost;
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
        return (other.getTotalCost() == this.getTotalCost());
    }

    public int compareTo(Label other){return (Double.compare(this.getTotalCost(), other.getTotalCost()));}

    public void setMark(boolean b) { this.mark = b; }

    public void setFatherPath(Arc fatherPath) {
        this.fatherPath = fatherPath;
    }

    public Node getCurrentNode() {return this.currentNode;}

    public double getTotalCost() {return this.cost;};

    public String toString() {return (""+this.currentNode.getId());}
}
