package task10;

import utils.Point;

import java.util.*;

import static utils.Utils.*;

public class Task10 {
    public static void solve() {
//        List<String> lines = parse("src/task10/sample1.txt");
//        List<String> lines = parse("src/task10/sample.txt");
        List<String> lines = parse("src/task10/input.txt");

        int[][] map = parseIntMap(lines);

        List<Point> trailHeads = findTrailheads(map);

        long score = 0;
        for (Point trailHead : trailHeads) {
            score += calculateScore(trailHead, map);
        }

        System.out.println(score);
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
