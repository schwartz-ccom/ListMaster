package res;

import subsys.Entry;
import subsys.EntryFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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
        ArrayList< Entry > filteredInverted = new ArrayList<>();
        // Filters 0 = Category
        // Filters 1 = Name
        // Filters 2 = Steward
        // Filters 3 = S/N

        for ( Entry test : getEntries() ) {
            if ( filters[ 0 ].equals( "0" ) || test.getType() == Integer.valueOf( filters[ 0 ] ) )
                filteredInverted.add( test );
            else if ( !filters[ 1 ].equals( "" ) && test.getBasicDetails()[ 1 ].equals( filters[ 1 ] ) )
                filteredInverted.add( test );
            else if ( !filters[ 2 ].equals( "" ) && test.getBasicDetails()[ 4 ].equals( filters[ 2 ] ) )
                filteredInverted.add( test );
            else if ( !filters[ 3 ].equals( "" ) && test.getBasicDetails()[ 6 ].equals( filters[ 3 ] ) )
                filteredInverted.add( test );
        }

        ArrayList< Entry > sorted = new ArrayList<>( getEntries() );
        sorted.removeAll( filteredInverted );

        Benchmarker.stop( "Filtering with " + filters.length + " filters" );
        displayElements( sorted );
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
