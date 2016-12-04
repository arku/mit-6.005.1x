package library;

import java.util.ArrayList;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book implements Comparable<Book> {

    private final String title;
    private final List<String> authors;
    private final int year;
    
    // Rep invariant:
    //  title.trim.length > 0
    //  authors.size > 0, for author in authors, author.trim.length > 0
    //  year > 0 
    // Abstraction function:
    //  represents an edition of a book with `title`, written by `authors` and published in `year`
    // Safety from rep exposure argument:
    //  All fields are private. authors is mutable, so defensive copies are made for authors list
    //  while creating  and returning the list
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.-
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.year = year;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        checkTitle();
        checkAuthors();
        checkYear();
    }
    
    private void checkTitle() {
        assert title.trim().length() != 0;
    }
    
    private void checkAuthors() {
        assert authors.size() != 0;
        
        for(String author: authors)
            assert author.trim().length() != 0;
    }
    
    private void checkYear() {
        assert year >= 0;
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return new ArrayList<>(authors);
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(title + " Written by ");
        
        for(String author: authors)
            builder.append(author + " ");
        
        builder.append("Published in " + year);
        return builder.toString();
    }

    
    @Override
    public boolean equals(Object that) {
        if (!(that instanceof Book)) return false;
        Book thatBook = (Book) that;
        return this.getTitle() == thatBook.getTitle() &&
               this.getAuthors().equals(thatBook.getAuthors()) &&
               this.getYear() == thatBook.getYear();
    }
    
    @Override
    public int hashCode() {
         int hashCode = this.getTitle().hashCode();
         for(String author: this.getAuthors())
             hashCode += author.hashCode();
         hashCode += getYear();
         return hashCode;
    }
    
    @Override
    public int compareTo(Book that) {
        if (this.getTitle().equals(that.getTitle()) &&
            this.getAuthors().equals(that.getAuthors())) {
            if (this.getYear() < that.getYear())
                return 1;
            else if (this.getYear() > that.getYear())
                return -1;
            else return 0;
        }
        
        else
            return this.getTitle().compareTo(that.getTitle());
            
    }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}

