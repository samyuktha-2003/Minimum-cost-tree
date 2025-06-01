
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GraphPanel extends JPanel {
    private final JFrame frame;
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private final ArrayList<Edge> mstEdges = new ArrayList<>();

    public GraphPanel() {
        frame = new JFrame("Kruskal’s Algorithm - Campus Network Demo");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        initializeGraph();
        computeKruskalMST();
        repaint();
    }

    private void initializeGraph() {
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

    private void computeKruskalMST() {
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // Draw all edges
        for (Edge e : edges) {
            Node n1 = nodes.get(e.src);
            Node n2 = nodes.get(e.dest);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(n1.x, n1.y, n2.x, n2.y);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(e.weight),
                (n1.x + n2.x) / 2, (n1.y + n2.y) / 2);
        }

        // Highlight MST edges
        g2.setColor(Color.GREEN);
        for (Edge e : mstEdges) {
            Node n1 = nodes.get(e.src);
            Node n2 = nodes.get(e.dest);
            g2.drawLine(n1.x, n1.y, n2.x, n2.y);
        }

        // Draw nodes
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            g2.setColor(Color.BLUE);
            g2.fillOval(n.x - 10, n.y - 10, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawString(i + ": " + n.name, n.x - 30, n.y - 15);
        }

        // Total cost display
        int total = 0;
        for (Edge e : mstEdges) total += e.weight;
        g2.setColor(Color.RED);
        g2.drawString("Minimum Cabling Cost: " + total + " meters", 20, 500);
    }
}
