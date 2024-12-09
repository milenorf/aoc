package task3;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task3 {
    public static void solve() {
        List<String> lines = parse("src/task3/input.txt");
        Pattern stringPattern = Pattern.compile("mul\\((\\d*),(\\d*)\\)");
        int total = 0;
        for (String line : lines) {
            Matcher m = stringPattern.matcher(line);
            while (m.find()) {
                int x = Integer.parseInt(m.group(1));
                int y = Integer.parseInt(m.group(2));

                total += x * y;
            }

        }
        System.out.println(total);
    }

    public static void solve2() {
        List<String> lines = parse("src/task3/input.txt");
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task3/sample2.txt");
        String line = String.join("", lines);
        Pattern mulPattern = Pattern.compile("mul\\((\\d*),(\\d*)\\)");
//        Pattern doPattern = Pattern.compile("do\\(\\)");
//        Pattern dontPattern = Pattern.compile("don't\\(\\)");
//        Matcher doMatch = mulPattern.matcher(line);
//        Matcher dontMatch = mulPattern.matcher(line);
        int total = 0;
        String[] dontLines = line.split("don't\\(\\)");
        boolean isStart = true;
        for (String dontLine: dontLines) {
            String[] doLines = dontLine.split("do\\(\\)");
            int index = 1;
            if (isStart) {
                index = 0;
                isStart = false;
            }
            for (; index < doLines.length; index++) {
                Matcher mul = mulPattern.matcher(doLines[index]);
                while (mul.find()) {
                    int x = Integer.parseInt(mul.group(1));
                    int y = Integer.parseInt(mul.group(2));

                    total += x * y;
                }
            }
        }
        System.out.println(total);
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
