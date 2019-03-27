package res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * FileHandler
 * Handles everything related the file opening or saving.
 * Works with the Interface when the OpenFile dialog appears, and
 * when a refresh is called.
 */
public class FileHandler {
    
    private File file;
    private String id = this.getClass().getSimpleName();
    
    private static FileHandler instance;
    
    public static FileHandler getInstance() {
        if ( instance == null )
            instance = new FileHandler();
        return instance;
    }
    
    private FileHandler() {
    
    }
    
    public File getWorkingFile() {
        return file;
    }
    
    public void setWorkingFile( File toWorkWith ) {
        if ( file != null ) {
            Out.printError( id, "Warning: Resetting current working file to something else!" );
        }
        file = toWorkWith;
    }
    
    public void processFile() {
        FileReader f;
        StringBuilder sb;
        
        // Try to see if we can initialize the file in the first place before doing anything else
        // This prevents issues like the user deleting the file after they select it.
        try {
            f = new FileReader( getWorkingFile() );
        } catch ( FileNotFoundException fnfe ) {
            Out.printError( id, "There was an issue getting the file: " + fnfe.getMessage() );
            return;
        }
        
        // Ignore first line, which contains column headers for now. I'll expand it to include
        // col headers soon.
        boolean isFirst = true;
        
        // Read through it, and on every new line, submit the line to the EntryFactory.
        try {
            sb = new StringBuilder();
            while ( f.ready() ) {
                char c = ( char ) f.read();
                if ( c == '\n' ) {
                    if ( !isFirst )
                        DataHandler.getInstance().submit( sb.toString() );
                    else
                        isFirst = false;
                    sb = new StringBuilder();
                } else
                    sb.append( c );
            }
            DataHandler.getInstance().refreshTable();
        } catch ( IOException ioe ) {
            Out.printError( id, "There was an error reading the file: " + ioe.getMessage() );
        }
    }
}
