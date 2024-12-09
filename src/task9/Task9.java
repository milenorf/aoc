package task9;

import utils.Direction;
import utils.Point;
import utils.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static utils.Utils.*;

public class Task9 {
    public static void solve() {
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task9/sample.txt");
        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task9/input.txt");
//        List<String> lines = parse("/Users/milen.orfeev/workspace/aoc/src/task9/input.txt");
        String line = lines.get(0);

        int id = 0;
        List<Character> memory = new ArrayList<>();
        for (int index = 0; index < line.length(); index++) {
                int count = Integer.parseInt(line.charAt(index)+ "");
                for (int counter = 0; counter < count; counter++) {
                    if (index % 2 == 0) {
                        char e = forDigit(id);
                        memory.add(e);
                    } else {
                        memory.add('.');
                    }
                }
                if (index %2 == 0) {
                    id++;
                }
        }

        printArray(memory);

        int start = 0;
        int moverIndex = memory.size() - 1;
        while(start != moverIndex) {
            if (memory.get(start) != '.') {
                start++;
                continue;
            }

            if (memory.get(moverIndex) == '.') {
                moverIndex--;
                continue;
            }

            char fileChunk = memory.get(moverIndex);
            memory.set(start, fileChunk);
            memory.set(moverIndex, '.');
            start++;
            moverIndex--;
        }

        printArray(memory);
    }

    private static char forDigit(int id) {
        if (id == 0)
        return Character.forDigit(id, 10);
    }

    private static void printArray(List<Character> memory) {
        for (char ch: memory) {
            System.out.print(ch);
        }
        System.out.println();
    }

    public static void solve2() {
    }

    private record File(int size, int id) {
    }

    private record Space(int size) {
    }
}
