/*
Lauren Acosta, CEN 3024C, 3/22/2024
class name: BookCatalog
the BookCatalog class serves as the backbone for managing the library's collection of books, providing essential functionalities such as adding, removing, checking out, and checking in books.
This class also calculates a checked out books due date and updates the status accordingly.
 */
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.time.format.DateTimeFormatter;

// BookCatalog class managing the collection of books
class BookCatalog {
    private List<Book> books;

    public BookCatalog() {
        this.books = new ArrayList<>();
    }

    // The addBooksFromFile method is responsible for populating the catalog of books by reading book information from a text file.
    // filePath specifies the path to the text file containing book information. The method reads this file to retrieve book details such as book ID, title, author, genre, and barcode.
    // No return value for this method (return type is void). Instead, it populates the catalog of books maintained by the BookCatalog class by adding Book objects created from the data read from the file.
    public void addBooksFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookInfo = line.split(",");
                int bookID = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1].trim();
                String author = bookInfo[2];
                String genre = bookInfo[3];
                String barcode = bookInfo[4].trim();
                String status = bookInfo[5].trim();
                String dueDate = bookInfo[6].trim();

                Book book = new Book(barcode, title, author, genre, status, dueDate);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The removeBookByBarcode method serves the purpose of removing a book from the catalog based on its barcode.
    // barcode argument represents the unique identifier of the book that needs to be removed from the catalog. It is used to locate and identify the book entry to be removed.
    // This method returns a boolean value indicating whether the removal operation was successful or not.
    // It returns true if a book with the specified barcode was found and removed successfully, and false if no book with the given barcode was found in the catalog.
    public boolean removeBookByBarcode(String barcode) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getBarcode().equals(barcode)) { // Check if the barcode matches
                iterator.remove();
                return true;
            }
        }
        return false; // If no book with the given barcode is found
    }

    // removeBookByTitle method serves the purpose of removing a book from the catalog based on its title.
    // title argument represents the title of the book that needs to be removed from the catalog. It is used to locate and identify the book entry to be removed.
    // The method returns a boolean value indicating whether the removal operation was successful or not.
    // It returns true if a book with the specified title was found and removed successfully, and false if no book with the given title was found in the catalog.
    public boolean removeBookByTitle(String title) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equals(title.trim())) {
                iterator.remove();
                return true;
            }
        }
        return false;    // If no book with the given title is found
    }

    // The checkOutBook method serves the purpose of checking out a book from the catalog.
    // title argument represents the title of the book that is being checked out. It is used to locate the book entry in the catalog.
    // The method does not have a return value. It performs an action of marking the book as checked out in the catalog. H
    // But it does print a message to determine if it was successful or not.
    public void checkOutBook(String title) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equalsIgnoreCase(title)) {
                if ("checked out".equals(book.getStatus())) {
                    System.out.println("Error: This book is already checked out.");
                    return;
                }
                // Calculate due date as 4 weeks from the current date
                LocalDate currentDate = LocalDate.now();
                LocalDate dueDate = currentDate.plusWeeks(4);
                book.setStatus("checked out");
                book.setDueDate(dueDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                System.out.println("Book checked out successfully. Due date: " + book.getDueDate());
                System.out.println("Book checked out successfully.");
                return; // Exit the method after successful checkout
            }
        }
        // If the loop completes without finding the book
        System.out.println("Error: Book not found.");
    }

    // The checkInBook method is responsible for marking a book as checked in within the catalog, indicating that it has been returned by the borrower and is available for borrowing again.
    // title argument represents the title of the book that is being checked in. It is used to locate the book entry in the catalog.
    // The method does not have a return value. It performs an action of marking the book as checked in the catalog.
    // it also does print a message to determine if it was successful or not.
    public boolean checkInBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if ("checked in".equals(book.getStatus())) {
                    System.out.println("Error: This book is already checked in.");
                    return false;
                }
                // Set status to "checked in"
                book.setStatus("checked in");

                // When a book is checked in, its status is changed to “checked in” and its due date is “null”.
                book.setDueDate(null);

                System.out.println("Book checked in successfully.");
                return true;
            }
        }
        // Book not found in the list, consider it as "checked in"
        System.out.println("Book checked in successfully.");
        return true;
    }

    // displayBooks method is to provide a visual representation of all the books present in the catalog.
    // It allows users or administrators to view the titles, authors, genres, barcodes, and other relevant details of each book.
    // The method does not take any arguments. It operates solely on the internal state of the BookCatalog object.
    // The method does not return any value. It simply prints the details of the books to the output.
    public void displayBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Return all books in the catalog
    public List<Book> getAllBooks() {
        return books;
    }

    // getTotalBooks method simply returns the size of the books list, which represents the total number of books in the catalog.
    public int getTotalBooks() {
        return books.size();
    }

    // The findBookByTitle method iterates through the list of books and returns the book with the matching title.
    //returns NULL if book is not found
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Book not found
    }

    //getBookByTitle provides a convenient way to retrieve a Book object based on its title from a collection of books
    //returns NULL if book is not found
    public Book getBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Return null if the book is not found
    }

    public void addBook(Book book) {
    }
}
