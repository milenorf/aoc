package utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

public class Utils {
    public static List<String> parse(String filename) {
        try {
            return readAllLines(Paths.get(filename));
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
}
