/*
Lauren Acosta, CEN 3024C, 3/22/2024
class name: Book
The Book class represents individual books within the library management system and plays a crucial role in managing the attributes and behaviors of each book.
 */

class Book {
    private String barcode;
    private String author;
    private String genre;
    private String title;
    private String status;
    private String dueDate;

    //The purpose of this constructor method is to initialize a new Book object with the provided attributes such as book ID, title, author, genre, and barcode.
    // It creates a new instance of a book with the specified characteristics.
    public Book(String barcode, String title, String author, String genre, String status, String dueDate) {
        this.barcode = barcode;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.status = "available"; // Initialize status to "available"
        this.dueDate = dueDate;

    }

    // Getters
    public String getBarcode() {
        return barcode;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }



    public String getStatus() {
        return status;
    }

    public String getDueDate() {
        return dueDate;
    }

    // Setter
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

// The purpose of this toString() method is to provide a string representation of a Book object.
// It includes the book's attributes such as book ID, title, author, genre, and barcode, formatted in a specific way.
// The method returns a string containing the concatenation of the book's attributes along with their corresponding values.

    @Override
    public String toString() {
        return "Book{" +
                "title='" + barcode + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", barcode='" + title + '\'' +
                ", status='" + status + '\'' +
                ", dueDate='" + dueDate + '\'' +
                '}';
    }

}
