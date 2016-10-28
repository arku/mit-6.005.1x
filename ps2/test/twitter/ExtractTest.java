package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: Testing strategy:
     * Partitioning based on Interval
     * 1. Same timestamps
     * 2. Different timestamps
     * 
     * Partitioning based on quantity of tweets:
     * 1. Empty set of tweets
     * 2. Single tweet
     * 3. More than one tweet
     * 
     * Partitioning based on mentions
     * 1. No mentions
     * 2. Single mention
     * 3. More than a mention
     * 4. Invalid mention ie. a mention preceded or followed by one of the following
     *    characters A-Z, a-z, 0-9, _, -
     * Paritioning based on the position
     * 1. At the beginning of the tweet
     * 2. At the middle
     * 3. At the end
     * Partitioning based on case
     * 1. lowercase mention
     * 2. uppercase mention
     * 3. togglecase mention 
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
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanNoTweets() {
        Instant minInstant = Instant.MIN, maxInstant = Instant.MAX;
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        
        assertEquals("expected start", minInstant, timespan.getStart());
        assertEquals("expected end", maxInstant, timespan.getEnd());
    }
    
    @Test
    public void testGetTimeSpanMoreThanOneTweet() {
        Instant latest = Instant.parse("2016-03-10T19:00:00Z"), oldest = Instant.parse("2016-01-10T03:00:00Z");
        Tweet latestTweet = new Tweet(345, "Anonymous", "Nothing fancy", latest);
        Tweet oldestTweet = new Tweet(32, "Anonymous", "Feeling crazy", oldest);
        
        Timespan timespan = Extract.getTimespan(Arrays.asList( latestTweet, tweet1, tweet2, oldestTweet));
        
        assertEquals("expected start", oldest, timespan.getStart());
        assertEquals("expected start", latest, timespan.getEnd());
    }
    
    public void testGetTimeSpanSameTimestamp() {
        Instant instantOne = Instant.parse("2016-03-10T19:00:00Z");
        Tweet tweetOne = new Tweet(90, "Anonymous", "Nothing fancy", instantOne);
        Tweet tweetTwo = new Tweet(83, "Anonymous", "Feeling crazy", instantOne);
        
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweetOne, tweetTwo));
        
        assertEquals("expected start", instantOne, timespan.getStart());
        assertEquals("expected start", instantOne, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersNoTweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList());
        assertTrue("no mentioned users", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersSingleMention() {
        Tweet singleMentionTweet = new Tweet(3, "Joseph", "It was a great event @JOHN hosted by @john", Instant.now());
        
        Set<String> actualMentions = Extract.getMentionedUsers(Arrays.asList(singleMentionTweet));
        Set<String> expectedMentions = new HashSet<String>();
        expectedMentions.add("john");
        assertEquals(expectedMentions,actualMentions);
    }
    
    @Test
    public void testGetMentionedUsersMoreThanMention() {
        Tweet moreThanMentionTweet = new Tweet(3, "Joseph", "@JOhn It was a great event hosted by @joseph, thanks to @rick for organizing", Instant.now());
       
        
        Set<String> actualMentions = Extract.getMentionedUsers(Arrays.asList(moreThanMentionTweet));
        Set<String> expectedMentions = new HashSet<String>();
        expectedMentions.add("joseph");
        expectedMentions.add("john");
        expectedMentions.add("rick");
        assertEquals(expectedMentions,actualMentions);
    }
    
    @Test
    public void testGetMentionedUsersMentionsAtEnd() {
        Tweet tweetWithMentionsAtEnd = new Tweet(3, "Jon", "@Bran @Rick It was a great event hosted by @robb, thanks to @EddarD", Instant.now());
       
        
        Set<String> actualMentions = Extract.getMentionedUsers(Arrays.asList(tweetWithMentionsAtEnd));
        Set<String> expectedMentions = new HashSet<String>();
        
        expectedMentions.add("bran");
        expectedMentions.add("rick");
        expectedMentions.add("robb");
        expectedMentions.add("eddard");
        assertEquals(expectedMentions, actualMentions);
    }
    
    @Test
    public void testGetMentionedUsersInvalidMention() {
        Tweet singleMentionTweet = new Tweet(3, "Joseph", "It was a great event hosted by i@john", Instant.now());
        
        Set<String> actualMentions = Extract.getMentionedUsers(Arrays.asList(singleMentionTweet));
        assertTrue("expected no mentions", actualMentions.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
