package task6;

import utils.Direction;
import utils.Point;
import utils.Position;

import java.util.HashSet;
import java.util.List;

import static utils.Utils.*;

public class Task6 {
    public static void solve() {
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/input.txt");
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/input.txt");
        char[][] map = parseMap(lines);

        Point start = findStart(map);

        Direction direction = Direction.UP;
        Point current = start;
        int visited = 0;
        while(insideMap(map, current)) {
            int x = current.x();
            int y = current.y();
            if (map[y][x] != 'X') {
                map[y][x] = 'X';
                visited++;
            }
            Point next = getNext(current, direction);
            while(isObstacle(map, next)) {
                direction = Direction.rotate90(direction);
                next = getNext(current, direction);
            }
            current = next;
        }
        System.out.println(visited);
        printMap(map);
    }

    public static void solve2() {
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/sample.txt");
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/input.txt");
        char[][] ogMap = parseMap(lines);

        int validObstructions = 0;

        for (int y = 0; y < ogMap.length; y++) {
            for (int x = 0; x < ogMap.length; x++) {
                if (canPlaceObstruction(x, y, ogMap)) {
//                    char[][] curMap = copyMap(ogMap);
                    ogMap[y][x] = '#';
                    if (isStuck(ogMap)) {
                        validObstructions++;
                    }
                    ogMap[y][x] = '.';
                }
            }
        }

        System.out.println(validObstructions);
    }

    private static boolean isStuck(char[][] map) {
        HashSet<Position> seen = new HashSet<>();

        Point start = findStart(map);

        Direction direction = Direction.UP;
        Point currentPoint = start;
        while(insideMap(map, currentPoint)) {
            Position currentPosition = new Position(currentPoint, direction);
            if (seen.contains(currentPosition)) {
                return true;
            }
            seen.add(currentPosition);
            int x = currentPoint.x();
            int y = currentPoint.y();
            Point next = getNext(currentPoint, direction);
            while(isObstacle(map, next)) {
                direction = Direction.rotate90(direction);
                next = getNext(currentPoint, direction);
            }
            currentPoint = next;
        }

        return false;
    }

    private static boolean canPlaceObstruction(int x, int y, char[][] map) {
        return map[y][x] != '^' && map[y][x] != '#';
    }

    private static boolean isObstacle(char[][] array, Point p) {
        return insideMap(array, p) && array[p.y()][p.x()] == '#';
    }

    private static Point getNext(Point p, Direction direction) {
        int x = p.x();
        int y = p.y();
        return switch (direction) {
            case UP -> new Point(x, y - 1);
            case RIGHT -> new Point(x + 1, y);
            case DOWN -> new Point(x, y + 1);
            case LEFT -> new Point(x - 1, y);
        };
    }

    private static boolean insideMap(char[][] map, Point p) {
        int x = p.x();
        int y = p.y();
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }

    private static Point findStart(char[][] array) {
        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[0].length; x++) {
                if (array[y][x] == '^') {
                    return new Point(x, y);
                }
            }
        }

        throw new RuntimeException("Couldn't find start");
    }

}
