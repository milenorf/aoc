package task4;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Task4 {
    public static void solve() {
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task4/input.txt");
        char[][] array = new char[lines.size()][lines.get(0).length()];
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            array[index]= line.toCharArray();
        }

        int count = 0;
        for (int ri = 0; ri < array.length; ri++) {
            for (int ci = 0; ci < array[0].length; ci++) {
                count += checkRight(ri, ci, array);
                count += checkDown(ri, ci, array);
                count += checkUp(ri, ci, array);
                count += checkLeft(ri, ci, array);
                count += checkDiagLeftUp(ri, ci, array);
                count += checkDiagLeftDown(ri, ci, array);
                count += checkDiagRightUp(ri, ci, array);
                count += checkDiagRightDown(ri, ci, array);
            }
        }

        System.out.println(count);
    }

    public static void solve2() {
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task4/input.txt");
        char[][] array = new char[lines.size()][lines.get(0).length()];
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            array[index]= line.toCharArray();
        }

        int count = 0;
        for (int ri = 0; ri < array.length; ri++) {
            for (int ci = 0; ci < array[0].length; ci++) {
                count += checkX(ri, ci, array);
//                count += checkRight(ri, ci, array);
//                count += checkDown(ri, ci, array);
//                count += checkUp(ri, ci, array);
//                count += checkLeft(ri, ci, array);
//                count += checkDiagLeftUp(ri, ci, array);
//                count += checkDiagLeftDown(ri, ci, array);
//                count += checkDiagRightUp(ri, ci, array);
//                count += checkDiagRightDown(ri, ci, array);
            }
        }

        System.out.println(count);
    }

    private static int checkX(int ri, int ci, char[][] array) {
        if (getSafe(ri, ci, array) != 'A') {
            return 0;
        }
        char topLeft = getSafe(ri - 1, ci - 1, array);
        char topRight = getSafe(ri - 1, ci + 1, array);
        char bottomLeft = getSafe(ri + 1,ci - 1, array);
        char bottomRight = getSafe(ri + 1, ci + 1, array);

        if (diagMatches(topLeft, bottomRight) && diagMatches(topRight, bottomLeft)) {
            return 1;
        }

        return 0;
    }

    private static boolean diagMatches(char top, char bottom) {
        return (top == 'M' && bottom == 'S') || (top == 'S' && bottom == 'M');
    }

    private static int checkDown(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri + 1, ci, array);
        char c3 = getSafe(ri + 2, ci, array);
        char c4 = getSafe(ri + 3, ci, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkUp(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri - 1, ci, array);
        char c3 = getSafe(ri - 2, ci, array);
        char c4 = getSafe(ri - 3, ci, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkRight(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri, ci + 1, array);
        char c3 = getSafe(ri, ci + 2, array);
        char c4 = getSafe(ri, ci + 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkLeft(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri, ci - 1, array);
        char c3 = getSafe(ri, ci - 2, array);
        char c4 = getSafe(ri, ci - 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkDiagRightDown(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri + 1, ci + 1, array);
        char c3 = getSafe(ri + 2, ci + 2, array);
        char c4 = getSafe(ri + 3, ci + 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkDiagLeftUp(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri - 1, ci - 1, array);
        char c3 = getSafe(ri - 2, ci - 2, array);
        char c4 = getSafe(ri - 3, ci - 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkDiagLeftDown(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri + 1, ci - 1, array);
        char c3 = getSafe(ri + 2, ci - 2, array);
        char c4 = getSafe(ri + 3, ci - 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkDiagRightUp(int ri, int ci, char[][] array) {
        char c1 = getSafe(ri, ci, array);
        char c2 = getSafe(ri - 1, ci + 1, array);
        char c3 = getSafe(ri - 2, ci + 2, array);
        char c4 = getSafe(ri - 3, ci + 3, array);

        return checkWord(c1, c2, c3, c4);
    }

    private static int checkWord(char c1, char c2, char c3, char c4) {
        String word = new StringBuilder().append(c1).append(c2).append(c3).append(c4).toString();
        return word.equals("XMAS") ? 1 : 0;
    }

    private static char getSafe(int ri, int ci, char[][] array) {
        if (ri < array.length && ri >= 0 && ci < array[0].length && ci >= 0) {
            return array[ri][ci];
        }
        return '!';
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
