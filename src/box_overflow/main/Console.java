package box_overflow.main;

public class Console {
    public Console(){
    }

    // Main functions
    public void print(String s){
        System.out.print(s);
    }
    public void println(String s){
        System.out.println(s);
    }

    // Surcharge method
    public void print(int a){
        print(""+a);
    }
    public void println(int a){
        println(""+a);
    }

    public void print(double a){
        print(""+a);
    }
    public void println(double a){
        println(""+a);
    }

    public void print(boolean a){
        print(""+a);
    }
    public void println(boolean a){
        println(""+a);
    }

    public void print(byte a){
        print(""+a);
    }
    public void println(byte a){
        println(""+a);
    }

    public void print(float a){
        print(""+a);
    }
    public void println(float a){
        println(""+a);
    }

    public void print(long a){
        print(""+a);
    }
    public void println(long a){
        println(""+a);
    }
}
