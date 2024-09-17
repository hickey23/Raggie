import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestExample {
    @Test
    void say(){
        Parent parent1 = new Parent("liu",23);
        Parent parent2 = new Parent("xiao",22);
        Parent parent3 = new Parent("xiaoxiao",8);
        ArrayList<Parent> list = new ArrayList<>();
        list.add(parent1);
        list.add(parent2);
        list.add(parent3);

        System.out.println(list);
        List<String> collect = list.stream().map(Parent::getName).collect(Collectors.toList());
        System.out.println("collect:"+collect);

        list.forEach(l-> {
            if (l.getName().equals("liu")) {
                System.out.println(l);
            }
        });
    }

    @Test
    public void BubbleSort(){
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            arr.add(i);
        }
        System.out.println(arr);
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j) > arr.get(j + 1)) {
                    // 交换 arr.get(j) 和 arr.get(j + 1)
                    int temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
        System.out.println(arr);
    }
}
