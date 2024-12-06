package task5;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

public class Task5 {
    public static void solve() {
//        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task1/sample.txt");
        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task5/input.txt");

        HashMap<Integer, Set<Integer>> deps = new HashMap<>();
        int index = 0;
        while (!lines.get(index).strip().isBlank()) {
            String line = lines.get(index);

            List<Integer> nums = Arrays.stream(line.strip().split("\\|"))
                    .map(Integer::parseInt)
                    .toList();

            Set<Integer> depsSet = deps.getOrDefault(nums.get(0), new HashSet<>());
            depsSet.add(nums.get(1));
            deps.put(nums.get(0), depsSet);
            index++;
        }
        index++;

        int sum = 0;
        while (index < lines.size()) {
            String line = lines.get(index);
            if (checkLine(line, deps)) {
                sum += getMiddle(line);
            }
            index++;
        }
        System.out.println(sum);
    }

    private static boolean checkLine(String line, HashMap<Integer, Set<Integer>> deps) {
        List<Integer> nums = Arrays.stream(line.strip().split(","))
                .map(Integer::parseInt)
                .toList();

        HashSet<Integer> metSoFar = new HashSet<>();
        for (int num : nums) {
            Set<Integer> required = deps.getOrDefault(num, new HashSet<>());
            for (int reqNum : required) {
                if (metSoFar.contains(reqNum)) {
                    return false;
                }
            }
            metSoFar.add(num);
        }

        return true;
    }

    public static void solve2() {
        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task5/input.txt");
//        List<String> lines = parse("/Users/milenorfeev/projects/aoc/src/task5/input.txt");

        HashMap<Integer, Set<Integer>> deps = new HashMap<>();
        int index = 0;
        while (!lines.get(index).strip().isBlank())  {
            String line = lines.get(index);

            List<Integer> nums = Arrays.stream(line.strip().split("\\|"))
                    .map(Integer::parseInt)
                    .toList();

            Set<Integer> depsSet = deps.getOrDefault(nums.get(0), new HashSet<>());
            depsSet.add(nums.get(1));
            deps.put(nums.get(0), depsSet);
            index++;
        }
        index++;

        int sum = 0;
        while (index < lines.size()) {
            String line = lines.get(index);
            if (!checkLine(line, deps)) {
                List<Integer> fixedLine = fixLine(line, deps);
                System.out.println(fixedLine);
                sum += getMiddle(fixedLine);
            }
            index++;
        }
        System.out.println(sum);
    }

    private static List<Integer> fixLine(String line, HashMap<Integer, Set<Integer>> deps) {
        List<Integer> nums = Arrays.stream(line.strip().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<Integer> result = new ArrayList<>();

        while (!nums.isEmpty()) {
            int nextNumIndex = findNext(nums, deps);
            result.add(nums.get(nextNumIndex));
            nums.remove(nextNumIndex);
        }

        return result;
    }

    private static int getMiddle(String line) {
        String nums[] = line.strip().split(",");
        String middle = nums[nums.length / 2];
        return Integer.parseInt(middle);
    }

    private static int getMiddle(List<Integer> nums) {
        return nums.get(nums.size() / 2);
    }

    public static List<String> parse(String filename) {
        try {
            return readAllLines(Paths.get(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findNext(List<Integer> nums, HashMap<Integer, Set<Integer>> deps) {
        for (int index = 0; index < nums.size(); index++) {
            boolean contains = false;
            for (int otherNum : nums) {
                if (deps.getOrDefault(otherNum, new HashSet<>()).contains(nums.get(index))) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                return index;
            }
        }

        throw new RuntimeException("fail");
    }
}
