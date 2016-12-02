package library;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    private final Book book;
    private Condition condition;
    
    // Rep invariant
    //   book cannot be null
    // Abstraction function:
    //   represents a copy of a book
    // Safety from rep exposure argument:
    // All the fields are private and book is an immutable type
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) throws Exception{
        this.book = book;
        this.condition = Condition.GOOD;
        
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() throws Exception {
        if (book == null) throw new Exception("Book cannot be null");
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return book;
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append(book.toString());
        builder.append(" Status - " + condition.toString().toLowerCase());
        
        return builder.toString();
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
