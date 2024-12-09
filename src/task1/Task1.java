package task1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.Utils.parse;

public class Task1 {
    public static void solve() {
//        List<String> lines = parse("src/task1/sample.txt");
        List<String> lines = parse("src/task1/input.txt");
        List<Integer> leftNums = new ArrayList<>();
        List<Integer> rightNums = new ArrayList<>();

        for (String line: lines) {
            String[] numbers = line.split(" {3}");

            leftNums.add(Integer.parseInt(numbers[0]));
            rightNums.add(Integer.parseInt(numbers[1]));
        }

        leftNums.sort(Comparator.naturalOrder());
        rightNums.sort(Comparator.naturalOrder());

        int totalDistance = 0;

        for (int index = 0; index < leftNums.size(); index++) {
            int leftNumber = leftNums.get(index);
            int rightNumber = rightNums.get(index);

            int distance = Math.abs(leftNumber - rightNumber);
            totalDistance += distance;
        }

        System.out.println(totalDistance);
    }

    public static void solve2() {
//        List<String> lines = parse("src/task1/sample.txt");
        List<String> lines = parse("src/task1/input.txt");
        List<Integer> leftNums = new ArrayList<>();
        List<Integer> rightNums = new ArrayList<>();

        for (String line: lines) {
            String[] numbers = line.split(" {3}");

            leftNums.add(Integer.parseInt(numbers[0]));
            rightNums.add(Integer.parseInt(numbers[1]));
        }

        leftNums.sort(Comparator.naturalOrder());
        rightNums.sort(Comparator.naturalOrder());

        int similarityScore = getSimilarityScore(leftNums, rightNums);

        System.out.println(similarityScore);
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
}
