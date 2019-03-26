package res;

import subsys.Entry;
import subsys.EntryFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * DataHandler
 * Handles all the data sent to it by the FileHandler
 * It's responsible for manipulating the data in the table,
 * as well as providing sorting / searching functionality
 */
public class DataHandler {

    private ArrayList< Entry > entries = new ArrayList<>();

    private JTable rTable;
    private DefaultTableModel tblModel;

    private int rejectCount = 0;
    private String id = this.getClass().getSimpleName();

    private static DataHandler instance;

    public static DataHandler getInstance() {
        if ( instance == null )
            instance = new DataHandler();
        return instance;
    }
    public void reset(){
        entries.clear();
        removeAllRows();
    }

    public void submit( String line ) {
        if ( line.isEmpty() )
            return;
        Entry temp = EntryFactory.createEntry( line );
        //Out.printInfo( id, temp.toString() );

        if ( temp.getType() != 0 && !entries.contains( temp ) )
            entries.add( temp );
        else
            rejectCount++;

    }
    public void refreshTable() {
        Out.printInfo( id, "Rejected: " + rejectCount );
        displayElements( getEntries() );
    }

    public void refreshTable( String[] filters ) {
        // If there are any sorting filters, use this.
        Benchmarker.start();
        ArrayList< Entry > filtered = new ArrayList<>( getEntries() );
        // Filters 0 = Category
        // Filters 1 = Name
        // Filters 2 = Steward
        // Filters 3 = S/N

        // Go through each filter and remove all unnecessary things
        Iterator< Entry > it = filtered.iterator();
        while ( it.hasNext() ){
            if ( Integer.valueOf( filters[ 0 ] ) != 0 ){
                if ( it.next().getType() != Integer.valueOf( filters[ 0 ] ) )
                    it.remove();
            }
            else
                it.next();
        }
        if ( !filters[ 1 ].isEmpty() ) {
            it = filtered.iterator();
            while ( it.hasNext() ) {
                if ( !it.next().getBasicDetails()[ 1 ].equals( filters[ 1 ] ) )
                    it.remove();
            }
        }

        if ( !filters[ 2 ].isEmpty() ) {
            it = filtered.iterator();
            while ( it.hasNext() ) {
                if ( !it.next().getBasicDetails()[ 4 ].equals( filters[ 2 ] ) )
                    it.remove();
            }
        }

        if ( !filters[ 3 ].isEmpty() ) {
            it = filtered.iterator();
            while ( it.hasNext() ) {
                if ( !it.next().getBasicDetails()[ 6 ].equals( filters[ 3 ] ) )
                    it.remove();
            }
        }
        Benchmarker.stop( "Filtering results" );
        displayElements( filtered );
    }

    private void displayElements( ArrayList< Entry > toList ) {
        Benchmarker.start();

        // Remove all rows first
        removeAllRows();

        // Now add the rows with the meaningful data according to search params
        Out.printInfo( id, "Adding " + toList.size() + " entries..." );
        for ( int entryCounter = 0; entryCounter < toList.size(); entryCounter++ ) {
            tblModel.addRow( toList.get( entryCounter ).getBasicDetails() );
        }
        Benchmarker.stop( "Refreshing table" );
    }

    public void setTable( JTable refTable ) {
        this.rTable = refTable;
        this.tblModel = ( DefaultTableModel ) rTable.getModel();
    }

    private void removeAllRows() {
        // Remove all rows
        Out.printInfo( id, "Removing " + tblModel.getRowCount() + " rows..." );
        int countToRemove = tblModel.getRowCount();
        for ( int rowCounter = 0; rowCounter < countToRemove; rowCounter++ ) {
            tblModel.removeRow( 0 );
        }
        rejectCount = 0;
    }

    public ArrayList< Entry > getEntries() {
        return entries;
    }
}
