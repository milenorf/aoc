package task9;

public abstract class MemoryLocation {
    private final MemoryType memoryType;
    private final int size;

    public MemoryLocation(MemoryType memoryType, int size){
        this.memoryType = memoryType;
        this.size = size;
    }

    public MemoryType getMemoryType() {
        return memoryType;
    }

    public abstract int getId();

    public int getSize() {
        return size;
    }
}
