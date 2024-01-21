package Model;

class User {
	
    private int userId;
    private String name;
    private int borrowedBooks;

    public User(int userId, String name) {
    	
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = 0;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void incrementBorrowedBooks() {
        borrowedBooks++;
    }

    public void decrementBorrowedBooks() {
        borrowedBooks--;
    }
}