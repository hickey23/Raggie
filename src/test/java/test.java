import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itliujiong.raggie.entity.DishDto;

import java.util.Random;

public class test {
//    public static void main(String[] args) {
//        Child child=new Child()
//    }
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


        double num=Math.random();
        if (num<=0.1){
            num=Math.random();
        }
        num=num*10000;
        String str_num=String.format("%.4f",num);
        System.out.println(str_num);
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < str_num.length(); i++) {
            char item = str_num.charAt(i);
            System.out.println(String.valueOf(item));
            if (i<4){
                System.out.println("###");
                stringBuilder.append(String.valueOf(item));
            }else {
                break;
            }

        }
        System.out.println(stringBuilder.toString());







    }
}
