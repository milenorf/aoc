package task9;

public class Space extends MemoryLocation{
    @Override
    public String toString() {
        return ".";
    }

    public Space(int size) {
        super(MemoryType.Space, size);
    }

    public Space() {
        super(MemoryType.Space, 1);
    }

    @Override
    public int getId() {
        throw new RuntimeException("space does not have id");
    }
}
