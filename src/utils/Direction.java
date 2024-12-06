package utils;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction rotate90(Direction direction) {
        return switch (direction) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
            default -> throw new RuntimeException();
        };
    }
}
