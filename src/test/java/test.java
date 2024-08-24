import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.entity.DishDto;
import net.sf.jsqlparser.expression.CollateExpression;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class test {
    public static void main(String[] args) {

        DishDto dishDto=new DishDto();
        System.out.println(dishDto.toString());

        System.out.println("----------------------------------");
        //=左边是编译类型，右边是运行类型
        //编译类似是animal，运行类型是dog
        Animal animal = new Dog();

        animal.say();
        System.out.println(animal.name);


        System.out.println("----------------------------------");
        Dog dog1=(Dog) animal;
        dog1.sayDog();
        System.out.println(dog1.name);


        //单列集合获取stream流
        //原来的stream流只能使用一次
        ArrayList<String> list=new ArrayList<>();
        Collections.addAll(list,"a","b","c");
        System.out.println(list);
        list.stream().forEach(s -> System.out.println(s));

        System.out.println("-----------------------------");
        //数组获取stream流
        int[] arr={1,2,3,4,5,3434,25,8};
        Arrays.stream(arr).forEach(i->{
            i=i+1;
            System.out.println(i);
        });

        System.out.println("----------------------");
        //限制读取几个元素
        list.stream().limit(2).forEach(System.out::println);

        //去重
        ArrayList<String> list1=new ArrayList<>();
        Collections.addAll(list1,"s","s","s","ss","sss");
        list1.stream().distinct().forEach(s -> System.out.println(s));

        String str="Welcome-to-Runoob";
        String[] split = str.split("-");
        System.out.println(Arrays.toString(split));

        ArrayList<String> strings=new ArrayList<>();
        Collections.addAll(strings,"zs-15","liu-22","xiao-18");
        strings.stream().forEach(s -> System.out.println(Arrays.toString(s.split("-"))));
        strings.stream().map(s -> Integer.parseInt(s.split("-")[1])).forEach(s-> System.out.println(s));



        System.out.println("----------------------");

        //stream中的终结方法
//        Long l = strings.stream().count();
        long count = strings.stream().count();
        System.out.println(count);


        //收集流中的数据，放到数组中
        Object[] objects = strings.stream().toArray();
        System.out.println(Arrays.toString(objects));
        System.out.println("---------------------------");


        ArrayList<Integer> list2=new ArrayList<>();
        Collections.addAll(list2,1,2,3,4,5,6,7,8,9,10);
        System.out.println(list2);

        List<Integer> collect = list2.stream().filter(s -> s % 2 == 0).collect(Collectors.toList());
        System.out.println(collect);

        ArrayList<Integer> list3=new ArrayList<>();
        for (int i=0;i<list2.size();i++){
            if (list2.get(i) %2==0){
                list3.add(list2.get(i));
            }
        }
        System.out.println(list3);

        System.out.println("---====================hashmap是个简直对===========================");
        HashMap<Character, Character> mappings = new HashMap<Character, Character>();
        mappings.put(')', '(');
        mappings.put('}', '{');
        mappings.put(']', '[');
        System.out.println("mappings:"+mappings);
        System.out.println(mappings.get('}'));


        StringBuilder stringBuilder = new StringBuilder();
        String s = new String();
        System.out.println("stringBuilder:"+stringBuilder);

    }
    @Test
    public void testFunc(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(i);
        }
        System.out.println(stringBuilder);
    }

}
