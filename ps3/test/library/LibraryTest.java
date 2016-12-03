package library;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy
     * ==================
     * 
     * buy():
     *  A new bookcopy
     *  already existing bookcopy
     * checkout():
     *  available copies - 1, n
     * checkIn():
     *  available copies - 1, n
     * isAvailable():
     *  Buy - before and after
     *  Checkout - before and after
     *  Checkin - before and after 
     * allCopies():
     *  copies - 0, 1 and n
     *  Checkout - before and after
     *  Checkin - before and after
     * availableCopies():
     *  copies - 0, 1 and n
     *  Checkout - before and after
     *  Checkin - before and after
     * find():
     *  title - case sensitivity
     *  same bookcopy and different bookcopies
     *  authors - case sensitivity
     *  searched author's position in the author's list
     *  date ordering
     * lose():
     *  available copies - 1, n
     *  
     */
    
    Library library;
    Book book;
    BookCopy copy;
    
    @Before
    public void setup() {
        library = makeLibrary();
        book = new Book("What", Arrays.asList("Arthur"), 2009);
        copy = new BookCopy(book);
    }
    
    @Test
    public void testBuyNewBookCopy() {        
        library.buy(book);
        assertEquals(true, library.isAvailable(copy));
        assertEquals(1, library.allCopies(book).size());
        assertEquals(1, library.availableCopies(book).size());
    }
    
    @Test
    public void testBuyExistingCopy() {
        library.buy(book);
        library.buy(book);
        assertEquals(true, library.isAvailable(copy));
        assertEquals(2, library.allCopies(book).size());
        assertEquals(2, library.availableCopies(book).size());
    }
    
    @Test
    public void testcheckOutSingleBookAvailable() {
        library.buy(book);
        assertEquals(1, library.allCopies(book).size());
        assertEquals(1, library.availableCopies(book).size());
        
        library.checkout(copy);
        assertEquals(1, library.allCopies(book).size());
        assertEquals(0, library.availableCopies(book).size());
    }
    
    @Test
    public void testcheckOutMoreThanBookAvailable() {
        library.buy(book);
        library.buy(book);
        assertEquals(2, library.allCopies(book).size());
        assertEquals(2, library.availableCopies(book).size());
        
        library.checkout(copy);
        assertEquals(2, library.allCopies(book).size());
        assertEquals(1, library.availableCopies(book).size());
        
        library.checkout(copy);
        assertEquals(2, library.allCopies(book).size());
        assertEquals(0, library.availableCopies(book).size());
        
    }
    
    @Test
    public void testCheckInSingleCopyAvailable() {
        library.buy(book);
        library.checkout(copy);
        
        assertEquals(1, library.allCopies(book).size());
        assertEquals(0, library.availableCopies(book).size());
        
        library.checkin(copy);
        assertEquals(1, library.allCopies(book).size());
        assertEquals(1, library.availableCopies(book).size());
    }
    
    @Test
    public void testCheckInMultipleCopiesAvailable() {
        library.buy(book);
        library.buy(book);
        library.checkout(copy);
        
        assertEquals(1, library.availableCopies(book).size());
        assertEquals(2, library.allCopies(book).size());

        library.checkin(copy);
        assertEquals(1, library.availableCopies(book).size());
        assertEquals(2, library.allCopies(book).size());
        
        
        library.checkout(copy);
        library.checkin(copy);
        assertEquals(2, library.availableCopies(book).size());
        assertEquals(2, library.allCopies(book).size());
    }
    
    @Test
    public void testIsAvailableBeforeAndAfterBuy() {
        assertEquals(false, library.isAvailable(copy));
        library.buy(book);
        assertEquals(true, library.isAvailable(copy));
    }
    
    @Test
    public void testIsAvailableBeforeAndAfterCheckout() {
        library.buy(book);
        assertEquals(true, library.isAvailable(copy));
        library.checkout(copy);
        assertEquals(false, library.isAvailable(copy));
    }
    
    @Test
    public void testIsAvailableBeforeAndAfterCheckin() {
        library.buy(book);
        library.checkout(copy);
        assertEquals(false, library.isAvailable(copy));
        library.checkin(copy);
        assertEquals(true, library.isAvailable(copy));
    }
    
    @Test
    public void testAllCopiesEmptySet() {
        assertEquals(Collections.emptySet(), library.allCopies(book));
    }
    
    @Test
    public void testAllCopiesSingletonSet() {
        library.buy(book);
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy));
        assertEquals(availableCopies, library.allCopies(book));
    }
    
    @Test
    public void testAllCopiesMoreThanOneCopy() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy, copy));
        assertEquals(availableCopies, library.allCopies(book));
    }
    
    @Test
    public void testAllCopiesBeforeAndAfterCheckout() {
        library.buy(book);
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy));
        
        assertEquals(availableCopies, library.allCopies(book));
        library.checkout(copy);
        assertEquals(availableCopies, library.allCopies(book));
    }
    
    @Test
    public void testAllCopiesBeforeAndAfterCheckin() {
        library.buy(book);
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy));
        
        library.checkout(copy);
        assertEquals(availableCopies, library.allCopies(book));
        library.checkin(copy);
        assertEquals(availableCopies, library.allCopies(book));
    }
    
    @Test
    public void testAvailableCopiesEmptySet() {
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    
    @Test
    public void testAvailableCopiesSingletonSet() {
        library.buy(book);
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy));
        assertEquals(availableCopies, library.availableCopies(book));
    }
    
    @Test
    public void testAvailableCopiesMoreThanOneCopy() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy, copy));
        assertEquals(availableCopies, library.availableCopies(book));
    }
    
    
    @Test
    public void testAvailableCopiesBeforeAndAfterCheckout() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy));
        assertEquals(availableCopies, library.availableCopies(book));
        
        library.checkout(copy);
        availableCopies.remove(copy);
        assertEquals(availableCopies, library.availableCopies(book));
        
        library.checkout(copy);
        assertEquals(Collections.emptySet(), library.allCopies(book));
    }
    
    @Test
    public void testAvailableCopiesBeforeAndAfterCheckin() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> availableCopies = new HashSet<>(Arrays.asList(copy, copy));

        library.checkout(copy);
        availableCopies.remove(copy);
        assertEquals(availableCopies, library.availableCopies(book));
        
        library.checkin(copy);
        availableCopies.add(copy);
        assertEquals(availableCopies, library.availableCopies(book));
    }
    
    
    @Test
    public void testFindExactTitleMatch() {
        library.buy(book);
        Book anotherBook = new Book("What", Arrays.asList("John"), 2004);
        BookCopy anotherBookCopy = new BookCopy(anotherBook);
        library.buy(anotherBook);
        
        Set<BookCopy> expectedBookCopies = new HashSet<>(Arrays.asList(copy, anotherBookCopy));
        assertEquals(expectedBookCopies, library.find(anotherBook.getTitle()));
    }
    
    @Test
    public void testFindExactTitleMatchSameBook() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> expectedBookCopies = new HashSet<>(Arrays.asList(copy));
        assertEquals(1, library.find(book.getAuthors().get(0)).size());
        assertEquals(expectedBookCopies, library.find(book.getTitle()));
    }
    
    @Test
    public void testFindInexactTitleMatch() {
        library.buy(book);
        assertEquals(Collections.emptySet(), library.find(book.getTitle().toLowerCase()));
        assertEquals(Collections.emptySet(), library.find(book.getTitle().toUpperCase()));
        assertEquals(Collections.emptySet(), library.find("WhAT"));
    }
    
    @Test
    public void testFindExactAuthorMatch() {
        Book anotherBook = new Book("Nothing", Arrays.asList("Arthur"), 2004);
        Book yetAnotherBook = new Book("Locked", Arrays.asList("Jon", "Joseph"), 2000);
        BookCopy anotherBookCopy = new BookCopy(anotherBook);
        
        library.buy(book);
        library.buy(anotherBook);
        library.buy(yetAnotherBook);
        
        Set<BookCopy> expectedBookCopies = new HashSet<>(Arrays.asList(copy, anotherBookCopy));
        assertEquals(expectedBookCopies, library.find(anotherBook.getTitle()));
    }
    
    @Test
    public void testFindExactAuthorMatchSameBook() {
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> expectedBookCopies = new HashSet<>(Arrays.asList(copy));
        assertEquals(1, library.find(book.getAuthors().get(0)).size());
        assertEquals(expectedBookCopies, library.find(book.getAuthors().get(0)));
    }
    
    @Test
    public void testFindInexactAuthor() {
        library.buy(book);
        String author = book.getAuthors().get(0);
        assertEquals(Collections.emptySet(), library.find(author.toLowerCase()));
        assertEquals(Collections.emptySet(), library.find(author.toUpperCase()));
        assertEquals(Collections.emptySet(), library.find("ARthUr"));
    }
    
    @Test
    public void testFindAuthorMatchFirstPosition() {
        library.buy(book);

        Set<BookCopy> matches = new HashSet<>(Arrays.asList(copy));
        assertEquals(matches, library.find(book.getAuthors().get(0)));
    }
    
    @Test
    public void testFindAuthorMatchLastPosition() {
        Book newBook = new Book("How to fly", Arrays.asList("Vic", "Kumar", "Arthur"), 2014);
        BookCopy newBookCopy = new BookCopy(newBook);
        
        Set<BookCopy> matches = new HashSet<>(Arrays.asList(newBookCopy));
        assertEquals(matches, library.find(book.getAuthors().get(2)));
    }
    
    @Test
    public void testFindSameTitleAuthorDifferentPublicationDates() {
        Book anotherBook = new Book("What", Arrays.asList("Arthur"), 2015);
        BookCopy anotherBookCopy = new BookCopy(anotherBook);
        
        Set<BookCopy> matches = new HashSet<>(Arrays.asList(anotherBookCopy, copy));
        assertEquals(matches, library.find("What"));
    }
    
    
    @Test
    public void testLoseSingleCopyAvailable() {
        
        library.buy(book);
        Set<BookCopy> copies = new HashSet<>(Arrays.asList(copy));
        
        assertEquals(true, library.isAvailable(copy));
        assertEquals(copies, library.allCopies(book));
        assertEquals(copies, library.availableCopies(book));
        
        library.lose(copy);
        
        assertEquals(false, library.isAvailable(copy));
        assertEquals(Collections.emptySet(), library.allCopies(book));
        assertEquals(Collections.emptySet(), library.availableCopies(book));
    }
    
    
    @Test
    public void testLoseMultipleCopiesAvailable() {
        
        library.buy(book);
        library.buy(book);
        
        Set<BookCopy> copies = new HashSet<>(Arrays.asList(copy, copy));
        
        assertEquals(true, library.isAvailable(copy));
        assertEquals(copies, library.allCopies(book));
        assertEquals(copies, library.availableCopies(book));
        
        library.lose(copy);
        copies.remove(copy);
        
        assertEquals(false, library.isAvailable(copy));
        assertEquals(copies, library.allCopies(book));
        assertEquals(copies, library.availableCopies(book));
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
