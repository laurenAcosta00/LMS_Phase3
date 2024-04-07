import static org.junit.jupiter.api.Assertions.*;
/*
Lauren Acosta, CEN 3024C, 3/5/2024
class name: BookTest
BookTest Class to test the functionality of the Book class, verifies the correctness of the toString() method in the Book class.
 */

class BookTest {

    //create an object to be tested
    Book book;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        // Initialization of the Book object with test data
        book = new Book("1234567890", "Twilight", "Stephanie Meyer", "fiction", "checked out", "2024-04-15");

    }

    @org.junit.jupiter.api.Test
    void testToString() {

        String expected = "Book{bookID=1, title=Twilight, author=Stephanie Meyer, genre=fiction, barcode=1234567890}";
        // assert equals will compare two values and display a message if they are not equal
        assertEquals(expected, book.toString(), " error: String representation of the book doesnt match the expected value");
    }
}
