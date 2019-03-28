package ui;

import res.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * Interface
 * The main class responsible for the user interface
 */
public class Interface {

    // Class Variables
    private String id = this.getClass().getSimpleName();
    private String lastLocationForFile = System.getProperty( "user.home" );

    private static int formVersion = 0;

    // Constructors
    public Interface() {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );

            // Because ProgressBar is broken on linux, which is what we're targeting,
            // override the settings for it
        } catch ( Exception e ) {
            Out.printError( id, "There was an issue setting look and feel: " + e.getLocalizedMessage() );
        }
        startGUI();
    }

    // Functions
    private void startGUI() {

        Benchmarker.start();

        // Create the spacing insets so nothing is too crowded
        int space = 4;
        Border b = BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder( 4, 4, 4, 4 ),
                BorderFactory.createEtchedBorder() );
        BorderLayout c = new BorderLayout();
        c.setHgap( space );
        c.setVgap( space );

        // Gotta have a frame!
        JFrame frm = new JFrame( "ListMaster v" + Constants.VERSION );
        frm.setBounds( 200, 300, 800, 600 );
        frm.setLayout( c );
        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frm.setVisible( true );

        // Start from top to bottom for GUI creation, beginning with the Menu
        JMenuBar bar = new JMenuBar();

        JMenu menuFile = new JMenu( "List Settings" );

        JMenuItem itemNewList = new JMenuItem( "New List" );
        JMenuItem itemOpenList = new JMenuItem( "Open List" );
        JMenuItem itemRefreshList = new JMenuItem( "Pull Updates for List" );
        JMenuItem itemSettings = new JMenuItem( "Settings" );
        JMenuItem itemExit = new JMenuItem( "Exit" );

        menuFile.add( itemNewList );
        menuFile.add( itemOpenList );
        menuFile.addSeparator();
        menuFile.add( itemRefreshList );
        menuFile.addSeparator();
        menuFile.add( itemSettings );
        menuFile.add( itemExit );

        JMenuItem itemReshowSearch = new JMenuItem( "Hide Search / Sort" );

        bar.add( menuFile );
        bar.add( itemReshowSearch );

        frm.setJMenuBar( bar );

        // Then the north panel with the search fields
        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets( 4, 4, 4, 4 );


        // North Panel ------------------------
        JPanel northPanel = new JPanel( new GridBagLayout() );
        northPanel.setBorder( b );

        // For now, I'm only allowing searches by type / name / steward / sn
        JLabel lblType = new JLabel( "Category: " );
        cc.gridx = 0;
        cc.gridy = 0;
        northPanel.add( lblType, cc );

        String[] options = { "All",
                "Camera",
                "Desktop",
                "Display",
                "Laptop",
                "Network",
                "Phone",
                "Printer",
                "Projector",
                "Scanner",
                "Server",
                "Storage",
                "Tablet",
                "Other" };
        JComboBox< String > cbxTypes = new JComboBox<>( options );
        cbxTypes.setSelectedIndex( 0 );
        cc.fill = GridBagConstraints.HORIZONTAL;
        cc.gridx = 1;
        cc.gridwidth = 1;
        northPanel.add( cbxTypes, cc );

        cc.fill = 0;
        cc.gridwidth = 1;

        JLabel lblName = new JLabel( "Device Name: " );
        cc.gridx = 0;
        cc.gridy = 1;
        northPanel.add( lblName, cc );

        JTextField txtName = new JTextField( 40 );
        cc.gridx = 1;
        northPanel.add( txtName, cc );

        JLabel lblSteward = new JLabel( "Device Steward: " );
        cc.gridx = 0;
        cc.gridy = 2;
        northPanel.add( lblSteward, cc );

        JTextField txtSteward = new JTextField( 40 );
        cc.gridx = 1;
        northPanel.add( txtSteward, cc );

        JLabel lblSN = new JLabel( "Device Serial Number: " );
        cc.gridx = 0;
        cc.gridy = 3;
        northPanel.add( lblSN, cc );

        JTextField txtSN = new JTextField( 40 );
        cc.gridx = 1;
        northPanel.add( txtSN, cc );

        JButton btnSearch = new JButton( "Search for Device" );
        cc.gridx = 1;
        cc.gridy = 5;
        cc.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add( btnSearch, cc );

        JButton btnClear = new JButton( "Clear" );
        cc.gridx = 0;
        cc.gridwidth = 1;
        northPanel.add( btnClear, cc );

        JProgressBar prgbrStatus = new JProgressBar();
        prgbrStatus.setValue( 20 );
        prgbrStatus.setMaximum( 100 );
        cc.gridx = 0;
        cc.gridy = 6;
        cc.gridwidth = 4;
        cc.fill = GridBagConstraints.HORIZONTAL;
        northPanel.add( prgbrStatus, cc );


        // South Panel --------------------
        JPanel southPanel = new JPanel( new BorderLayout() );
        southPanel.setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );

        String[] colNames = new String[]{ "Category", "Name", "Make", "Model", "Steward", "Room", "S/N" };

        DefaultTableModel tbl = new DefaultTableModel( 0, colNames.length );

        JTable tblInfo = new JTable( tbl );
        tblInfo.setCellSelectionEnabled( true );
        tbl.setColumnIdentifiers( colNames );
        DataHandler.getInstance().setTable( tblInfo );

        JScrollPane sp = new JScrollPane( tblInfo );
        southPanel.add( sp, BorderLayout.CENTER );

        frm.add( northPanel, BorderLayout.NORTH );
        frm.add( southPanel, BorderLayout.CENTER );


        Benchmarker.stop( "Creating GUI" );
        frm.repaint();

        // Add actionListeners and whatnot to elements, now that everything is defined.
        itemOpenList.addActionListener( actionEvent -> {
            // Open a dialog asking for .CSV values.
            // Since I'm asking for the MCL, it should be exported as .csv first, but
            // I want to make that automatic in the future for us Linux users.

            // Create the chooser, and restrict its usability to 'foolproof' it
            JFileChooser openFile = new JFileChooser();
            openFile.setDialogTitle( "Open MCL List" );
            openFile.setFileSelectionMode( JFileChooser.FILES_ONLY );
            openFile.setDragEnabled( true );
            openFile.setCurrentDirectory( new File( lastLocationForFile ) );
            openFile.setMultiSelectionEnabled( false );

            // Create a file name filter, so that only .csv types can be chosen
            openFile.setFileFilter( new FileNameExtensionFilter( "Comma Separated Values", "csv" ) );

            int status = openFile.showOpenDialog( frm );

            // If the user selects alright.
            // If not, keep using whatever file we were working with.
            if ( status == JFileChooser.APPROVE_OPTION ) {
                lastLocationForFile = openFile.getCurrentDirectory().getAbsolutePath();
                FileHandler.getInstance().setWorkingFile( openFile.getSelectedFile() );
                FileHandler.getInstance().processFile();
            }
        } );

        itemRefreshList.addActionListener( actionEvent -> {
            DataHandler.getInstance().reset();
            FileHandler.getInstance().processFile();

            // Test to make sure btnClear is set up first. It should be.
            // but hey, ya never know.

            if ( btnClear.getActionListeners().length != 0 )
                btnClear.doClick();
        } );

        itemSettings.addActionListener( actionEvent -> {

        } );

        itemReshowSearch.addActionListener( actionEvent -> {
            if ( formVersion == 0 ) {
                frm.remove( northPanel );
                itemReshowSearch.setText( "Show Search / Sort" );
                formVersion = 1;
            }
            else if ( formVersion == 1 ) {
                frm.add( northPanel, BorderLayout.NORTH );
                itemReshowSearch.setText( "Hide Search / Sort" );
                formVersion = 0;
            }
            northPanel.revalidate();
            northPanel.repaint();
            southPanel.revalidate();
            southPanel.repaint();
            frm.repaint();
        } );

        btnSearch.addActionListener( actionEvent -> {
            String[] toFilterBy = new String[ 4 ];
            toFilterBy[ 0 ] = String.valueOf( cbxTypes.getSelectedIndex() );
            toFilterBy[ 1 ] = txtName.getText();
            toFilterBy[ 2 ] = txtSteward.getText();
            toFilterBy[ 3 ] = txtSN.getText();
            prgbrStatus.setValue( prgbrStatus.getValue() + 10 );
            prgbrStatus.repaint();
            DataHandler.getInstance().refreshTable( toFilterBy );
        } );

        btnClear.addActionListener( actionEvent -> {
            txtName.setText( "" );
            txtSteward.setText( "" );
            txtSN.setText( "" );
            cbxTypes.setSelectedIndex( 0 );
            DataHandler.getInstance().refreshTable();
        } );

        itemExit.addActionListener( actionEvent -> {
            //TODO Add saving / apply changes to list
            System.exit( 0 );
        } );

        frm.setMinimumSize( new Dimension( 650, 600 ) );

        // By default, try to get a list from Documents.
        FileHandler.getInstance().getDefaultDocumentsList();

        frm.repaint();
    }

}
