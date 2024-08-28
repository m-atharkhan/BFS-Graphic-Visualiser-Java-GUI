//Hello guys thanks for visiting my code.
//Sorry for not writing comments.
//Feel free to Ask the functionalities from any AI platform like ChatGPT, Gemini ...

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;

public class JavaBFS_GUI extends JFrame {
    private static final int nodeRadius = 20;
    private static final Color nodeColor = Color.BLACK;
    private static final Color visitedNodeColor = Color.GREEN;
    private static final Color textColor = Color.WHITE;
    private static final Color edgeColor = Color.BLUE;
    private static final Color visitedEdgeColor = Color.RED;
    private static final int delayTime = 2000;

    private final JPanel panel;
    private final JButton startButton;

    private final ArrayList<Node> nodes = new ArrayList<>();
    private final ArrayList<int[]> edges = new ArrayList<>();

    private Node startNode = null;
    private Node endNode = null;

    private Map<Node, Node> parentMap = new HashMap<>();
    private Set<Node> visitedNodes = new HashSet<>();

    public JavaBFS_GUI() {
        setTitle("Graph || Breadth First Search");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
                drawPath(g, g);
            }
        };
        panel.setBackground(Color.WHITE);
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                handleMouseClick(e.getPoint());
            }
        });

        startButton = new JButton("Start BFS");
        startButton.setBackground(Color.YELLOW);
        startButton.addActionListener(e -> startBFS());

        add(panel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }

    private void drawGraph(Graphics g) {
        // Draw edges
        g.setColor(edgeColor);
        for (int[] edge : edges) {
            Node p1 = nodes.get(edge[0]);
            Node p2 = nodes.get(edge[1]);
            g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        // Draw nodes
        for (Node node : nodes) {
            g.setColor(nodeColor);
            g.fillOval(node.getX() - nodeRadius, node.getY() - nodeRadius, nodeRadius * 2, nodeRadius * 2);

            // Draw node number
            g.setColor(textColor);
            g.setFont(new Font("Times New Roman", Font.BOLD, 14));
            String text = String.valueOf(node.getNumber());
            FontMetrics metrics = g.getFontMetrics();
            int textX = node.getX() - (metrics.stringWidth(text) / 2);
            int textY = node.getY() + (metrics.getAscent() / 2);
            g.drawString(text, textX, textY);
        }
    }

    private void drawPath(Graphics g, Graphics e) {
        if (parentMap != null) {
            for (Map.Entry<Node, Node> entry : parentMap.entrySet()) {
                Node start = entry.getKey();
                Node end = entry.getValue();

                e.setColor(visitedNodeColor);
                e.fillOval(start.getX() - nodeRadius, start.getY() - nodeRadius, nodeRadius * 2, nodeRadius * 2);

                e.setColor(visitedEdgeColor);
                e.setFont(new Font("Times New Roman", Font.BOLD, 14));
                String ntext = String.valueOf(start.getNumber());
                FontMetrics nmetrics = e.getFontMetrics();
                int ntextX = start.getX() - (nmetrics.stringWidth(ntext) / 2);
                int ntextY = start.getY() + (nmetrics.getAscent() / 2);
                e.drawString(ntext, ntextX, ntextY);

                e.setColor(visitedNodeColor);
                e.fillOval(end.getX() - nodeRadius, end.getY() - nodeRadius, nodeRadius * 2, nodeRadius * 2);

                e.setColor(visitedEdgeColor);
                e.setFont(new Font("Times New Roman", Font.BOLD, 14));
                String mtext = String.valueOf(end.getNumber());
                FontMetrics mmetrics = e.getFontMetrics();
                int mtextX = end.getX() - (mmetrics.stringWidth(mtext) / 2);
                int mtextY = end.getY() + (mmetrics.getAscent() / 2);
                e.drawString(mtext, mtextX, mtextY);

                g.setColor(visitedEdgeColor);
                g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            }
        }
    }

    private void handleMouseClick(Point point) {
        for (Node node : nodes) {
            if (node.contains(point)) {
                if (startNode == null) {
                    startNode = node;
                } else if (endNode == null) {
                    endNode = node;
                    edges.add(new int[] { nodes.indexOf(startNode), nodes.indexOf(endNode) });
                    startNode = null;
                    endNode = null;
                    panel.repaint();
                }
                return;
            }
        }

        nodes.add(new Node(point.x, point.y, nodes.size() + 1));
        panel.repaint();
    }

    private void startBFS() {
        if (nodes.isEmpty())
            return;

        new Thread(() -> {
            Queue<Node> queue = new LinkedList<>();
            Node start = nodes.get(0);
            queue.add(start);
            visitedNodes.add(start);

            parentMap.clear();

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                SwingUtilities.invokeLater(() -> {
                    panel.repaint();
                });

                try {
                    Thread.sleep(delayTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int[] edge : edges) {
                    Node neighbor = null;
                    if (nodes.get(edge[0]).equals(current)) {
                        neighbor = nodes.get(edge[1]);
                    } else if (nodes.get(edge[1]).equals(current)) {
                        neighbor = nodes.get(edge[0]);
                    }
                    if (neighbor != null && !visitedNodes.contains(neighbor)) {
                        visitedNodes.add(neighbor);
                        queue.add(neighbor);
                        parentMap.put(neighbor, current);
                    }
                }
            }
            SwingUtilities.invokeLater(() -> panel.repaint());
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JavaBFS_GUI frame = new JavaBFS_GUI();
            frame.setVisible(true);
        });
    }

    private static class Node {
        private final int x, y, number;

        public Node(int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getNumber() {
            return number;
        }

        public boolean contains(Point point) {
            int radius = nodeRadius;
            return (point.x - x) * (point.x - x) + (point.y - y) * (point.y - y) <= radius * radius;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }
}
