package library;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy
     * ==================
     * BookCopy():
     *  exception
     * getBook():
     *  call getBook() and observe the returned book
     * getCondition():
     *  good, damaged
     * toString():
     *  statuses of good, damaged
     * equals():
     *  check for referential equality
     *  reflexivity, symmetry and transitivity
     * 
     */
    
    @Test
    public void testBookCopyConstructor() throws Exception {
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=Exception.class)
    public void testBookCopyConstructorException() throws Exception {
        BookCopy copy = new BookCopy(null);
    }
    
    
    @Test
    public void testGetBook() throws Exception {
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test
    public void testGetGoodCondition() throws Exception {
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(BookCopy.Condition.GOOD, copy.getCondition());
    }
    
    @Test
    public void testGetDamagedCondition() throws Exception {
        BookCopy.Condition damaged = BookCopy.Condition.DAMAGED;
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        
        copy.setCondition(damaged);
        assertEquals(damaged, copy.getCondition());
    }
    
    @Test
    public void testToStringGoodCondition() throws Exception {
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        assertTrue(copy.toString().indexOf("good") >= 0);
    }
    
    @Test
    public void testToStringDamagedCondition() throws Exception {
        BookCopy.Condition damaged = BookCopy.Condition.DAMAGED;
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(damaged);
        assertTrue(copy.toString().indexOf("damaged") >= 0);
    }
    
    @Test
    public void testEquals() throws Exception {
        Book book = new Book("Not good", Arrays.asList("Jon", "Joe"), 1990);
        Book anotherBook = book;
        Book yetAnotherBook = anotherBook;
        Book oneMoreBook = new Book("Okay", Arrays.asList("Jon") , 2000);
        
        assertTrue(book.equals(book));

        assertTrue(book.equals(anotherBook));
        assertTrue(anotherBook.equals(book));

        assertTrue(anotherBook.equals(yetAnotherBook));
        assertTrue(book.equals(yetAnotherBook));
        assertFalse(book.equals(oneMoreBook));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
