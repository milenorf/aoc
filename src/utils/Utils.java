package utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

public class Utils {
    public static List<String> parse(String filename) {
        try {
            return readAllLines(Path.of(filename).toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMap(char[][] map){
        String output = Arrays.stream(map)
                .map(String::new)
                .collect(Collectors.joining("\n"));

        System.out.println(output);
    }

    public static char[][] copyMap(char[][] map) {
        char[][] copy = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                copy[y][x] = map[y][x];
            }
        }

        return copy;
    }

    public static char[][] parseMap(List<String> lines) {
        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            map[index]= line.toCharArray();
        }

        return map;
    }

    public static boolean insideMap(char[][] map, Point p) {
        int x = p.x();
        int y = p.y();
        return y >= 0 && y < map.length
                && x >= 0 && x < map[0].length;
    }

    public static int[][] parseIntMap(List<String> lines) {
        int[][] map = new int[lines.size()][lines.get(0).length()];
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            int[] intLine = new int[line.length()];
            for (int innerIndex = 0; innerIndex < intLine.length; innerIndex++) {
                intLine[innerIndex] = Character.digit(line.charAt(innerIndex), 10);
            }
            map[index] = intLine;
        }

        return map;
    }
}
