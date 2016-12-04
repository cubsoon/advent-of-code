import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toList;

/**
 * http://adventofcode.com/2016/day/1
 * @author cubsoon
 */
public class Day1AoC {

    private enum Direction {
        N, E, S, W;

        private static Direction fromNumericDirection(int direction) {
            int ord = (direction + Direction.values().length) % Direction.values().length;
            return Direction.values()[ord];
        }

        public Direction turn(Turn turn) {
            int change;
            switch (turn) {
                case L:
                    change = -1;
                    break;
                case R:
                    change = 1;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
            return fromNumericDirection(ordinal() + change);
        }
    }

    private enum Turn {
        L,
        R;
    }

    private static final String INPUT = "R4, R3, L3, L2, L1, R1, L1, R2, R3, L5, L5, R4, L4, R2, R4, L3, R3, L3, R3, " +
            "R4, R2, L1, R2, L3, L2, L1, R3, R5, L1, L4, R2, L4, R3, R1, R2, L5, R2, L189, R5, L5, R52, R3, L1, R4, " +
            "R5, R1, R4, L1, L3, R2, L2, L3, R4, R3, L2, L5, R4, R5, L2, R2, L1, L3, R3, L4, R4, R5, L1, L1, R3, L5, " +
            "L2, R76, R2, R2, L1, L3, R189, L3, L4, L1, L3, R5, R4, L1, R1, L1, L1, R2, L4, R2, L5, L5, L5, R2, L4, " +
            "L5, R4, R4, R5, L5, R3, L1, L3, L1, L1, L3, L4, R5, L3, R5, R3, R3, L5, L5, R3, R4, L3, R3, R1, R3, R2, " +
            "R2, L1, R1, L3, L3, L3, L1, R2, L1, R4, R4, L1, L1, R3, R3, R4, R1, L5, L2, R2, R3, R2, L3, R4, L5, R1, " +
            "R4, R5, R4, L4, R1, L3, R1, R3, L2, L3, R1, L2, R3, L3, L1, L3, R4, L4, L5, R3, R5, R4, R1, L2, R3, R5, " +
            "L5, L4, L1, L1";

    public static void main(String[] args) {
        calculateDistance(INPUT);
    }

    private static void calculateDistance(String input) {
        List<Move> moves = Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Move::new)
                .collect(toList());

        Direction direction = Direction.N;
        Position position = Position.start();

        Optional<Position> firstVisitedTwice = Optional.empty();
        Set<Position> visited = new HashSet<>(singleton(Position.start()));

        for (Move move : moves) {
            direction = direction.turn(move.turn);

            for (Position newPosition : position.onWay(direction, move.distance)) {
                if (!firstVisitedTwice.isPresent() && visited.contains(newPosition)) {
                    firstVisitedTwice = Optional.of(newPosition);
                }
                visited.add(newPosition);
            }

            position = position.after(direction, move.distance);
        }

        System.out.printf("Distance to last position: %d\n", Position.start().distanceTo(position));
        System.out.printf("Distance to first position visited twice: %d\n",
                Position.start().distanceTo(firstVisitedTwice.orElse(Position.start())));
    }

    private static class Move {
        public final Turn turn;
        public final int distance;
        public Move(String move) {
            this.turn = Turn.valueOf(move.substring(0, 1));
            this.distance = Integer.valueOf(move.substring(1));
        }
    }

    private static class Position {
        private int ns;
        private int ew;
        private Position(int ns, int ew) {
            this.ns = ns;
            this.ew = ew;
        }
        public static Position start() {
            return new Position(0, 0);
        }
        public Position after(Direction direction, int distance) {
            switch (direction) {
                case N:
                    return new Position(ns + distance, ew);
                case S:
                    return new Position(ns - distance, ew);
                case E:
                    return new Position(ns, ew + distance);
                case W:
                    return new Position(ns, ew - distance);
                default:
                    throw new UnsupportedOperationException();
            }
        }
        public List<Position> onWay(Direction direction, int distance) {
            List<Position> onWay = new LinkedList<>();
            for (int i = 1; i <= distance; i++) {
                onWay.add(after(direction, i));
            }
            return onWay;
        }
        public int distanceTo(Position position) {
            return Math.abs(ns - position.ns) + Math.abs(ew - position.ew);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return Objects.equals(ns, position.ns) &&
                    Objects.equals(ew, position.ew);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ns, ew);
        }
    }
}