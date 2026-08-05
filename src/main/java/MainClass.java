import structure.skiplist.SkipList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        for (var i = nums.size()-1; i > -1; i--) {
            int value = nums.get(i);
            skipList.delete(value);
            skipList.traverse();
        }
    }
}
