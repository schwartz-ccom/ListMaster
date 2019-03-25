import res.Constants;
import ui.Interface;

public class Initiator {

    // This starts the entire program
    private Initiator(){
        System.out.println( "Launching ListMaster v" + Constants.VERSION );
        new Interface();
    }

    public static void main( String [] args ){
        new Initiator();
    }
}
