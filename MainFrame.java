/*
Lauren Acosta, CEN 3024C, 3/20/2024
class name: MainFrame
The main objective of the program is to provide a user-friendly interface for managing a library's book catalog. Users can perform operations such as adding new books, removing existing ones, checking books in and out, and viewing the entire catalog.
The MainFrame class encapsulates these functionalities and presents them in a visually appealing GUI, enhancing the user experience.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// A. Lauren Acosta, CEN 3024C, 4/7/2024
//B. MainFrame extends JFrame
//C. MainFrame extends JFrame class establishes the connection to MySQL database using parameters (URL, USERNAME, PASSWORD) to enable database operations within the application.
public class MainFrame extends JFrame {

    // parameters for establishing a connection to MySQL database from Java application
    private static final String URL = "jdbc:mysql://localhost:3306/L_M_S";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Lucca068!";
    private Connection connection;

    //instance variables for GUI components and objects
    private JTextField fileNameField;
    private JTextArea databaseTextArea;
    private JButton loadFileButton;
    private BookCatalog catalog;

    private JTextField barcodeField;
    private JButton removeByBarcodeButton;
    private JTextField titleField;
    private JButton removeByTitleButton;

    private JTextField checkOutTitleField;
    private JButton checkOutButton;
    private JTextField checkInTitleField;
    private JButton checkInButton;

    //A. Lauren Acosta, CEN 3024C, 4/7/2024
    //B. MainFrame
    //C. MainFrame class creates the user interface (GUI) window of the library management system (LMS) application. It's the primary interface through which users can interact with the system, such as loading books from a file, displaying information about books in the database, removing books by barcode or title, and checking books in and out.
    public MainFrame() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize database connection
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Initialize book catalog
        catalog = new BookCatalog();


        // Initialize GUI components
        fileNameField = new JTextField(20);

        databaseTextArea = new JTextArea();
        databaseTextArea.setEditable(false);
        JButton loadDatabaseButton = new JButton("Load Books from Database");
        barcodeField = new JTextField(10);
        removeByBarcodeButton = new JButton("Remove by Barcode");
        titleField = new JTextField(20);
        removeByTitleButton = new JButton("Remove by Title");

        checkOutTitleField = new JTextField(20);
        checkOutButton = new JButton("Check Out");
        checkInTitleField = new JTextField(20);
        checkInButton = new JButton("Check In");




        // Remove by barcode button action listener
        removeByBarcodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = barcodeField.getText();
                removeBookByBarcode(barcode);
            }
        });

        // Remove by title button action listener
        removeByTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                removeBookByTitle(title);
            }
        });

        // Check out button action listener
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = checkOutTitleField.getText();
                checkOutBook(title);
            }
        });

        // Check in button action listener
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = checkInTitleField.getText();
                checkInBook(title);
            }
        });

        // Layout setup
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JLabel databaseLabel = new JLabel("Enter database name: ");

        //inputPanel.add(new JLabel("Enter database name: "));
        JTextField databaseNameField = new JTextField(20); // Text field for database name
        inputPanel.add(loadDatabaseButton);

        // Action listener for the load database button
        loadDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the database name entered by the user
                String databaseName = databaseNameField.getText();
                loadBooksFromDatabase(); // Call the method to load books from the specified database
            }
        });

        // Add components to inputPanel
        inputPanel.add(databaseLabel);
        inputPanel.add(databaseNameField);
        inputPanel.add(loadDatabaseButton);

        // Add inputPanel to mainPanel
        mainPanel.add(inputPanel, BorderLayout.NORTH);

// Set mainPanel as the content pane of the frame
        setContentPane(mainPanel);

// Make the frame visible
        setVisible(true);

        JPanel actionsPanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column
        actionsPanel.add(new JLabel("Enter barcode to remove: "));
        actionsPanel.add(barcodeField);
        actionsPanel.add(removeByBarcodeButton);
        actionsPanel.add(new JLabel("Enter title to remove: "));
        actionsPanel.add(titleField);
        actionsPanel.add(removeByTitleButton);
        actionsPanel.add(new JLabel("Enter title to check in: "));
        actionsPanel.add(checkInTitleField);
        actionsPanel.add(checkInButton);
        actionsPanel.add(new JLabel("Enter title to check out: "));
        actionsPanel.add(checkOutTitleField);
        actionsPanel.add(checkOutButton);

        mainPanel.add(actionsPanel, BorderLayout.WEST);

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
        exitPanel.add(exitButton);
        mainPanel.add(exitPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }




    // 1. loadBooksFromDatabase
    // 2. This method loads book information from a MySQL database and updates the BookCatalog accordingly. It retrieves data from the "books" table, creates Book objects, and adds them to the catalog.
    // 3. This method doesn't take any arguments.
    // 4. It doesn't return any value (void).
    private void loadBooksFromDatabase() {
        try {
            // Modify the SQL query to include the database name dynamically

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String barcode = resultSet.getString("barcode");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String status = resultSet.getString("status");
                String dueDate = resultSet.getString("due_date");

                Book book = new Book(barcode, title, author, genre, status, dueDate);
                catalog.addBook(book);
            }
            updateDatabaseTextArea();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 1. removeBookByBarcode
    // 2. This method removes a book from both the MySQL database and the BookCatalog based on its barcode. It executes a SQL DELETE query to remove the book entry from the "books" table where the barcode matches the provided parameter.
    // 3. Argument: String barcode: The barcode of the book to be removed.
    // 4. This method doesn't return any value (void).
    private void removeBookByBarcode(String barcode) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE barcode = ?");
            statement.setString(1, barcode);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book with barcode '" + barcode + "' removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                catalog.removeBookByBarcode(barcode);
                updateDatabaseTextArea();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Book with barcode '" + barcode + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. removeBookByTitle
    // 2. This method removes a book from both the MySQL database and the BookCatalog based on its title. It executes a SQL DELETE query to remove the book entry from the "books" table where the title matches the provided parameter.
    // 3. Argument: String title: The title of the book to be removed.
    // 4. This method doesn't return any value (void).
    private void removeBookByTitle(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM books WHERE title = ?");
            statement.setString(1, title);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book '" + title + "' removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                catalog.removeBookByTitle(title);
                updateDatabaseTextArea();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Book '" + title + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. checkOutBook
    // 2. This method updates the status of a book in the MySQL database to "checked out" and sets the due date to four weeks from the current date. It executes an SQL UPDATE query to modify the "status" and "dueDate" columns in the "books" table where the title matches the provided parameter.
    // 3. Argument: String title: The title of the book to be checked out.
    // 4. This method doesn't return any value (void).
    private void checkOutBook(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE books SET status = 'checked out', dueDate = DATE_ADD(NOW(), INTERVAL 4 WEEK) WHERE title = ?");
            statement.setString(1, title);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book '" + title + "' checked out successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateDatabaseTextArea();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Book '" + title + "' not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. checkInBook
    // 2. his method updates the status of a book in the MySQL database to "checked in" and sets the due date to NULL. It executes an SQL UPDATE query to modify the "status" and "dueDate" columns in the "books" table where the title matches the provided parameter.
    // 3. Argument: String title: The title of the book to be checked in.
    // 4. This method doesn't return any value (void).
    private void checkInBook(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE books SET status = 'checked in', dueDate = NULL WHERE title = ?");
            statement.setString(1, title);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book '" + title + "' checked in successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateDatabaseTextArea();
            } else {
                JOptionPane.showMessageDialog(this, "Error: Book '" + title + "' not found or is already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. updateDatabaseTextArea
    // 2. This method updates the text area in the GUI with information about all the books stored in the catalog. It iterates through all the books in the catalog, retrieves their information, and appends each book's details to a StringBuilder object
    // 3. No arguments.
    // 4. This method doesn't return any value (void).
    private void updateDatabaseTextArea() {
        StringBuilder sb = new StringBuilder();
        for (Book book : catalog.getAllBooks()) {
            sb.append(book).append("\n");
        }
        databaseTextArea.setText(sb.toString());
    }

    // A. Lauren Acosta, CEN 3024C, 4/7/2024
    // B. Main
    // C. The main objective of the program is to provide a user-friendly interface for managing a library's book catalog. Users can perform operations such as adding new books, removing existing ones, checking books in and out, and viewing the entire catalog. The MainFrame class encapsulates these functionalities and presents them in a visually appealing GUI, enhancing the user experience.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
    
