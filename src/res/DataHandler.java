package res;

import subsys.Entry;
import subsys.EntryFactory;

import java.util.ArrayList;

public class DataHandler {

    private ArrayList< Entry > entries = new ArrayList<>();

    private String id = this.getClass().getSimpleName();

    private static DataHandler instance;
    public static DataHandler getInstance(){
        if ( instance == null )
            instance = new DataHandler();
        return instance;
    }

    public void submit( String line ){

        Entry temp = EntryFactory.createEntry( line );
        Out.printInfo( id, temp.toString() );

        entries.add( temp );
    }
}
