import lombok.Data;

import java.util.ArrayList;

@Data
public class Parent {
    private String name;
    private Integer age;

    public Parent() {

    }

    public Parent(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String inverseStr(String s){
        ArrayList<Character> arrayList = new ArrayList<>();
        char[] chars = s.toCharArray();
        String str="";
        for (int i=chars.length-1;i>=0;i--){
            arrayList.add(chars[i]);
        }
        System.out.println(arrayList);
        for (int i=0;i<arrayList.toArray().length;i++){
            str=str+arrayList.toArray()[i];
        }
        return str;
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        String str=parent.inverseStr("hello");
        System.out.println("翻转前的字符串是：hello");
        System.out.println("翻转后的字符串是:"+str);
    }
}
