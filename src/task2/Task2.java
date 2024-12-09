package task2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Task2 {
    public static void solve() {
        List<String> lines = parse("src/task2/sample.txt");
        List<List<Integer>> reports = new ArrayList<>();
        for (String line : lines) {
            List<Integer> report = new ArrayList<>();
            for (String num : line.split(" ")) {
                report.add(Integer.valueOf(Integer.parseInt(num)));
            }
            reports.add(report);
        }

        int counter = 0;
        for (List<Integer> report : reports) {
            if (checkReport(report)) {
                counter += 1;
            }
        }

        System.out.println(counter);
    }

    public static void solve2() {
        List<String> lines = parse("src/task2/input.txt");
        List<List<Integer>> reports = new ArrayList<>();
        for (String line : lines) {
            List<Integer> report = new ArrayList<>();
            for (String num : line.split(" ")) {
                report.add(Integer.valueOf(Integer.parseInt(num)));
            }
            reports.add(report);
        }

        int counter = 0;
        for (List<Integer> report : reports) {
            if (checkReport(report)) {
                counter += 1;
            } else {
                for (int index = 0; index < report.size(); index++) {
                    List<Integer> copy = new ArrayList<>(report);
                    copy.remove(index);
                    if (checkReport(copy)) {
                        counter += 1;
                        break;
                    }
                }
            }
        }

        System.out.println(counter);
    }


    private static boolean checkReport(List<Integer> report) {
        boolean isIncreasing = report.get(0) < report.get(1);
        for (int index = 0; index < report.size() - 1; index++) {
            int num1 = report.get(index);
            int num2 = report.get(index + 1);
            int difference = Math.abs(num2 - num1);

            if (difference > 3 || difference < 1)  {
                System.out.println("report failed, big differemce");
                return false;
            }

            if (num2 > num1 && !isIncreasing) {
                System.out.println("report failed, should have been decreasing");
                return false;
            }

            if (num1 > num2 && isIncreasing) {
                System.out.println("report failed, should have been increasing");
                return false;
            }
        }

        return true;
    }

    private static int getSimilarityScore(List<Integer> leftNums, List<Integer> rightNums) {
        int rightIndex = 0;
        int leftIndex = 0;

        int similarityScore = 0;
        while (leftIndex < leftNums.size()) {
            int leftNumber = leftNums.get(leftIndex);
            while (rightIndex < rightNums.size() && rightNums.get(rightIndex) < leftNumber) {
                rightIndex++;
            }

            int similarCount = 0;

            while (rightIndex < rightNums.size() && rightNums.get(rightIndex) == leftNumber) {
                similarCount++;
                rightIndex++;
            }

            int leftSimilarCount = 0;
            while (leftIndex < leftNums.size() && leftNums.get(leftIndex) == leftNumber) {
                leftSimilarCount++;
                leftIndex++;
            }

            similarityScore += similarCount * leftNumber * leftSimilarCount;
        }
        return similarityScore;
    }

    public static List<String> parse(String filename) {
        try {
            return readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
