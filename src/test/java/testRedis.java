import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class testRedis {
    @Test
    public void test(){
        System.out.println(111);
        //1.获取连接
        Jedis jedis=new Jedis("localhost",6379);

        //2.执行具体操作
        jedis.set("username","xiaoxiong");
        jedis.set("age","23");

        String age=jedis.get("age");
        String username=jedis.get("username");
        System.out.println(username+" "+age);


        Set<String> keys=jedis.keys("*");
        System.out.println(keys);

        for (String key:keys){
            System.out.println(key);
        }

        //3.关闭连接
        jedis.close();
    }
}
