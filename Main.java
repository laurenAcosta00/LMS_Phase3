/*
Lauren Acosta, CEN 3024C, 3/22/2024
class name: Main
The Main class encapsulates the core functionality of the library management system,
which includes managing books, facilitating user interactions, and ensuring the smooth execution of the program
to achieve the efficiency of managing a library's catalog and borrowing system.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Library Management System!");

        // Instantiate BookCatalog
        BookCatalog catalog = new BookCatalog();

        // task 1: Ask user for file name and add books from the file
        System.out.print("Enter the file name: ");
        String fileName = scanner.nextLine();
        catalog.addBooksFromFile(fileName);

        // Task 2: Print the database with a message indicating printing is taking place
        System.out.println("Printing the database...");
        catalog.displayBooks();
        System.out.println("Database printing completed.");

        // Task 3: Remove a book by barcode number
        System.out.print("Enter barcode number to remove: ");
        String barcodeToRemove = scanner.nextLine();
        boolean removedByBarcode = catalog.removeBookByBarcode(barcodeToRemove);
        if (removedByBarcode) {
            System.out.println("Book with barcode '" + barcodeToRemove + "' removed successfully.");
        } else {
            System.out.println("Book with barcode '" + barcodeToRemove + "' not found.");
        }

        //  Reprint the updated library
        System.out.println("Updated library after removal:");
        catalog.displayBooks();

        //scanner.close();

        // Task 4: Remove a book by title
        System.out.print("Enter title to remove: ");
        String titleToRemove = scanner.nextLine();
        boolean removedByTitle = catalog.removeBookByTitle(titleToRemove);
        if (removedByTitle) {
            System.out.println("Book '" + titleToRemove + "' removed successfully.");
        } else {
            System.out.println("Book '" + titleToRemove + "' not found.");
        }

        //  Reprint the updated library
        System.out.println("Updated library after removal:");
        catalog.displayBooks();

        // Task 5: Check out a book
        System.out.print("Enter title to check out: ");
        String titleToCheckout = scanner.nextLine();
        catalog.checkOutBook(titleToCheckout);

        System.out.println("Updated library after removal:");
        catalog.displayBooks();

        // Task 6: Check in a book
        System.out.print("Enter title to check in: ");
        String titleToCheckin = scanner.nextLine();
        catalog.checkInBook(titleToCheckin);

        // Display all books after modifications
        System.out.println("Updated Book Catalog:");
        catalog.displayBooks();

        scanner.close();
    }
}
