import java.util.*;

/**
 * @author deer
 * @date 2021-11-23
 */
public class ListUsed {
    public static void main(String[] args) {
        // 创建不可变对象
        System.out.println("list:" + List.of(1, 2, 3));
        System.out.println("set:" + Set.of(1, 2, 3));
        System.out.println("map:" + Map.of("a", 1, "b", 2));
        List<Integer> integers = new ArrayList<>(List.of(4, 1, 67, 8));
        integers.sort(Integer::compareTo);
        System.out.println(integers);
    }
}
