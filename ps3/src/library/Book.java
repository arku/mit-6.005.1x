package library;

import java.util.ArrayList;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    private String title;
    private List<String> authors;
    private int year;
    
    // Rep invariant:
    //  title.trim.length > 0
    //  authors.size > 0, for author in authors, author.trim.length > 0
    //  year > 0 
    // Abstraction function:
    //  represents an edition of a book with `title`, written by `authors` and published in `year`
    // TODO: safety from rep exposure argument
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) throws Exception{
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.year = year;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() throws Exception{
        checkTitle();
        checkAuthors();
        checkYear();
    }
    
    private void checkTitle() throws Exception {
        if(title.trim().length() == 0)
            throw new Exception("Title must atleast have a single character");
    }
    
    private void checkAuthors() throws Exception {
        if(authors.size() == 0)
            throw new Exception("Book must atleast have a single author");
        for(String author: authors) {
            if (author.trim().length() == 0)
                throw new Exception("Author name must atleast have a single character");
        }
    }
    
    private void checkYear() throws Exception {
        if(year < 0) throw new Exception("Year must be non-negative");
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

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
