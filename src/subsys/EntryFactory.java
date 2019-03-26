package subsys;

import res.Constants;

public class EntryFactory {

    public static Entry createEntry( String line ) {

        // Use a negative value on split so it doesn't remove empty strings.
        String[] parts = line.split( ",", -1 );
        for ( int x = 0; x < parts.length; x++ ){
            if ( parts[ x ] == null || parts[ x ].isEmpty() ){
                parts[ x ] = "";
            }
        }

        int type = 0;

        switch ( parts[ 1 ] ){
            case "Camera": type = Constants.TYPE_CAMERA;
                break;
            case "Desktop": type = Constants.TYPE_DESKTOP;
                break;
            case "Display": type = Constants.TYPE_DISPLAY;
                break;
            case "Laptop": type = Constants.TYPE_LAPTOP;
                break;
            case "Network": type = Constants.TYPE_NETWORK;
                break;
            case "Phone": type = Constants.TYPE_PHONE;
                break;
            case "Printer": type = Constants.TYPE_PRINTER;
                break;
            case "Projector": type = Constants.TYPE_PROJECTOR;
                break;
            case "Scanner": type = Constants.TYPE_SCANNER;
                break;
            case "Server": type = Constants.TYPE_SERVER;
                break;
            case "Storage": type = Constants.TYPE_STORAGE;
                break;
            case "Tablet": type = Constants.TYPE_TABLET;
                break;
            case "Other": type = Constants.TYPE_OTHER;
                break;
        }

        Entry toRet = new Entry( parts[ 0 ],
                type,
                parts[ 2 ],
                parts[ 3 ],
                parts[ 4 ],
                parts[ 5 ],
                parts[ 8 ],
                parts[ 6 ],
                parts[ 7 ] );

        toRet.setComment( parts[ 9 ] );
        toRet.setFound( false );
        toRet.setIDs( parts[ 10 ], parts[ 11 ] );
        toRet.setDates( parts[ 12 ], parts[ 13 ] );
        toRet.setOperatingSystem( parts[ 14 ] );
        return toRet;
    }

}
