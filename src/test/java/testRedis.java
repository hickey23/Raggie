import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.time.Month;
import java.util.*;

public class testRedis {
    @Test
    public void test() {
        System.out.println(111);
        //1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);

        //2.执行具体操作
        jedis.set("username", "xiaoxiong");
        jedis.set("age", "23");

        String age = jedis.get("age");
        String username = jedis.get("username");
        System.out.println(username + " " + age);


        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        for (String key : keys) {
            System.out.println(key);
        }

        //3.关闭连接
        jedis.close();
    }

    @Test
    void testHash() {
        Jedis jedis = new Jedis("localhost", 6379);
        //插入hash数据
        jedis.hset("user:1", "name", "Jack");
        jedis.hset("user:1", "age", "24");

        //获取
        Map<String, String> stringMap = jedis.hgetAll("user:1");
        System.out.println("读取的hash值是：" + stringMap);

    }

    @Test
    void testUUID() {
        UUID randomUUID = UUID.randomUUID();
        System.out.println("uuid:" + randomUUID);

        String str = "hello";
        System.out.println(str.length());
//        System.out.println(checkStr);
        String[] strings = {"dog", "racecar", "car"};
        //字符串数组排序
        Arrays.sort(strings);
        System.out.println(Arrays.toString(strings));

        StringBuilder stringBuilder = new StringBuilder("liujiong");
        System.out.println("stringBuilder:" + stringBuilder.toString());

    }

    @Test
    void testBufferedInputStream() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("/Users/liujiong/IdeaProjects/Raggie/src/test/java/data.txt"));
        byte[] bytes = bufferedInputStream.readAllBytes();
        System.out.println(Arrays.toString(bytes));

        //bytes转string
        String str = new String(bytes);
        System.out.println("str:" + str);

        System.out.println("------------------------------");
        //新建文件
        File file = new File("/Users/liujiong/IdeaProjects/Raggie/src/test/java/data1.txt");
        if (file.createNewFile()) {
            System.out.println("文件创建成功！");
            try {
                //字节流
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                String str1 = "hello,liujiong";
                //字符串转bytes
                byte[] srtbyte = str1.getBytes();
                fileOutputStream.write(srtbyte);

                fileOutputStream.flush();
                //close 输入流
                fileOutputStream.close();

            } catch (IOException e) {
                System.out.println("error:" + e);
                e.printStackTrace();
            }
        } else {
            System.out.println("出错了，该文件已经存在。");
        }


        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        System.out.println("last:" + Arrays.toString(last));
        System.out.println("-----------------------------");
        String s = "hello";
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i);
            System.out.println(index);
        }
        System.out.println(Math.max(0, 900));
    }

    @Test
    void testHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // 将int类型转换为String类型:
            // 使用String的valueOf()静态方法:(key,value) HashMap中的键必须唯一
            hashMap.put(String.valueOf(i), String.valueOf((i + 2)));
            strings.add(hashMap.get(String.valueOf(i)));
        }
        System.out.println(hashMap);
        System.out.println(strings);
        // for (String str:strings){
        //     System.out.println("@@@::"+str);
        // }
        //遍历hashmap
        for (Map.Entry<String, String> hashMap1 : hashMap.entrySet()) {
            System.out.println(hashMap1);
        }
        //Java HashMap迭代键
        System.out.println("Java HashMap迭代键");
        Set<String> key = hashMap.keySet();
        for (String key1 : key) {
            System.out.println(key1);
        }

        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("liu");
        strings1.add("xiao");
        System.out.println(strings1);
    }

    @Test
    void reverseStr() {
        String str = "hello";
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        char[] array = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            array[i] = str.charAt(i);
            stringBuilder.append(array[i]);
        }
        System.out.println(stringBuilder);
        System.out.println(stringBuilder.toString().equals(str));
        // ---字符串反转---
        System.out.println("---字符串反转---");
        char[] chars = str.toCharArray();
        for (int j = str.length() - 1; j >= 0; j--) {
            // System.out.println(j);
            stringBuilder1.append(chars[j]);
        }
        System.out.println(stringBuilder1);
        Date date = new Date();
        System.out.println(Objects.equals(String.valueOf(date), date.toString()));
    }

    @Test
    void testPoint() {
        String str = "Hello";
        System.out.println(str.length());
        char[] charArray = str.toCharArray();
        int start = 0;
        int end = charArray.length - 1;
        while (start < end) {
            char temp = charArray[start];
            charArray[start] = charArray[end];
            charArray[end] = temp;
            start++;
            end--;
        }
        String reverse = String.valueOf(charArray);
        System.out.println(reverse); // olleH
    }

    @Test
    void testArr(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("1");
        stringArrayList.add("2");
        stringArrayList.add("3");
        System.out.println(stringArrayList);

        String[] arr={"1","2","3"};
        System.out.println(Arrays.toString(arr));
        System.out.println(Math.round(1.5));
        System.out.println(Math.round(1.7));
        System.out.println(Math.round(-1.5));
        System.out.println(Math.round(-1.3));
    }

    @Test
    void testGC(){
        if (true) {
            int a=1;
            byte[] placeHolder = new byte[64 * 1024 * 1024];
            System.out.println(placeHolder.length / 1024);
        }
        System.gc();
        int [] arr={2,1,5,3,4,8,6};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void test111(){

    }

}



