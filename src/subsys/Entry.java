package subsys;

import res.Benchmarker;

/**
 * Entry
 * This keeps track of all the Entries from the csv and whatnot.
 * Provides a data member for the entries, as well as getters / setters
 */
public class Entry {

//------ Private data members

    // Normal class variables
    private String id = this.getClass().getSimpleName();

    // Data members
    private boolean found = false;
    private String date = "";
    private int type = 0;
    private String name = "";
    private String steward = "";
    private String facility = "";
    private String room = "";
    private String make = "";
    private String model = "";
    private String serialNumber = "";
    private String comment = "";
    private String docasset = "";
    private String unhid = "";
    private String purchaseDate = "";
    private String warrantyExpireDate = "";
    private String OSLicenseSticker = "";

//------ Constructors

    /**
     * Default Constructor 1
     * Defaults everything to blank.
     */
    public Entry() {

    }

    /**
     * Default Constructor 2
     * Allows for instantiation of Date, Type, Name. Steward, Facility, Room, SN, Make, Model
     *
     * @param d  Date as String
     * @param t  Type as int. Type found in Constants.java
     * @param n  Name as String
     * @param s  Steward as String
     * @param f  Facility as String
     * @param r  Room as String
     * @param sn SerialNumber as String
     * @param m  Make as String
     * @param mo Model as String
     */
    public Entry( String d, int t, String n, String s, String f, String r, String sn, String m, String mo ) {
        this.date = d;
        this.type = t;
        this.name = n;
        this.steward = s;
        this.facility = f;
        this.room = r;
        this.serialNumber = sn;
        this.make = m;
        this.model = mo;
    }

//------ Functions

    // Getters / setters

    /**
     * setFound
     * When doing inventory, it's nice to know if things are checked off or not.
     *
     * @param found The status of found for the entry
     */
    public void setFound( boolean found ) {
        this.found = found;
    }

    /**
     * setComment
     * Comments are usually long, so this is on its own to prevent clutter when instantiating a class.
     *
     * @param c The comment to add
     */
    public void setComment( String c ) {
        this.comment = c;
    }

    /**
     * setIDs
     * Set the UNH ID and Doc Asset of the Entry. Not typical of entries, so it's not a constructor.
     *
     * @param UNHID    The UNH ID
     * @param docAsset The Doc Asset Tag associated with it
     */
    public void setIDs( String docAsset, String UNHID ) {
        this.docasset = docAsset;
        this.unhid = UNHID;
    }

    /**
     * setDates
     * Sets important dates regarding the Entry.
     *
     * @param purchDate      The Purchase date of the Entry
     * @param warrantyExpire The Warranty Expiration date of the Entry
     */
    public void setDates( String purchDate, String warrantyExpire ) {
        this.purchaseDate = purchDate;
        this.warrantyExpireDate = warrantyExpire;
    }

    /**
     * setOperatingSystem
     * Set what kind of OS the device shipped with
     *
     * @param os The operating system
     */
    public void setOperatingSystem( String os ) {
        this.OSLicenseSticker = os;
    }

    /**
     * getType
     * Returns the raw type of the entry
     *
     * @return type as int
     */
    public int getType() {
        return this.type;
    }

    /**
     * getBasicDetails
     * Get just the basic details for the JTable
     */
    public String[] getBasicDetails() {
        String[] basic = new String[ 7 ];
        basic[ 0 ] = typeToString( this.type );
        basic[ 1 ] = this.name;
        basic[ 2 ] = this.make;
        basic[ 3 ] = this.model;
        basic[ 4 ] = this.steward;
        basic[ 5 ] = this.room;
        basic[ 6 ] = this.serialNumber;
        return basic;
    }

