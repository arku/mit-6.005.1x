package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: Testing strategy
     * guessFollowsGraph:
     * Partitioning based on quantity of tweets:
     *  1. No Tweets
     *  2. Exactly one tweet
     *  3. More than one tweet
     *  Partitioning based on authors:
     *  1. Author not being a follower
     *  2. Author being a followee
     * Partitioning based on cases:
     *  1. Uppercase
     *  2. Lowercase
     *  3. ToggleCase
     *
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Tweet tweet1 = new Tweet(1, "kumar", "I don't mention anyone", d1);
    private static final Tweet tweet2 = new Tweet(2, "Rob", "Thanks to @keVin @john @joseph", d1);
    private static final Tweet tweet3 = new Tweet(3, "LisA", "Thanks to @kevin @JOHN @kumar @rob @joseph", d1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphNoTweetsEmptyGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphSingleTweetEmptyGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphSingleTweetNonEmptyGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        
        assertFalse("expected non empty graph", followsGraph.isEmpty());
        assertFalse("map should not contain author as key", followsGraph.containsKey("kumar"));
        assertEquals("map should contain three keys", followsGraph.keySet().size(), 3);
        assertEquals("the keys should have 1 value each", 1, followsGraph.get("joseph").size());
        assertEquals("the keys should have 1 value each", 1, followsGraph.get("john").size());
        assertEquals("the keys should have 1 value each", 1, followsGraph.get("kevin").size());
    }
    
    // private static final Tweet tweet2 = new Tweet(2, "Rob", "Thanks to @keVin @john @joseph", d1);
    // private static final Tweet tweet3 = new Tweet(3, "LisA", "Thanks to @kevin @JOHN @kumar @rob @joseph", d1);
    
    @Test
    public void testGuessFollowsGraphSingleMultipleTweetsNonEmptyGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet3));
        
        assertFalse("expected non empty graph", followsGraph.isEmpty());
        
        assertEquals("map should contain 5 keys", 5, followsGraph.keySet().size());
        
        assertFalse("author(lisa) should not be a key", followsGraph.containsKey("lisa"));
        assertTrue("author(rob) should be a key", followsGraph.containsKey("rob"));
        assertEquals("rob should have a follower", 1, followsGraph.get("rob").size());

        assertFalse("map should not contain two Kevins", followsGraph.containsKey("keVin"));
        assertTrue("map should contain a Kevin", followsGraph.containsKey("kevin"));
        assertEquals("joseph should have two followers", 2, followsGraph.get("joseph").size());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
