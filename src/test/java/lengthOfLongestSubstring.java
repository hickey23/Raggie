import java.util.HashSet;
import java.util.Set;

public class lengthOfLongestSubstring {
    private static Object resource1 = new Object();//资源 1
    private static Object resource2 = new Object();//资源 2

    public int getlengthOfLongestSubstring(String s) {
        //维护当前最长不重复字符子串
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        int max = 0;
        while(right<s.length()){
            if(!set.contains(s.charAt(right))){
                //未查到重复字符就一直加，right右移
                set.add(s.charAt(right));
                right++;
            }else{
                //right查到重复字符先不动，left右移，set删left经过的字符，直到重复的这个字符删掉为止
                set.remove(s.charAt(left));
                left++;
            }
            //每一次计算当前set子串的长度
            max = Math.max(max, set.size());
        }
        return max;
    }

    public static void main(String[] args) {
        lengthOfLongestSubstring lengthOfLongestSubstring = new lengthOfLongestSubstring();
        int len=lengthOfLongestSubstring.getlengthOfLongestSubstring("hello");
//        System.out.println("hello".charAt(0));
        System.out.println(len);

        System.out.println("--------------------------");
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程 2").start();
    }


}