    /**
     * getAllDetails
     * Get's all the details of the entry and returns it as a string array.
     * element[ 0 ] = Found
     * element[ 1 ] = Date
     * element[ 2 ] = Type
     * element[ 3 ] = Name
     * element[ 4 ] = Steward
     * element[ 5 ] = Facility
     * element[ 6 ] = Room
     * element[ 7 ] = Make
     * element[ 8 ] = Model
     * element[ 9 ] = SerialNumber
     * element[ 11 ] = Comment
     * element[ 12 ] = DocAsset
     * element[ 13 ] = UNH ID
     * element[ 14 ] = Purchase Date
     * element[ 15 ] = Warranty Expiration Date
     * element[ 16 ] = OS License Sticker
     *
     * @return String[] with all these details
     */
    public String[] getAllDetails() {

        Benchmarker.start();

        String[] toRet = new String[ 16 ];
        toRet[ 0 ] = String.valueOf( this.found );
        toRet[ 1 ] = String.valueOf( this.date );
        toRet[ 2 ] = String.valueOf( this.type );
        toRet[ 3 ] = String.valueOf( this.name );
        toRet[ 4 ] = String.valueOf( this.steward );
        toRet[ 5 ] = String.valueOf( this.facility );
        toRet[ 6 ] = String.valueOf( this.room );
        toRet[ 7 ] = String.valueOf( this.make );
        toRet[ 8 ] = String.valueOf( this.model );
        toRet[ 9 ] = String.valueOf( this.serialNumber );
        toRet[ 10 ] = String.valueOf( this.comment );
        toRet[ 12 ] = String.valueOf( this.docasset );
        toRet[ 13 ] = String.valueOf( this.unhid );
        toRet[ 14 ] = String.valueOf( this.purchaseDate );
        toRet[ 15 ] = String.valueOf( this.warrantyExpireDate );
        toRet[ 16 ] = String.valueOf( this.OSLicenseSticker );

        Benchmarker.stop( "Return all Entry values" );

        return toRet;
    }

    private String typeToString( int t ) {
        switch ( t ) {
            case 1:
                return "Camera";
            case 2:
                return "Desktop";
            case 3:
                return "Display";
            case 4:
                return "Laptop";
            case 5:
                return "Network";
            case 6:
                return "Phone";
            case 7:
                return "Printer";
            case 8:
                return "Projector";
            case 9:
                return "Scanner";
            case 10:
                return "Server";
            case 11:
                return "Storage";
            case 12:
                return "Tablet";
            case 13:
                return "Other";
            default:
                return "UNKNOWN TYPE";
        }
    }

    public String getSN() {
        return this.serialNumber;
    }

    @Override
    public boolean equals( Object o ) {
        // If o is an Entry, get it's S/N and compare it to this one's SN

        if ( o instanceof Entry )
            return ( ( Entry ) o ).getSN().equals( this.getSN() );
        return false;
    }

    public String toString() {

        String nline = "\n";

        StringBuilder sb = new StringBuilder();
        sb.append( "-----------------------------" );
        sb.append( nline );

        sb.append( "Entry - " );
        sb.append( this.name );
        sb.append( nline );

        sb.append( "Found - " );
        sb.append( this.found );
        sb.append( nline );

        sb.append( "Date Inventoried - " );
        sb.append( this.date );
        sb.append( nline );

        sb.append( "Type - " );
        sb.append( typeToString( this.type ) );
        sb.append( nline );

        sb.append( "Device Name - " );
        sb.append( this.name );
        sb.append( nline );

        sb.append( "Steward - " );
        sb.append( this.steward );
        sb.append( nline );

        sb.append( "Facility - " );
        sb.append( this.facility );
        sb.append( nline );

        sb.append( "Room - " );
        sb.append( this.room );
        sb.append( nline );

        sb.append( "Make - " );
        sb.append( this.make );
        sb.append( nline );

        sb.append( "Model - " );
        sb.append( this.model );
        sb.append( nline );

        sb.append( "Serial Number - " );
        sb.append( this.serialNumber );
        sb.append( nline );

        sb.append( "Comment - " );
        sb.append( this.comment );
        sb.append( nline );

        sb.append( "Doc Asset Tag - " );
        sb.append( this.docasset );
        sb.append( nline );

        sb.append( "UNH ID Number - " );
        sb.append( this.unhid );
        sb.append( nline );

        sb.append( "Purchase Date - " );
        sb.append( this.purchaseDate );
        sb.append( nline );

        sb.append( "Warranty Expiration Date - " );
        sb.append( this.warrantyExpireDate );
        sb.append( nline );

        sb.append( "Operating System License Sticker - " );
        sb.append( this.OSLicenseSticker );
        sb.append( nline );

        return sb.toString();
    }
}
