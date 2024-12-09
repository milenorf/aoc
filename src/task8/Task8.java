package task8;

import utils.Distance;
import utils.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static utils.Utils.*;

public class Task8 {
    public static void solve() {
//        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task8/sample.txt");
        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task8/input.txt");
        char[][] map = parseMap(lines);
        HashMap<Character, List<Point>> antennaLocations = new HashMap<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                char c = map[y][x];
                if (c != '.') {
                    List<Point> antennas = antennaLocations.getOrDefault(c, new ArrayList<>());
                    antennas.add(new Point(x, y));
                    antennaLocations.put(c, antennas);
                }
            }
        }

        HashSet<Point> amplitutes = new HashSet<>();
        for(Character antennaType: antennaLocations.keySet()) {
            List<Point> antennas = antennaLocations.get(antennaType);
            for(int index = 0; index < antennas.size(); index++) {
                Point a1 = antennas.get(index);
                for(int innerIndex = index + 1; innerIndex < antennas.size(); innerIndex++) {
                    Point a2 = antennas.get(innerIndex);
                    Distance distance = calcDistance(a1, a2);
                    Point current = new Point(a1.x() - distance.x(), a1.y() - distance.y());
                    while (insideMap(map, current)) {
                        addAmplitude(current, amplitutes, map);
                        current = new Point(current.x() - distance.x(), current.y() - distance.y());
                    }
                    Point current2 = new Point(a2.x() + distance.x(), a2.y() + distance.y());
                    while(insideMap(map, current2)) {
                        addAmplitude(current2, amplitutes, map);
                        current2 = new Point(current2.x() + distance.x(), current2.y() + distance.y());
                    }
                }
                if (antennas.size() > 1) {
                    amplitutes.addAll(antennas);
                }
            }
        }

        printMap(map);
        System.out.println(amplitutes.size());
    }

    private static void addAmplitude(Point amplitude, HashSet<Point> amplitutes, char[][] map) {
        if (insideMap(map, amplitude)) {
            amplitutes.add(amplitude);
            map[amplitude.y()][amplitude.x()] = '#';
        }
    }

    private static Distance calcDistance(Point a1, Point a2) {
        return new Distance(a2.x() - a1.x(), a2.y() - a1.y());
    }

    public static void solve2() {
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/sample.txt");
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task6/input.txt");
        char[][] ogMap = parseMap(lines);

    }


    private static boolean insideMap(char[][] map, Point p) {
        int x = p.x();
        int y = p.y();
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }
}
