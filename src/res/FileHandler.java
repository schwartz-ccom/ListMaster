package res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public void processNewFile() {
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

        boolean isFirst = true;
        // Read through it, and on every new line, submit the line to the EntryFactory.
        try {
            sb = new StringBuilder();
            while ( f.ready() ) {
                char c = ( char ) f.read();
                if ( c == '\n' ) {
                    Out.printInfo( id, "NEW LINE DETECTED" );
                    if ( !isFirst )
                        DataHandler.getInstance().submit( sb.toString() );
                    else
                        isFirst = false;
                    sb = new StringBuilder();
                }
                else
                    sb.append( c );
            }
        } catch ( IOException ioe ) {
            Out.printError( id, "There was an error reading the file: " + ioe.getMessage() );
        }
    }
}