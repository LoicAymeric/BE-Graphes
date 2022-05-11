package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;
import org.insa.graphs.model.LabelStar;

import java.util.ArrayList;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    private boolean AllMarked(LabelStar[] tabLabel) //return true s'il sont tous marqués
    {
        boolean state = true;
        int i = 0;
        while (state && i< tabLabel.length )
        {
            if (!tabLabel[i].getMark())
            {
                state = false;
            }
            i++;
        }
        return state;
    }
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();

        ShortestPathSolution solution = null;
        Node start = data.getOrigin();
        Node end = data.getDestination();
        if (start.getId() == end.getId())
        {
            return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }

        notifyOriginProcessed(start);

        BinaryHeap<Label> labelHeap = new BinaryHeap<Label>();
        LabelStar[] tabLabel = new LabelStar[data.getGraph().size()] ;
        //init
        for (Node n : data.getGraph().getNodes())
        {
            tabLabel[n.getId()] = new LabelStar(n, end);
        }

        tabLabel[start.getId()].setCost(0);
        labelHeap.insert(tabLabel[start.getId()]);

        //Iterations
        Label x;
        boolean markedEnd = false;

        while (!this.AllMarked(tabLabel) && !labelHeap.isEmpty() && !markedEnd)
        {

            x = labelHeap.deleteMin();
            tabLabel[x.getCurrentNode().getId()].setMark(true);
            notifyNodeMarked(x.getCurrentNode());

            if (x.getCurrentNode() == end) //si la destination est marquée
            {
                markedEnd = true;
            }
            for (Arc y : x.getCurrentNode().getSuccessors())
            {

                if (data.isAllowed(y)) {
                    LabelStar yLabel = tabLabel[y.getDestination().getId()];
                    if (!yLabel.getMark()) {
                        double previousCost = yLabel.getCost();
                        yLabel.setCost(Double.min(yLabel.getTotalCost(), x.getCost() + data.getCost(y)));
                        if (previousCost != yLabel.getCost()) { //si le coût min de y a changé
                            if (previousCost != 9999999)
                            {
                                labelHeap.remove(yLabel);
                            }
                            notifyNodeReached(y.getDestination());

                            labelHeap.insert(yLabel);
                            tabLabel[y.getDestination().getId()].setFatherPath(y);
                        }
                    }
                }
            }
        }
        if (!markedEnd){
            return new ShortestPathSolution(data, AbstractSolution.Status.INFEASIBLE);
        }
        notifyDestinationReached(end);

        //Path creation
        ArrayList<Node> nodePath = new ArrayList<Node>();
        LabelStar endLabel = tabLabel[end.getId()];
        nodePath.add(0, endLabel.getCurrentNode());
        while (tabLabel[nodePath.get(0).getId()].getFatherPath().getOrigin() != start)
        {
            //on ajoute en début de liste le father de chaque node à partir de la fin
            Node nodeAdd = tabLabel[nodePath.get(0).getId()].getFatherPath().getOrigin();
            nodePath.add(0,nodeAdd);

        }
        nodePath.add(0, start);

        Path path = new Path(data.getGraph());
        if (data.getMode() == AbstractInputData.Mode.LENGTH) {
            path = Path.createShortestPathFromNodes(data.getGraph(), nodePath);
        }
        else{
            path = Path.createFastestPathFromNodes(data.getGraph(), nodePath);
        }
        solution = new ShortestPathSolution(data, AbstractSolution.Status.OPTIMAL, path);

        return solution;
    }

}

