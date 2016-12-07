package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.Is;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // rep
    private final Set<BookCopy> inLibrary;
    private final Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // safety from rep exposure argument:
    // Both the fields are final. And no method in the class returns a copy of the rep, so rep
    // is not exposed
    
    public SmallLibrary() {
        inLibrary = new HashSet<>();
        checkedOut = new HashSet<>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert Collections.disjoint(inLibrary, checkedOut) == true;
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy copy = new BookCopy(book);
        inLibrary.add(copy);
            
        checkRep();
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        inLibrary.remove(copy);
        checkedOut.add(copy);
        
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        checkedOut.remove(copy);
        inLibrary.add(copy);
        
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> copies = new HashSet<>();
        addBookCopies(inLibrary, book, copies);
        addBookCopies(checkedOut, book, copies);
        return copies;
    }
    
    /**
     * Mutates copies by adding book copies present in a bookSet
     * which are copies of book
     * @param bookSet the set which contains the book copies
     * @param book the book for which the copies are to be added
     * @param copies the set of book copies to be mutated
     */
    private void addBookCopies(Set<BookCopy> bookSet, Book book, Set<BookCopy> copies) {
        for (BookCopy copy: bookSet) {
            if (copy.getBook().equals(book))
                copies.add(copy);
        }
    }
    
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> copies = new HashSet<>();
        addBookCopies(inLibrary, book, copies);
        return copies;
    }

    @Override
    public List<Book> find(String query) {
        List<Book> matchingBooks = new ArrayList<>();
        
        findMatchingBooks(inLibrary, matchingBooks, query);
        findMatchingBooks(checkedOut, matchingBooks, query);
        
        Collections.sort(matchingBooks);
        
        return matchingBooks;
    }
    
    /**
     * Mutates matchingBooks with matches founded in the books present in
     * the set of bookCopies
     * @param bookSet the set of book copies
     * @param matchingBooks the list of matching books
     * @param query the search term
     */
    private void findMatchingBooks(Set<BookCopy> bookSet, List<Book> matchingBooks, String query) {
        for(BookCopy copy: bookSet) {
            Book book = copy.getBook();
            // A book should appear at most once in a list
            if(!matchingBooks.contains(book) &&
               (isTitleMatching(book, query) ||
                isAuthorMatching(book, query)) )
                matchingBooks.add(book);
        }
        
        checkRep();
    }
    
    
    /**
     * Returns whether the book's title is the same as the query(case-sensitive)
     * @param book the book to query on
     * @param query the search term
     * @return true if the book's title matches query, false otherwise
     */
    private boolean isTitleMatching(Book book, String query) {
        return book.getTitle().equals(query);
    }
    
    /**
     * Returns whether one of the book's authors' names is 
     * the same as the query(case-sensitive)
     * @param book the book to query on
     * @param query the search term
     * @return true if the one of the book's author's names matches query, 
     * false otherwise
     */
    private boolean isAuthorMatching(Book book, String query) {
        for (String author: book.getAuthors()) {
            if (author.equals(query)) return true;
        }
        return false;
    }
    
    @Override
    public void lose(BookCopy copy) {
        if (inLibrary.contains(copy))
            inLibrary.remove(copy);
        else 
            checkedOut.contains(copy);
        checkRep();
    }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
