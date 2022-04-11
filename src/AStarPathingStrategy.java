import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        // creating data structures
        List<Point> path = new LinkedList<Point>();
        Comparator<Node> comp = Comparator.comparing(Node::getfValue);
        PriorityQueue<Node> openListQ = new PriorityQueue<Node>(comp);
        HashMap<Point, Node> openListM = new HashMap();
        HashMap<Point, Node> closedListM = new HashMap();

        // condition before entering loop
        int g_val = 0;
        int h_val = heuristic(start, end);
        int f_val = g_val + h_val;
        Node current = new Node(null, start, g_val, h_val, f_val);
        openListQ.add(current);
        openListM.put(start, current);

        // entering the loop
        while (!withinReach.test(current.location, end)) {
            // getting all valid neighbors (for which we can pass through and not already on closed list)
            List<Point> validNeighbors = potentialNeighbors.apply(current.location).filter(canPassThrough).
                    filter((p1) -> !closedListM.containsKey(p1)).collect(Collectors.toList());
            // analyze all neighbors
            for (Point neighbor: validNeighbors) {
                // get all g, h, & f values
                g_val = current.gValue + 1;
                h_val = heuristic(neighbor, end);
                f_val = g_val + f_val;
                Node adjacent = new Node(current, neighbor, g_val, h_val, f_val);

                // if not on open_list, add it to open_list
                if (!openListM.containsKey(neighbor)) {
                    openListM.put(neighbor, adjacent);
                    openListQ.add(adjacent);
                }
                // if already on open_list, check if better g value
                if (openListM.containsKey(neighbor)) {
                    if (g_val < openListM.get(neighbor).gValue) {
                        openListM.put(neighbor, adjacent);
                        openListQ.add(adjacent);
                    }
                }

            }
            closedListM.put(current.location, current);
            // if open_list is empty, return empty list since no path is found
            if (openListQ.isEmpty()) {
                return path;
            }
            current = openListQ.remove();
            openListM.remove(current.location);
        }

        // to get the path
        while (current.prior != null) {
            path.add(0, current.location);
            current = current.prior;
        }
        return path;
    }

    public int heuristic(Point current, Point dest) {
        return (int)Math.sqrt(Math.pow((dest.x-current.x), 2) + Math.pow((dest.y-current.y), 2));
    }

    private class Node
    {
        private Node prior;
        private Point location;
        private int fValue;
        private int hValue;
        private int gValue;

        public Node(Node prior, Point location, int gValue, int hValue, int fValue)
        {
            this.prior = prior;
            this.location = location;
            this.gValue = gValue;
            this.hValue = hValue;
            this.fValue = fValue;
        }

        public int getfValue() {
            return fValue;
        }

        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (other.getClass() != this.getClass()) {
                return false;
            }
            Node n = (Node)other;
            return this.location.equals(n.location);
        }

        public int hashCode()
        {
            return Objects.hash(location);
        }
    }

}
