package task9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static utils.Utils.parse;

public class Task9 {
    public static void solve() {
//        List<String> lines = parse("src/task9/sample.txt");
        List<String> lines = parse("src/task9/input.txt");
        String line = lines.get(0);

        int id = 0;
        List<MemoryLocation> memory = new ArrayList<>();
        for (int index = 0; index < line.length(); index++) {
            int count = Integer.parseInt(line.charAt(index) + "");
            for (int counter = 0; counter < count; counter++) {
                if (index % 2 == 0) {
                    memory.add(new File(id));
                } else {
                    memory.add(new Space());
                }
            }
            if (index % 2 == 0) {
                id++;
            }
        }

        printArray(memory);

        int start = 0;
        int moverIndex = memory.size() - 1;
        while (start != moverIndex) {
            if (memory.get(start).getMemoryType() != MemoryType.Space) {
                start++;
                continue;
            }

            if (memory.get(moverIndex).getMemoryType() == MemoryType.Space) {
                moverIndex--;
                continue;
            }

            MemoryLocation fileChunk = memory.get(moverIndex);
            memory.set(start, fileChunk);
            memory.set(moverIndex, new Space());
            start++;
            moverIndex--;
        }

        long checksum = 0;
        for (int index = 0; index < memory.size(); index++) {
            MemoryLocation memoryLocation = memory.get(index);
            int fileId = memoryLocation.getMemoryType() == MemoryType.File ? memoryLocation.getId() : 0;
            checksum += (long) fileId * index;
        }


        System.out.println(checksum);
    }

    private static void printArray(List<MemoryLocation> memory) {
        for (MemoryLocation memoryLocation : memory) {
            System.out.print(memoryLocation.toString());
        }
        System.out.println();
    }

    public static void solve2() {
//        List<String> lines = parse("src/task9/sample.txt");
        List<String> lines = parse("src/task9/input.txt");
        String line = lines.get(0);

        List<MemoryLocation> memory = new ArrayList<>();
        LinkedList<File> fileStack = new LinkedList<>();

        int id = 0;
        for (int index = 0; index < line.length(); index++) {
            int count = Integer.parseInt(line.charAt(index) + "");
            if (index % 2 == 0) {
                File file = new File(id, count);

                memory.add(file);
                fileStack.addFirst(file);

                id++;
            } else {
                memory.add(new Space(count));
            }
        }

//        printArray(memory);

        while (!fileStack.isEmpty()) {
            File file = fileStack.pop();

            int fileIndex = memory.size() - 1;
            while(!(memory.get(fileIndex).getMemoryType() == MemoryType.File && memory.get(fileIndex).getId() == file.getId())) {
                fileIndex--;
            }

            int spaceIndex = 0;
            while (!isValidLocation(spaceIndex, memory, file)) {
                if (spaceIndex == fileIndex) {
                    spaceIndex = -1;
                    break;
                }
                spaceIndex++;
            }
            if (spaceIndex == -1) {
                continue;
            }


            Space space = (Space) memory.get(spaceIndex);
            if (space.getSize() == file.getSize()) {
                memory.set(spaceIndex, file);
                memory.set(fileIndex, space);
                mergeSpace(memory, fileIndex);
            } else {
                int spaceLeft = space.getSize() - file.getSize();
                memory.set(spaceIndex, new Space(spaceLeft));
                memory.add(spaceIndex, file);
                memory.set(fileIndex + 1, new Space(file.getSize()));
                mergeSpace(memory, fileIndex + 1);
            }
//            System.out.println("done");
        }

        long checksum = calculateChecksum(memory);


        System.out.println(checksum);
    }

    private static void mergeSpace(List<MemoryLocation> memory, int i) {
        int size = memory.get(i).getSize();
        MemoryLocation prev = memory.get(i - 1);
        MemoryLocation next = i == memory.size() - 1 ? new File(-1, -1) : memory.get(i + 1);
        if (prev.getMemoryType() == MemoryType.Space && next.getMemoryType() != MemoryType.Space) {
            size += prev.getSize();
            memory.set(i - 1, new Space(size));
            memory.remove(i);
        } else if (prev.getMemoryType() != MemoryType.Space && next.getMemoryType() == MemoryType.Space) {
            size += next.getSize();
            memory.set(i, new Space(size));
            memory.remove(i + 1);
        } else if (prev.getMemoryType() == MemoryType.Space && next.getMemoryType() == MemoryType.Space) {
            size += next.getSize();
            size += prev.getSize();
            memory.set(i - 1, new Space(size));
            memory.remove(i + 1);
            memory.remove(i);
        }
    }

    private static long calculateChecksum(List<MemoryLocation> memory) {
        long checksum = 0;
        int checksumIndex = 0;
        for (MemoryLocation memoryLocation : memory) {
            if (memoryLocation.getMemoryType() == MemoryType.File) {
                for (int count = 0; count < memoryLocation.getSize(); count++) {
                    checksum += (long) memoryLocation.getId() * checksumIndex;
                    checksumIndex++;
                }
            } else {
                checksumIndex += memoryLocation.getSize();
            }
        }
        return checksum;
    }

    private static boolean isValidLocation(int start, List<MemoryLocation> memory, File file) {
        MemoryLocation memoryLocation = memory.get(start);
        if (memoryLocation.getMemoryType() != MemoryType.Space) {
            return false;
        }

        return memoryLocation.getSize() >= file.getSize();
    }

}
