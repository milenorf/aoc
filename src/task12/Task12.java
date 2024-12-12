package task12;

import utils.Point;

import java.util.*;
import java.util.stream.Collectors;

import static utils.Utils.*;

public class Task12 {
    public static void solve() {
//        List<String> lines = parse("src/task12/sample1.txt");
//        List<String> lines = parse("src/task12/sample.txt");
//        List<String> lines = parse("src/task12/input.txt");
        List<String> lines = parse("src/task12/input.txt");
        char[][] map = parseMap(lines);

        Graph graph = constructGraph(map);


        String line = lines.get(0);
//        solveInt(line);
        solveChar(line);

    }

    private static Graph constructGraph(char[][] map) {
        List<Point> visited = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (visited.contains(new Point(x, y))) {
                    continue;
                }
                parseSection(map, new Point(x, y), visited);
            }
        }
    }

    private static List<Node> parseSection(char[][] map, Point point, List<Point> visited) {
        List<Node> section = new ArrayList<>();
        LinkedList<Node> stack = new LinkedList<>();
        stack.addFirst(new Node(point, map[point.y()][point.x()], new ArrayList<>()));

        while (!stack.isEmpty()) {
            Node current = stack.removeFirst();
            section.add(current);

            List<Node> adjacent = findAdjacent(current, map);
            current.vertices().addAll(adjacent);
            for (Node adjacentNode : adjacent) {
                if (!visited.contains(adjacentNode)) {
                    stack.addFirst(adjacentNode);
                }
            }

            visited.add(current);
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
            adjacent.add(new Node(new Point(x, y), map[y][x], new ArrayList<>()));
        }
    }

    private static boolean isValidNode(int x, int y, char value, char[][] map) {
        if (!insideMap(x, y, map)) {
            return false;
        }

        return map[x][y] == value;
    }

    private static void solveInt(String line) {
        List<Long> stones = Arrays.stream(line.split(" ")).map(Task12::toStone).toList();

        int blinks = 75;
        List<Long> curStones = new ArrayList<>(stones);
        for(int blink = 1; blink <= blinks; blink++) {

            List<Long> nextStones = new ArrayList<>();
            for (int index = 0; index < curStones.size(); index++) {
                long stone = curStones.get(index);
                if (stone == 0) {
                    nextStones.add(1L);
                } else {
                    String num = String.valueOf(stone);
                    if (num.length() % 2 == 0) {
                        nextStones.add(Long.parseLong(num.substring(0, num.length() / 2)));
                        nextStones.add(Long.parseLong(num.substring(num.length() / 2)));
                    } else {
                        nextStones.add(stone * 2024);
                    }
                }
            }
            curStones = nextStones;
            System.out.println(blink + " " + curStones.size());
//            printStones(curStones);
        }

        System.out.println(curStones.size());
    }

    private static void solveChar(String line) {
        List<char[]> stones = Arrays.stream(line.split(" ")).map(String::toCharArray).toList();

        int blinks = 75;
        List<char[]> curStones = new ArrayList<>(stones);
        for(int blink = 1; blink <= blinks; blink++) {

            List<char[]> nextStones = new ArrayList<>();
            for (int index = 0; index < curStones.size(); index++) {
                char[] stone = curStones.get(index);
                if (stone[0] == '0') {
                    nextStones.add(new char[]{'1'});
                } else {
                    if (stone.length % 2 == 0) {
                        nextStones.add(copyChar(stone, 0, stone.length / 2));
                        nextStones.add(copyChar(stone, stone.length / 2, stone.length));
                    } else {
                        long newNumber = Long.parseLong(new String(stone)) * 2024;
                        char[] newStone = String.valueOf(newNumber).toCharArray();
                        nextStones.add(newStone);
                    }
                }
            }
            curStones = nextStones;
            System.out.println(blink + " " + curStones.size());
//            printStones(curStones);
        }

        System.out.println(curStones.size());

    }

    private static char[] copyChar(char[] array, int startIndex, int endIndex) {
        int zeros = 0;
        for (int index = startIndex; index < endIndex; index++) {
            if (array[index] != 0) {
                break;
            }
            zeros++;
        }
        char[] copy = new char[endIndex - zeros];
        for (int i = startIndex; i  < endIndex; i++) {
            copy[i] = array[i];
        }

        return copy;
    }

    private static void printStones(List<Long> curStones) {
        System.out.println(curStones.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }

    private static boolean evenDigitsCount(long stone) {
        return String.valueOf(stone).length() % 2 == 0;
    }

    private static long toStone(String s) {
        return Long.parseLong(s);
    }

    public static void solve2() {
//        List<String> lines = parse("src/task10/sample1.txt");
//        List<String> lines = parse("src/task10/sample.txt");
        List<String> lines = parse("src/task10/input.txt");

        int[][] map = parseIntMap(lines);

        List<Point> trailHeads = findTrailheads(map);

        long rate = 0;
        for (Point trailHead : trailHeads) {
            rate += calculateRate(trailHead, map);
        }

        System.out.println(rate);
    }

    private static long calculateRate(Point trailHead, int[][] map) {
        LinkedList<Point> stack = new LinkedList<>();
        long rate = 0;
        stack.addFirst(trailHead);

        while (!stack.isEmpty()) {
            Point point = stack.removeFirst();
            int x = point.x();
            int y = point.y();

            int height = map[y][x];
            if (height == 9) {
                rate += 1;
            } else {
                addIfValid(x - 1, y, stack, map, height);
                addIfValid(x + 1, y, stack, map, height);
                addIfValid(x, y - 1, stack, map, height);
                addIfValid(x, y + 1, stack, map, height);
            }
        }

        return rate;
    }

    private static long calculateScore(Point trailHead, int[][] map) {
        LinkedList<Point> stack = new LinkedList<>();
        HashSet<Point> finals = new HashSet<>();
        stack.addFirst(trailHead);

        while (!stack.isEmpty()) {
            Point point = stack.removeFirst();
            int x = point.x();
            int y = point.y();

            int height = map[y][x];
            if (height == 9) {
                finals.add(point);
            } else {
                addIfValid(x - 1, y, stack, map, height);
                addIfValid(x + 1, y, stack, map, height);
                addIfValid(x, y - 1, stack, map, height);
                addIfValid(x, y + 1, stack, map, height);
            }
        }

        return finals.size();
    }

    private static void addIfValid(int x, int y, LinkedList<Point> stack, int[][] map, int height) {
        if (!insideMap(x, y, map)) {
            return;
        }

        if (map[y][x] - height == 1) {
            stack.addFirst(new Point(x, y));
        }
    }

    private static boolean insideMap(int x, int y, int[][] map) {
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }

    private static boolean insideMap(int x, int y, char[][] map) {
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }

    private static List<Point> findTrailheads(int[][] map) {
        List<Point> trailHeads = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 0) {
                    trailHeads.add(new Point(x, y));
                }
            }
        }
        return trailHeads;
    }
}
