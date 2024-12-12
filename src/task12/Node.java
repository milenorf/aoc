package task12;

import utils.Point;

import java.util.List;

public record Node(Point location, char value, List<Node> vertices) {
}
