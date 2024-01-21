package Model;
import java.util.*;
import java.time.LocalDate;

public class Library {
	 private List<Book> books;
	    private Map<Integer, User> users;
	    private List<Transaction> transactions;
	    private int transactionIdCounter;

	    public Library() {
	        this.books = new ArrayList<>();
	        this.users = new HashMap<>();
	        this.transactions = new ArrayList<>();
	        this.transactionIdCounter = 1;
	    }

	    public void addBook(Book book) {
	        books.add(book);
	    }

	    public void removeBook(Book book) {
	        books.remove(book);
	    }

	    public Book findBookById(int bookId) {
	        for (Book book : books) {
	            if (book.getBookId() == bookId) {
	                return book;
	            }
	        }
	        return null;
	    }

	    public User findUserById(int userId) {
	        return users.get(userId);
	    }

	    public void registerStudent(User user) {
	        users.put(user.getUserId(), user);
	    }

	    public boolean canBorrowBooks(User user) {
	        return user.getBorrowedBooks() < 3;
	    }

	    public void borrowBook(Book book, int userId) {
	        User user = users.get(userId);
	        if (user != null && canBorrowBooks(user)) {
	            if (book.isAvailable()) {
	                book.setAvailable(false);
	                LocalDate borrowDate = LocalDate.now();
	                Transaction transaction = new Transaction(transactionIdCounter++, userId, book.getBookId(), borrowDate);
	                transactions.add(transaction);
	                user.incrementBorrowedBooks();
	                System.out.println("Book \"" + book.getTitle() + "\" has been borrowed by " + user.getName());
	            } else {
	                System.out.println("Book is not available for borrowing.");
	            }
	        } else {
	            System.out.println("User not found or has borrowed the maximum number of books.");
	        }
	    }

	    public void returnBook(int bookId) {
	        Book book = findBookById(bookId);
	        if (book != null && !book.isAvailable()) {
	            book.setAvailable(true);
	            Transaction transaction = null;
	            for (Transaction t : transactions) {
	                if (t.getBookId() == bookId && t.getReturnDate() == null) {
	                    transaction = t;
	                    break;
	                }
	            }
	            if (transaction != null) {
	                transaction.setReturnDate(LocalDate.now());
	                User user = findUserById(transaction.getUserId());
	                user.decrementBorrowedBooks();
	                System.out.println("Book \"" + book.getTitle() + "\" has been returned.");
	                calculateFine(transaction); // Calculate and display fine, if applicable
	            }
	        } else {
	            System.out.println("Book not found or is already available.");
	        }
	    }

	    public List<Book> searchBooks(String searchTerm) {
	        List<Book> searchResults = new ArrayList<>();
	        for (Book book : books) {
	            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
	                    book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase()) ||
	                    book.getCategory().toLowerCase().contains(searchTerm.toLowerCase())) {
	                searchResults.add(book);
	            }
	        }
	        return searchResults;
	    }

	    private void calculateFine(Transaction transaction) {
	        LocalDate dueDate = transaction.getBorrowDate().plusDays(15);
	        LocalDate currentDate = LocalDate.now();
	        if (currentDate.isAfter(dueDate)) {
	            long daysOverdue = currentDate.toEpochDay() - dueDate.toEpochDay();
	            int fine = (int) (daysOverdue * 10); // Fine: 10 rupees per day
	            System.out.println("Fine: " + fine + " rupees.");
	        }
	    }
	    }

