package task14;

import utils.Point;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.Utils.parse;
import static utils.Utils.printMap;

public class Task14 {
    public static void solve() {
        String file = "input";
//        String file = "sample";
        List<String> lines = parse("src/task14/" + file + ".txt");
        int xSize = file.equals("sample") ? 11 : 101;
        int ySize = file.equals("sample") ? 7 : 103;

        List<Robot> robots = parseRobots(lines);

        int seconds = 100;
        for (int second = 1; second <= seconds; second++) {
            List<Robot> newRobotLocations = new ArrayList<>();
            for (Robot robot : robots) {
                Point velocity = robot.velocity();
                Point location = robot.location();
                int newX = moveCoordinate(location.x(), velocity.x(), xSize);
                int newY = moveCoordinate(location.y(), velocity.y(), ySize);
                newRobotLocations.add(new Robot(new Point(newX, newY), velocity));
            }
            robots = newRobotLocations;
        }

        QuadrantCount robotCount = countRobots(robots, xSize, ySize);
        int answer = robotCount.q1() * robotCount.q2() * robotCount.q3() * robotCount.q4();
        System.out.println(answer);
    }

    public static void solve2() {
        String file = "input";
//        String file = "sample";
        List<String> lines = parse("src/task14/" + file + ".txt");
        int xSize = file.equals("sample") ? 11 : 101;
        int ySize = file.equals("sample") ? 7 : 103;

        List<Robot> robots = parseRobots(lines);
        char[][] map = initMap(xSize, ySize);

        long seconds = Long.MAX_VALUE - 1;
        for (long second = 1; second <= seconds; second++) {
            List<Robot> newRobotLocations = new ArrayList<>();
            for (Robot robot : robots) {
                Point velocity = robot.velocity();
                Point location = robot.location();
                int newX = moveCoordinate(location.x(), velocity.x(), xSize);
                int newY = moveCoordinate(location.y(), velocity.y(), ySize);
                newRobotLocations.add(new Robot(new Point(newX, newY), velocity));
            }
            robots = newRobotLocations;
            if (christmasTree(robots, xSize, ySize)) {
                printTree(robots, map);
                System.out.println(second);
                break;
            }
        }

    }

    private static boolean christmasTree(List<Robot> robots, int xSize, int ySize) {
//        QuadrantCount quadrantCount = countRobots(robots, xSize, ySize);
//        if(!symmetricalY(quadrantCount)) {
//            return false;
//        }
        int startY = 0;
        long count = countLine(robots, startY);
        for (int y = startY + 1; y < ySize / 2; y++) {
            long nextCount = countLine(robots, y);
            if (nextCount < count) {
                return false;
            }
            count = nextCount;
        }

        return true;
    }

    private static long countLine(List<Robot> robots, int lineNumber) {
        return robots.stream().filter(robot -> robot.location().y() == lineNumber).count();
    }

    private static boolean symmetricalY(QuadrantCount quadrantCount) {
        return quadrantCount.q1() == quadrantCount.q2() && quadrantCount.q3() == quadrantCount.q4();
    }

    private static void printTree(List<Robot> robots, char[][] map) {
        drawRobots(robots, map);
        printMap(map);
        System.out.println();
        clearRobots(robots, map);
    }

    private static void clearRobots(List<Robot> robots, char[][] map) {
        draw(robots, map, '-');
    }

    private static void draw(List<Robot> robots, char[][] map, char x) {
        for (Robot robot : robots) {
            Point location = robot.location();
            map[location.y()][location.x()] = x;
        }
    }

    private static void drawRobots(List<Robot> robots, char[][] map) {
        draw(robots, map, '*');
    }

    private static char[][] initMap(int xSize, int ySize) {
        char[][] map = new char[ySize][xSize];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                map[y][x] = '-';
            }
        }
        return map;
    }

    private static List<Robot> parseRobots(List<String> lines) {
        List<Robot> robots = new ArrayList<>();
        Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+)\\sv=(-?\\d+),(-?\\d+)");
        for (String line: lines) {
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                System.out.println("invalid line " + line);
            }
            Point location = new Point(groupInt(matcher, 1), groupInt(matcher, 2));
            Point velocity = new Point(groupInt(matcher, 3), groupInt(matcher, 4));
            robots.add(new Robot(location, velocity));
        }
        return robots;
    }

    private static QuadrantCount countRobots(List<Robot> robots, int xSize, int ySize) {
        int midX = xSize / 2;
        int midY = ySize / 2;

        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;

        for(Robot robot : robots) {
            Point location = robot.location();
            if (location.x() < midX) {
                if (location.y() < midY) {
                    q1++;
                } else if (location.y() > midY) {
                    q3++;
                }
            } else if (location.x() > midX) {
                if (location.y() < midY) {
                    q2++;
                } else if (location.y() > midY) {
                    q4++;
                }
            }
        }

//        printQuadrants(q1, q2, q3, q4);

        return new QuadrantCount(q1, q2, q3, q4);
    }

    private static void printQuadrants(int q1, int q2, int q3, int q4) {
        String output = Stream.of(q1, q2, q3, q4).map(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(output);
    }

    private static int moveCoordinate(int location, int velocity, int border) {
        return (location + velocity + border) % border;
    }

    private static int groupInt(Matcher matcher, int groupCount) {
        return Integer.parseInt(matcher.group(groupCount));
    }
}
