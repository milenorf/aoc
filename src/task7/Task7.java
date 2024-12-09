package task7;

import utils.Operation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static utils.Utils.parse;

public class Task7 {
    public static void solve() {
//        List<String> lines = parse("src/task7/sample.txt");
        List<String> lines = parse("src/task7/input.txt");

        long result = 0;
        for (String line : lines) {
            String[] input = line.split(":");
            long total = Long.parseLong(input[0]);
            List<Long> nums = Arrays.stream(input[1].strip().split(" ")).map(Long::parseLong).toList();
            boolean calibration = calibrate(new LinkedList<>(nums), total);
            if (calibration) {
                result += total;
            }
//            System.out.println(calibration);
        }
        System.out.println(result);
    }

    private static boolean calibrate(List<Long> nums, long total) {
        LinkedList<Long> stack = new LinkedList<>(nums);
        long soFar = stack.removeFirst();
        return calibrateRec(new LinkedList<>(stack), total, soFar, Operation.ADD)
                || calibrateRec(new LinkedList<>(stack), total, soFar, Operation.MULTIPLY)
                || calibrateRec(new LinkedList<>(stack), total, soFar, Operation.CONCAT);
    }

    private static boolean calibrateRec(LinkedList<Long> stack, long total, long soFar, Operation operation) {
        if (stack.isEmpty()) {
            return soFar == total;
        }
        long num = stack.removeFirst();
        soFar = calc(num, operation, soFar);
        return calibrateRec(new LinkedList<>(stack), total, soFar, Operation.ADD)
                || calibrateRec(new LinkedList<>(stack), total, soFar, Operation.MULTIPLY)
                || calibrateRec(new LinkedList<>(stack), total, soFar, Operation.CONCAT);
    }

    private static long calc(long num, Operation operation, long soFar) {
        return switch (operation) {
            case MULTIPLY -> soFar * num;
            case ADD -> soFar + num;
            case CONCAT -> concat(soFar, num);
        };
    }

    private static long concat(long soFar, long num) {
        return Long.parseLong("" + soFar + num);
    }


}
