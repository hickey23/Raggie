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
}
