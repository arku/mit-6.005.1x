package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: Testing strategy:
     * writtenBy:
     * Partitioning based on quantity of tweets:
     *  1. No Tweets
     *  2. Exactly one tweet
     *  3. More than one tweet
     * Partitioning based on tweets written by username:
     *  1. No tweets written by the given username
     *  2. Single tweet written by the given username
     *  3. More than one tweet written by the given username
     *  Partitioning based on case:
     *  1. Uppercase username
     *  2. Lowercase username
     *  3. Togglecase username
     *  Partitioning based on the order of the tweets fed in as input
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByNoTweets() {
        List<Tweet> writtenBy = Filter.writtenBy(Collections.EMPTY_LIST, "lita");
        
        assertEquals("expected empty list", 0, writtenBy.size());
    }
    
    @Test
    public void testWrittenBySingleTweetNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1), "lita");
        
        assertEquals("expected empty list", 0, writtenBy.size());
    }
    
    @Test
    public void testWrittenByMoreThanOneTweetSingleResult() {
        Tweet tweetByLita = new Tweet(3456, "lita", "Nothing in here", d1);
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweetByLita), "lita");
        
        assertEquals("expected to be a singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain lita's tweet", writtenBy.contains(tweetByLita));
    }
    
    @Test
    public void testWrittenByMoreThanOneTweetMultipleResults() {
        Tweet tweet3 = new Tweet(3456, "lita", "Nothing in here", d1);
        Tweet tweet4 = new Tweet(567, "lita", "I am so fancy", d2);
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet4, tweet3), "lita");
        
        assertEquals("expected to be a 2 element list", 2, writtenBy.size());
        assertTrue("expected list to contain lita's tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain lita's tweet", writtenBy.contains(tweet4));
        assertEquals("expected tweet4 to appear before tweet3", writtenBy.get(0), tweet4);
        assertEquals("expected tweet3 to appear after tweet4", writtenBy.get(1), tweet3);
        
    }
    
    @Test
    public void testWrittenByMoreThanOneTweetMultipleResultsDifferentCase() {
        Tweet tweet3 = new Tweet(3456, "lita", "Nothing in here", d1);
        Tweet tweet4 = new Tweet(567, "LITA", "I am so fancy", d2);
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet4, tweet3, tweet1, tweet2), "liTA");
        
        assertEquals("expected to be a 2 element list", 2, writtenBy.size());
        assertTrue("expected list to contain lita's tweet", writtenBy.contains(tweet3));
        assertTrue("expected list to contain lita's tweet", writtenBy.contains(tweet4));
        assertEquals("expected tweet4 to appear before tweet3", writtenBy.get(0), tweet4);
        assertEquals("expected tweet3 to appear after tweet4", writtenBy.get(1), tweet3);
        
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
