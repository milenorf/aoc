package task9;

public class File extends MemoryLocation {
    private int id;

    public File(int id, int size) {
        super(MemoryType.File, size);
        this.id = id;
    }

    public File(int id) {
        super(MemoryType.Space, 1);
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "";
    }

    public int getId() {
        return id;
    }
}
