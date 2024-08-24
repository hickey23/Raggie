/**
 * @Author GJY
 * @Date 2021/6/27 14:27
 * @Version 1.
 * synchronized修饰实例方法，当线程拿到锁，其他线程无法拿到该对象的锁，那么其他线程就无法访问该对象的其他同步方法
 * 但是可以访问该对象的其他非synchronized方法
 * 锁住的是类的实例对象
 */


//synchronized是Java中的一个关键字，在多线程共同操作共享资源的情况下，可以保证在同一时刻只有一个线程可以对共享资源进行操作，从而实现共享资源的线程安全。
public class SynchronizedDemo1 implements Runnable  {
    //模拟一个共享数据
    private static int total=0;

    //同步方法,每个线程获取到锁之后，执行5次累加操作
    public synchronized void increase(){
        for (int i = 1; i < 6; i++) {
            System.out.println(Thread.currentThread().getName()+"执行累加操作..."+"第"+i+"次累加");
            try {
                total=total+1;
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //实例对象的另一个同步方法
    public synchronized void declare(){
        System.out.println(Thread.currentThread().getName()+"执行total-1");
        total--;
        System.out.println(Thread.currentThread().getName()+"执行total-1完成");
    }

    //普通实例方法
    public void simpleMethod(){
        System.out.println(Thread.currentThread().getName()+ "  ----实例对象的普通方法---");
    }


    @Override
    public void run() {
        //线程执行体
        System.out.println(Thread.currentThread().getName()+"准备执行累加,还没获取到锁");
        //执行普通方法
        simpleMethod();
        //调用同步方法执行累加操作
        increase();
        //执行完increase同步方法后，会释放掉锁，然后线程1和线程2会再一次进行锁的竞争，谁先竞争得到锁，谁就先执行declare同步方法
        System.out.println(Thread.currentThread().getName()+"完成累加操作");
        //调用实例对象的另一个同步方法
        System.out.println(Thread.currentThread().getName()+"准备执行total-1");
        declare();
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDemo1 syn = new SynchronizedDemo1();
        Thread thread1 = new Thread(syn,"线程1");
        Thread thread2 = new Thread(syn,"线程2");
        thread1.start();
        thread2.start();

        String s="hello";
        //不转化为字符数组，就不能遍历s
        System.out.println(s.toCharArray());


    }
}

