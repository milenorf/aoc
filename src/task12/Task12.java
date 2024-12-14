package task12;

import utils.Point;

import java.util.*;

import static utils.Utils.*;

public class Task12 {
    public static void solve() {
        List<String> lines = parse("src/task12/input.txt");
//        List<String> lines = parse("src/task12/sample1.txt");
//        List<String> lines = parse("src/task12/sample.txt");
        char[][] map = parseMap(lines);

        Graph graph = constructGraph(map);

        int total = 0;
        for (List<Node> section : graph.sections()) {
            int area = section.size();
            int perimeter = 0;
            for (Node node : section) {
                perimeter += 4 - node.vertices().size();
            }
            total += area * perimeter;
        }
        System.out.println(total);
    }

    public static void solve2() {
//        List<String> lines = parse("src/task12/input.txt");
//        List<String> lines = parse("src/task12/sample1.txt");
        List<String> lines = parse("src/task12/sample2.txt");
//        List<String> lines = parse("src/task12/sample.txt");
        char[][] map = parseMap(lines);

        Graph graph = constructGraph(map);

        int total = 0;
        for (List<Node> section : graph.sections()) {
            int area = section.size();
            int perimeter = calcPerimeter(map, section);
            System.out.println(section.get(0).value() + ": " + perimeter);
            total += area * perimeter;
        }
        System.out.println(total);
    }

    private static int calcPerimeter(char[][] map, List<Node> section) {
        int fences = 0;

        for (Node node : section) {
            char value = node.value();
            int x = node.location().x();
            int y = node.location().y();

            fences += addTopFence(map, x, y, value) ? 1 : 0;
            fences += addRightFence(map, x, y, value) ? 1 : 0;
            fences += addBottomFence(map, x, y, value) ? 1 : 0;
            fences += addLeftFence(map, x, y, value) ? 1 : 0;
        }

        return fences;
    }

    private static boolean addTopFence(char[][] map, int x, int y, char value) {
        if (topExists(x, y, map, value)) {
            return false;
        }

        if (!rightExists(x, y, map, value)) {
            return true;
        }

        return topRightExists(x, y, map, value);
    }

    private static boolean addRightFence(char[][] map, int x, int y, char value) {
        if (rightExists(x, y, map, value)) {
            return false;
        }

        if (!bottomExists(x, y, map, value)) {
            return true;
        }

        return bottomRightExists(x, y, map, value);
    }

    private static boolean addBottomFence(char[][] map, int x, int y, char value) {
        if (bottomExists(x, y, map, value)) {
            return false;
        }

        if (!rightExists(x, y, map, value)) {
            return true;
        }

        return bottomRightExists(x, y, map, value);
    }

    private static boolean addLeftFence(char[][] map, int x, int y, char value) {
        if (leftExists(x, y, map, value)) {
            return false;
        }

        if (!bottomExists(x, y, map, value)) {
            return true;
        }

        return bottomLeftExists(x, y, map, value);
    }

    private static boolean topExists(int x, int y, char[][] map, char value) {
        return sameValue(x, y - 1, map, value);
    }

    private static boolean leftExists(int x, int y, char[][] map, char value) {
        return sameValue(x - 1, y, map, value);
    }
    private static boolean rightExists(int x, int y, char[][] map, char value) {
        return sameValue(x + 1, y, map, value);
    }
    private static boolean bottomExists(int x, int y, char[][] map, char value) {
        return sameValue(x, y + 1, map, value);
    }

    private static boolean topLeftExists(int x, int y, char[][] map, char value) {
        return sameValue(x - 1, y - 1, map, value);
    }

    private static boolean topRightExists(int x, int y, char[][] map, char value) {
        return sameValue(x + 1, y - 1, map, value);
    }
    private static boolean bottomLeftExists(int x, int y, char[][] map, char value) {
        return sameValue(x - 1, y + 1, map, value);
    }
    private static boolean bottomRightExists(int x, int y, char[][] map, char value) {
        return sameValue(x + 1, y + 1, map, value);
    }

    private static boolean sameValue(int x, int y, char[][] map, char value) {
        return insideMap(x, y, map) && map[y][x] == value;
    }

    private static Graph constructGraph(char[][] map) {
        List<Point> visited = new ArrayList<>();
        Graph graph = new Graph(new ArrayList<>());
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (visited.contains(new Point(x, y))) {
                    continue;
                }
                List<Node> nodes = parseSection(map, new Point(x, y), visited);
                graph.sections().add(nodes);
            }
        }

        return graph;
    }

    private static List<Node> parseSection(char[][] map, Point point, List<Point> visited) {
        List<Node> section = new ArrayList<>();
        LinkedList<Node> stack = new LinkedList<>();
        stack.addFirst(new Node(point, map[point.y()][point.x()], new ArrayList<>()));

        while (!stack.isEmpty()) {
            Node current = stack.removeFirst();
            if (visited.contains(current.location())) {
                continue;
            }
            section.add(current);

            List<Node> adjacent = findAdjacent(current, map);
            current.vertices().addAll(adjacent);
            for (Node adjacentNode : adjacent) {
                if (!visited.contains(adjacentNode.location())) {
                    stack.addFirst(adjacentNode);
                }
            }

            visited.add(current.location());
        }

        return section;
    }

    private static List<Node> findAdjacent(Node current, char[][] map) {
        int x = current.location().x();
        int y = current.location().y();
        char value = current.value();
        List<Node> adjacent = new ArrayList<>();

        addIfNeeded(value, map, x - 1, y, adjacent);
        addIfNeeded(value, map, x + 1, y, adjacent);
        addIfNeeded(value, map, x, y - 1, adjacent);
        addIfNeeded(value, map, x, y + 1, adjacent);

        return adjacent;
    }

    private static void addIfNeeded(char value, char[][] map, int x, int y, List<Node> adjacent) {
        if (isValidNode(x, y, value, map)) {
            adjacent.add(new Node(new Point(x, y), value, new ArrayList<>()));
        }
    }

    private static boolean isValidNode(int x, int y, char value, char[][] map) {
        if (!insideMap(x, y, map)) {
            return false;
        }

        return map[y][x] == value;
    }

    private static boolean insideMap(int x, int y, char[][] map) {
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }
}
