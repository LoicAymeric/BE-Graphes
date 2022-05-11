package org.insa.graphs.gui.simple;

import java.awt.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

import static java.lang.Math.abs;
import static java.lang.Math.random;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        /*// Visit these directory to see the list of available files on Commetud.
        final String mapName = "../map/insa.mapgr";
        final String pathName = "../path/path_fr31insa_rangueil_insa.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        final Graph graph = reader.read();

        // Create the drawing:
        final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        drawing.drawGraph(graph);

        // TODO: Create a PathReader.
        final PathReader pathReader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

        // TODO: Read the path.
        final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
        drawing.drawPath(path, Color.red);*/
        testInexistant();
        testNull();
        testCourt();
        testLong();
    }
    public static boolean test(ShortestPathAlgorithm algo) throws IOException {



        BellmanFordAlgorithm belma = new BellmanFordAlgorithm(algo.getInputData());
        ShortestPathSolution sol2 = belma.run();
        ShortestPathSolution sol1 = algo.run();
        if (sol1.isFeasible() && sol2.isFeasible()) {
            Path p1 = sol1.getPath();
            Path p2 = sol2.getPath();
            return p1.getMinimumTravelTime() == p2.getMinimumTravelTime() && abs(p1.getLength() - p2.getLength()) < 0.1;
        } else if (!sol1.isFeasible() && !sol2.isFeasible()) {
            return true;
        } else {
            return false;
        }
    }
        public static boolean testChemin(ShortestPathAlgorithm algo, String pathName) throws IOException {

            ArcInspector arcIn = ArcInspectorFactory.getAllFilters().get(0);

            final PathReader pathReader = new BinaryPathReader(
                    new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
            final Path path = pathReader.readPath(algo.getInputData().getGraph());

            ShortestPathSolution sol1 = algo.run();
            if (sol1.isFeasible())
            {
                Path p1 = sol1.getPath();
                return p1.getMinimumTravelTime() == path.getMinimumTravelTime() && abs(p1.getLength() - path.getLength()) < 0.1;
            }
            else
            {
                return false;
            }
    }

    public static ShortestPathData initData(String map, int debut, int fin, int mode) throws IOException {
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graph = reader.read();
        ArcInspector arcIn = ArcInspectorFactory.getAllFilters().get(mode);
        ShortestPathData data = new ShortestPathData(graph, graph.get(debut), graph.get(fin), arcIn);
        return data;
    }

    public static ShortestPathData initDataChemin(String map, String pathName, int mode) throws IOException {
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(map))));
        final Graph graph = reader.read();

        final PathReader pathReader = new BinaryPathReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));
        final Path path = pathReader.readPath(graph);

        ArcInspector arcIn = ArcInspectorFactory.getAllFilters().get(mode);
        ShortestPathData data = new ShortestPathData(graph, path.getOrigin(), path.getDestination(), arcIn);
        return data;
    }
    public static void testInexistant() throws IOException {
        //insa 608 1282
        //mode route et length (1)
        System.out.println("Début du test de chemin inexistant...");
        System.out.println("--- Dijkstra ----");
        boolean etat = test(new DijkstraAlgorithm(initData("../map/insa.mapgr", 608, 1282, 1)));
        if (etat)
        {
            System.out.println("Réussite du test !");
        }
        else
        {
            System.out.println("Echec du test");
        }

        System.out.println("--- Astar ----");
        etat = test(new AStarAlgorithm(initData("../map/insa.mapgr", 608, 1282, 1)));
        if (etat)
        {
            System.out.println("Réussite du test !");
        }
        else
        {
            System.out.println("Echec du test");
        }
    }

    public  static  void testNull() throws IOException {
        System.out.println("Début du test de chemin null...");
        System.out.println("--- Dijkstra ----");
        boolean etat = test(new DijkstraAlgorithm(initData("../map/insa.mapgr", 608,608, 0)));
        if (etat)
        {
            System.out.println("Réussite du test !");
        }
        else
        {
            System.out.println("Echec du test");
        }

        System.out.println("--- Astar ----");
        etat = test(new AStarAlgorithm(initData("../map/insa.mapgr", 608,608, 0)));
        if (etat)
        {
            System.out.println("Réussite du test !");
        }
        else
        {
            System.out.println("Echec du test");
        }
    }
    public  static  void testCourt() throws IOException {
        boolean etat1 = true;
        boolean etat2, etat3;
        System.out.println("Début des tests de chemin court");
        for (int i =0; i<10; i++)
        {
            System.out.println("test n°"+i);
            int r1 = (int)(random()*1000);
            int r2 = (int)(random()*1000);
            int mode = (int)(random()*3);
            etat2 = test(new DijkstraAlgorithm(initData("../map/insa.mapgr", r1, r2, mode)));
            etat3 = test(new AStarAlgorithm(initData("../map/insa.mapgr", r1, r2, mode)));
            etat1 = etat1 && etat2 && etat3;
            if (!etat2)
            {
                System.out.println("Erreur  en dijkstra pour les points :"+r1 + " et " +r2);
            }
            if (!etat3)
            {
                System.out.println(mode);
                System.out.println("Erreur  en Astar pour les points :"+r1 + " et " +r2);
            }

        }
        if (etat1)
        {
            System.out.println("Réussite des test !");
        }
        else
        {
            System.out.println("Echec des test");
        }

    }
    public  static  void testLong() throws IOException {
        boolean state = true;
        System.out.println("Début des tests longs");
        System.out.println("--- Dijkstra ---");
        System.out.println("1er test");
        state = state && testChemin(new DijkstraAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                        "../path/bl_spiral1.path", 0 )),"../path/bl_spiral1.path" );
        System.out.println("2eme test");
        state = state && testChemin(new DijkstraAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                "../path/bl_spiral2.path", 0 )),"../path/bl_spiral2.path" );
        System.out.println("3eme test");
        state = state && testChemin(new DijkstraAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                "../path/bl_spiral3.path", 0 )),"../path/bl_spiral3.path" );
        if (state)
        {
            System.out.println("Réussite des tests de Dijkstra");
        }
        else
        {
            System.out.println("Echec des tests de Dijkstra");
        }

        System.out.println("--- Astar ---");
        System.out.println("1er test");
        state = state && testChemin(new AStarAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                "../path/bl_spiral1.path", 0 )),"../path/bl_spiral1.path" );
        System.out.println("2eme test");
        state = state && testChemin(new AStarAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                "../path/bl_spiral2.path", 0 )),"../path/bl_spiral2.path" );
        System.out.println("3eme test");
        state = state && testChemin(new AStarAlgorithm(initDataChemin("../map/fractal-spiral.mapgr",
                "../path/bl_spiral3.path", 0 )),"../path/bl_spiral3.path" );
        if (state)
        {
            System.out.println("Réussite des tests de Astar");
        }
        else
        {
            System.out.println("Echec des tests de Astar");
        }
    }

}

