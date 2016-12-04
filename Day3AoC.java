import com.google.common.io.Resources;

import java.io.IOException;
import java.util.*;

import static java.nio.charset.Charset.forName;
import static java.util.stream.Collectors.toList;

/**
 * http://adventofcode.com/2016/day/3
 * @author cubsoon
 */
public class Day3AoC {

    private static String INPUT;

    public static void main(String[] args) throws IOException {

        INPUT = Resources.toString(Aoc.class.getResource("/input.txt"), forName("UTF-8"));

        List<List<Integer>> triangleList = Arrays.stream(INPUT.split("\n"))
                .map(String::trim)
                .map(row -> {
                    return Arrays.stream(row.split("\\s+"))
                            .map(String::trim)
                            .map(Integer::valueOf)
                            .sorted()
                            .collect(toList());
                })
                .collect(toList());

        printValidCount(triangleList);

        List<Integer> integers = Arrays.stream(INPUT.split("\n"))
                .map(String::trim)
                .flatMap(row -> Arrays.stream(row.split("\\s+")).map(String::trim))
                .map(Integer::valueOf)
                .collect(toList());

        Integer[] order = {0, 3, 6, 1, 4, 7, 2, 5, 8};

        List<Integer> reshuffled = new LinkedList<>();
        for (int i = 0; i < integers.size(); i += order.length) {
            for (Integer shift : order) {
                reshuffled.add(integers.get(i + shift));
            }
        }

        Deque<List<Integer>> triangles = new LinkedList<>();
        triangles.add(new LinkedList<>());
        for (Integer integer : reshuffled) {
            List<Integer> triangle = triangles.getLast();
            if (triangle.size() < 3) {
                triangle.add(integer);
            } else {
                Collections.sort(triangle);
                triangle = new LinkedList<>();
                triangle.add(integer);
                triangles.add(triangle);
            }
        }

        printValidCount(triangles);
    }

    private static void printValidCount(Collection<List<Integer>> triangleList) {
        long correct = triangleList.stream()
                .filter(triangle -> (triangle.get(2) < triangle.get(1) + triangle.get(0)))
                .count();

        System.out.printf("correct: %d\n", correct);
    }
}