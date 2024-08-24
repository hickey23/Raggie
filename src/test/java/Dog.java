public class Dog extends Animal{

    public  String name="dog";
    @Override
    public void say(){
        System.out.println("dog say...");
    }

    public void sayDog(){
        System.out.println("sayDog...");
    }


    public static int fun(int a){
        if (a==0){
            return 0;
        }else {
            return 1;
        }
    }
}
