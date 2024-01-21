package Model;
import java.util.*;


public class Main {
	public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int studentId = -1; // Initialize studentId to a default value

        Book book1 = new Book(1, "Book 1", "Author A", "Fiction");
        Book book2 = new Book(2, "Book 2", "Author B", "Mystery");
        library.addBook(book1);
        library.addBook(book2);

        System.out.println("Welcome to the Library Management System!");
        int choice;
        do {
            System.out.println("1. Student Registration");
            System.out.println("2. Book Borrow");
            System.out.println("3. Book Return");
            System.out.println("4. Book Search");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    studentId = scanner.nextInt();
                    scanner.nextLine(); // Clear the buffer
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();
                    User student = new User(studentId, studentName);
                    library.registerStudent(student);
                    System.out.println("Student \"" + student.getName() + "\" has been registered.");
                    break;
                case 2:
                    if (studentId == -1) {
                        System.out.println("Please register as a student first.");
                    } else {
                        System.out.print("Enter Book ID to borrow: ");
                        int borrowBookId = scanner.nextInt();
                        library.borrowBook(library.findBookById(borrowBookId), studentId);
                    }
                    break;
                case 3:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnBookId);
                    break;
                case 4:
                    scanner.nextLine(); // Clear the buffer
                    System.out.print("Enter search term (title, author, or category): ");
                    String searchTerm = scanner.nextLine();
                    List<Book> searchResults = library.searchBooks(searchTerm);
                    if (searchResults.isEmpty()) {
                        System.out.println("No books found matching the search term.");
                    } else {
                        System.out.println("Search Results:");
                        for (Book book : searchResults) {
                            System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() +
                                    ", Author: " + book.getAuthor() + ", Category: " + book.getCategory() +
                                    ", Availability: " + (book.isAvailable() ? "Available" : "Borrowed"));
                        }
                    }
                    break;
                case 5:
                    System.out.println("Exiting the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }
}