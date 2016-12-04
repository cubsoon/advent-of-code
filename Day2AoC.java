import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * http://adventofcode.com/2016/day/2
 * @author cubsoon
 */
public class Day2AoC {

    private static final String INPUT = "DLUUULUDLRDDLLLUDULLULLRUURURLUULDUUUDLDDRUDLUULLRLDDURURDDRDRDLDURRURDLDUURULDDULDRDDLDLDLRDRUURLDLUDDDURULRLLLLRLULLUDRDLDUURDURULULULRLULLLULURLRDRDDDDDDDLRLULUULLULURLLDLRLUDULLDLLURUDDLDULDLULDDRLRLRDDLRURLLLURRLDURRDLLUUUUDRURUULRLDRRULLRUDLDRLUDRDRDRRDDURURRDRDRUDURDLUDRUDLRRULDLRDDRURDDUUDLDRDULDDRRURLLULRDRURLRLDLLLUULUUDLUDLDRRRRDUURULDUDUDRLDLLULLLRDDDDDLRDDLLUULLRRRDURLRURDURURLUDRRLRURDRDRRRRULUDLDRDULULRUDULLLUDRRLRLURDDURULDUUDULLURUULRDRDULRUUUDURURDDRRUDURRLRDRULRUUU\n" +
            "LDRURRUUUULDRDDDLLULDRUDDRLLDLDRDLRUDDDLDDULULULLRULDUDRRDLRUURURDRURURDLLRUURDUUDRLDURDRDLRRURURDUUUURUURRLLLDRDUURRRRURULUUUDLUDDRUURRLDULRDULRRRRUDURRLURULRURRDRDLLDRRDUDRDURLDDRURULDRURUDDURDLLLUURRLDRULLURDRDRLDRRURRLRRRDDDDLUDLUDLLDURDURRDUDDLUDLRULRRRDRDDLUDRDURDRDDUURDULRRULDLDLLUDRDDUDUULUDURDRLDURLRRDLDDLURUDRLDUURLLRLUDLLRLDDUDLLLRRRLDLUULLUDRUUDRLDUUUDUURLRDDDDRRDRLDDRDLUDRULDDDRDUULLUUUUULDULRLLLRLLDULRDUDDRDDLRRLRDDULLDURRRURDDUDUDDRLURRLUUUULLDRDULUUDRDULDLLUDLURDLLURRDLUULURRULRLURRRRRUURDDURLRLLDDLRRDUUURDRDUDRDDDLLDDRDRRRLURRDUULULULULRRURDDLDDLLLRUDDDDDDLLLRDULURULLRLRDRR\n" +
            "DDRLLLDLRRURRDLDDRUURRURRLRRRRUURUURDLURRRDDLRUDRURLUURLLRRLRLURLURURDULLLLDLRURULUUDURRLULRDRDRRDDLLULRLUDLUUUDRLLRRURRLDULDDLRRLUUUUDDLRLDRLRRDRDLDDURDDRDDLDLURLRRRDDUDLLRLRLURRRRULLULLLLDRLDULDLLDULRLDRDLDDRRDDDDRUDRLLURULRLDDLLRRURURDDRLLLULLULDDRDLDDDLRLLDRLDRUURRULURDDRLULLDUURRULURUUDULLRUDDRRLLDLLRDRUDDDDLLLDDDLLUUUULLDUUURULRUUDUUUDDLDURLDRDRRLLUDULDLUDRLLLDRRRULUUDDURUDRLUDDRRLLDUDUURDDRURLUURDURURURRUUDUDDLLLDRRRURURRURDLRULLDUDRLRLLRUDRUDLR\n" +
            "RRRDRLRURLRRLUURDRLDUURURLRDRRUDLLUUDURULLUURDLLDRRLURRUDUUDRRURLRRDULLDDLRRRUDUUDUUDLDDDLUUDLDULDDULLDUUUUDDUUDUDULLDDURRDLRRUDUDLRDUULDULRURRRLDLLURUDLDDDRRLRDURDLRRLLLRUDLUDRLLLRLLRRURUDLUDURLDRLRUDLRUULDRULLRLDRDRRLDDDURRRUDDDUDRRDRLDDRDRLLRLLRDLRDUDURURRLLULRDRLRDDRUULRDDRLULDLULURDLRUDRRDDDLDULULRDDRUDRLRDDRLDRDDRRRDUURDRLLDDUULRLLLULLDRDUDRRLUUURLDULUUURULLRLUDLDDLRRDLLRDDLRDRUUDURDDLLLDUUULUUDLULDUDULDRLRUDDURLDDRRRDLURRLLRRRUDDLDDRURDUULRUURDRRURURRRUUDUDULUDLUDLLLUUUULRLLRRRRDUDRRDRUDURLUDDLDRDLDDRULLRRULDURUL\n" +
            "DLLLRDDURDULRRLULURRDULDLUDLURDDURRLLRRLLULRDLDRDULRLLRDRUUULURRRLLRLDDDRDRRULDRRLLLLDLUULRRRURDDRULLULDDDLULRLRRRUDRURULUDDRULDUDRLDRRLURULRUULLLRUURDURLLULUURUULUUDLUDLRRULLLRRLRURDRRURDRULRURRUDUDDDRDDULDLURUDRDURLDLDLUDURLLRUULLURLDDDURDULRLUUUDLLRRLLUURRDUUDUUDUURURDRRRRRRRRRUDULDLULURUDUURDDULDUDDRDDRDRLRUUUUDLDLRDUURRLRUUDDDDURLRRULURDUUDLUUDUUURUUDRURDRDDDDULRLLRURLRLRDDLRUULLULULRRURURDDUULRDRRDRDLRDRRLDUDDULLDRUDDRRRD";

    private enum Direction {
        U, D, L, R;

        static int SIZE_X = 5;
        static int SIZE_Y = 5;

        public static final Integer[][] KEYPAD =
        {
                {null, null, 1, null, null},
                {null, 2, 3, 4, null},
                {5, 6, 7, 8, 9},
                {null, 10, 11, 12, null},
                {null, null, 13, null, null},
        };

        private static boolean valueAt(int x, int y) {
            if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
                return false;
            }
            if (KEYPAD[y][x] == null) {
                return false;
            }
            return true;
        }

        public Position move(Position position) {
            int newx = position.x;
            int newy = position.y;

            switch (this) {
                case U:
                    newy -= 1;
                    break;
                case D:
                    newy += 1;
                    break;
                case L:
                    newx -= 1;
                    break;
                case R:
                    newx += 1;
                    break;
            }
            if (valueAt(newx, newy)) {
                return new Position(newx, newy);
            } else {
                return position;
            }
        }
    }

    public static class Position {
        final int x;
        final int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        List<Input> inputs = Arrays.stream(INPUT.split("\n"))
                .map(String::trim)
                .map(Input::new)
                .collect(toList());

        Position pos = new Position(2, 2);
        for (Input input : inputs) {
            for (Direction direction : input.directions) {
                pos = direction.move(pos);
            }
            System.out.printf("%d %d: %d\n", pos.y, pos.x, Direction.KEYPAD[pos.y][pos.x]);
        }
        System.out.print("\n");

    }

    private static class Input {

        private final List<Direction> directions;

        public Input(String token) {
            directions = new LinkedList<>();
            for (char c : token.toCharArray()) {
                directions.add(Direction.valueOf(String.valueOf(c)));
            }
        }
    }
}