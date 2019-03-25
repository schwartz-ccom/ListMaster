package res;

// A simple way to measure how long certain functions or spots of code take to run
public class Benchmarker {
    private static long time = 0;

    public static void start(){
        time = System.currentTimeMillis();
    }

    public static void stop( String what ){
        System.out.println( what.trim() + " took " + ( System.currentTimeMillis() - time ) + "ms to finish" );
        time = 0;
    }
}
