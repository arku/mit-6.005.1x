package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * Book():
     *  Exception
     * getTitle():
     *  create an object and observe
     * getAuthors():
     *  number of authors - 1, n
     * getYear():
     *  create an object and observe
     * toString():
     *  create an object and inspect the string representation 
     * equals():
     *  title - case sensitive
     *  author - case sensitive
     *  reflexivity, symmetry and transitivity
     *  
     */
    
    @Test
    public void testBookConstructor() {
        String bookTitle = "My life";
        List<String> authors = Arrays.asList("Anonymous");
        int year = 1900;

        Book book = new Book(bookTitle, authors, year);
        assertEquals(bookTitle, book.getTitle());
        assertEquals(authors, book.getAuthors());
        assertEquals(year, book.getYear());
    }
    
    @Test(expected=AssertionError.class)
    public void testBookConstructorInvalidTitle() {
        List<String> authors = Arrays.asList("Anonymous");
        Book book = new Book("  ", authors, 1900);
    }
    
    @Test(expected=AssertionError.class)
    public void testBookConstructorInvalidAuthorsList() {
        String bookTitle = "My life";
        List<String> authors = new ArrayList<>();

        Book book = new Book(bookTitle, authors, 1900);
        assertEquals(bookTitle, book.getTitle());
        
    }
    
    @Test(expected=AssertionError.class)
    public void testBookConstructorNegativeYear() {
        String bookTitle = "My life";
        List<String> authors = Arrays.asList("Anonymous");
        Book book = new Book(bookTitle, authors, -1900);
    }
    
    @Test
    public void testGetTitle() {
        String bookTitle = "My life";
        List<String> authors = new ArrayList<>();
        authors.add("Anonymous");

        Book book = new Book(bookTitle, authors, 1900);
        assertEquals(bookTitle, book.getTitle());
        
    }
    
    @Test
    public void testGetAuthorsSingleAuthor() {
        String bookTitle = "My life";
        String author = "Anonymous";
        List<String> authors = new ArrayList<>();
        authors.add(author);

        Book book = new Book(bookTitle, authors, 1900);
        
        assertEquals(1, book.getAuthors().size());
        assertEquals(author, book.getAuthors().get(0));
        
    }
    
    @Test
    public void testGetAuthorsMoreThanOneAuthor() {
        String bookTitle = "My life";
        List<String> authors = new ArrayList<>();
        authors.add("joseph");
        authors.add("JOSEPH");

        Book book = new Book(bookTitle, authors, 1900);
        
        assertEquals(2, book.getAuthors().size());
        assertEquals("joseph", book.getAuthors().get(0));
        assertEquals("JOSEPH", book.getAuthors().get(1));
        
    }

    @Test
    public void testGetYear() {
        String bookTitle = "My life";
        List<String> authors = new ArrayList<>();
        authors.add("Anonymous");

        Book book = new Book(bookTitle, authors, 1900);
        assertEquals(1900, book.getYear());
        
    }
    
    @Test
    public void testToString() {
        String bookTitle = "Nothing fancy";
        List<String> authors = Arrays.asList("Jon", "Kevin", "Cody");
        int publishedYear = 2009;
        
        Book book = new Book(bookTitle, authors, publishedYear);

        assertTrue(book.toString().indexOf(bookTitle) >= 0);
        for(String author: authors)
            assertTrue(book.toString().indexOf(author) >= 0);
        assertTrue(book.toString().indexOf(String.valueOf(publishedYear)) >= 0);
    }
    
    @Test
    public void testEqualsSameBook() {
        String bookTitle = "Nothing fancy";
        List<String> authors = Arrays.asList("Jon", "Kevin", "Cody");
        int publishedYear = 2009;
        
        Book book = new Book(bookTitle, authors, publishedYear);
        assertTrue(book.equals(book));
    }
    
    @Test
    public void testEqualsSymmetryAndTransitivity() {
        String bookTitle = "Nothing fancy";
        List<String> authors = Arrays.asList("Jon", "Kevin", "Cody");
        int publishedYear = 2009;
        
        Book book = new Book(bookTitle, authors, publishedYear);
        Book anotherBook = new Book(bookTitle, authors, publishedYear);
        Book yetAnotherBook = new Book(bookTitle, authors, publishedYear);
        
        assertTrue(book.equals(anotherBook));
        assertTrue(anotherBook.equals(book));
        assertTrue(yetAnotherBook.equals(anotherBook));
        assertTrue(book.equals(yetAnotherBook));
        
        assertTrue(book.hashCode() == anotherBook.hashCode());
        assertTrue(anotherBook.hashCode() == yetAnotherBook.hashCode());
        assertTrue(book.hashCode() == yetAnotherBook.hashCode());
    }
    
    @Test
    public void testEqualsDifferentBooks() {
        String bookTitle = "Nothing fancy";
        List<String> authors = Arrays.asList("Jon", "Kevin", "Cody");
        int publishedYear = 2009;
        
        List<String> authorNamesUpperCased = Arrays.asList("JON", "KEVIN", "CODY");

        Book book = new Book(bookTitle, authors, publishedYear);
        Book anotherBook = new Book(bookTitle.toLowerCase(), authors, publishedYear);
        Book yetAnotherBook = new Book(bookTitle, authorNamesUpperCased, publishedYear-1);
        
        assertFalse(book.equals(anotherBook));
        assertFalse(book.equals(yetAnotherBook));
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
