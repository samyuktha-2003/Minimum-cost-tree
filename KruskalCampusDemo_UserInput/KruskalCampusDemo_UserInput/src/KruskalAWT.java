import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class KruskalAWT extends Frame {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private final ArrayList<Edge> mstEdges = new ArrayList<>();

    public KruskalAWT() {
        setTitle("Kruskal's Algorithm - AWT Demo");
        setSize(800, 600);
        setVisible(true);
        initializeGraph();
        computeKruskalMST();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void initializeGraph() {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Enter number of buildings:"));
        for (int i = 0; i < n; i++) {
            String name = JOptionPane.showInputDialog("Enter name for building " + (i + 1) + ":");
            int x = Integer.parseInt(JOptionPane.showInputDialog("Enter X coordinate for " + name + ":"));
            int y = Integer.parseInt(JOptionPane.showInputDialog("Enter Y coordinate for " + name + ":"));
            nodes.add(new Node(name, x, y));
        }

        int e = Integer.parseInt(JOptionPane.showInputDialog("Enter number of cable connections (edges):"));
        for (int i = 0; i < e; i++) {
            int src = Integer.parseInt(JOptionPane.showInputDialog("Enter source node index (0 to " + (n - 1) + "):"));
            int dest = Integer.parseInt(JOptionPane.showInputDialog("Enter destination node index (0 to " + (n - 1) + "):"));
            int weight = Integer.parseInt(JOptionPane.showInputDialog("Enter cable cost between " + nodes.get(src).name + " and " + nodes.get(dest).name + ":"));
            edges.add(new Edge(src, dest, weight));
        }
    }

    public void computeKruskalMST() {
        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(nodes.size());
        for (Edge edge : edges) {
            int x = ds.find(edge.src);
            int y = ds.find(edge.dest);
            if (x != y) {
                mstEdges.add(edge);
                ds.union(x, y);
            }
        }
    }

    public void paint(Graphics g) {
        for (Edge e : edges) {
            Node n1 = nodes.get(e.src);
            Node n2 = nodes.get(e.dest);
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(e.weight),
                    (n1.x + n2.x) / 2, (n1.y + n2.y) / 2);
        }

        g.setColor(Color.GREEN);
        for (Edge e : mstEdges) {
            Node n1 = nodes.get(e.src);
            Node n2 = nodes.get(e.dest);
            g.drawLine(n1.x, n1.y, n2.x, n2.y);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            g.setColor(Color.BLUE);
            g.fillOval(n.x - 10, n.y - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(i + ": " + n.name, n.x - 30, n.y - 15);
        }

        int total = 0;
        for (Edge e : mstEdges) total += e.weight;
        g.setColor(Color.RED);
        g.drawString("Minimum Cabling Cost: " + total + " meters", 20, 550);
    }

    public static void main(String[] args) {
        new KruskalAWT();
    }
}

class Node {
    String name;
    int x, y;

    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
