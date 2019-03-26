package res;

public class Out {

    public static void printInfo( String classId, String msg ){
        System.out.println( "[ " + classId + " ] " + msg );
    }
    public static void printError( String classId, String msg ){
        System.err.println( "[ " + classId + " ] " + msg );
    }
}
