import structure.skiplist.SkipList;

import java.util.*;
import java.util.random.RandomGenerator;

public class MainClass {
    public static void main(String[] args){
        System.out.println("module loading...");
        SkipList<Integer> skipList = new SkipList<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        RandomGenerator generator = RandomGenerator.getDefault();
//        List<Integer> nums = List.of(0,1,2,3,4,5,6,7,8,9);
        List<Integer> nums = new ArrayList<>();
        for (var i = 0; i < 10; i++) {
            int value = generator.nextInt(25); //generator.nextInt(25); // nums.get(i);
            nums.add(value);
            skipList.insert(value);
            skipList.traverse();
        }

        Set<Integer> set = new HashSet<>();
        set.addAll(nums);
        set.stream().sorted()
            .forEach((value) -> {
            int v_count = skipList.count(value);
            System.out.println(value+" count = "+v_count);
        });

        for (var i = nums.size()-1; i > -1; i--) {
            int value = nums.get(i);
            if (skipList.contains(value)) {
                skipList.delete(value);
            }
            skipList.traverse();
        }
    }
}
