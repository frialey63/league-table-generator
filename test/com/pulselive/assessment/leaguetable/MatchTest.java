/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class comprises the test cases for the Match class.
 * 
 * @author Paul Parlett
 *
 */
public class MatchTest {

	@Test
	public void testGetResultHomeWin() {
		Match match = new Match("Arsenal", "Tottenham Hotspur", 1, 0);
		
		assertEquals(Match.Result.HOME_WIN, match.getResult());
	}

	@Test
	public void testGetResultAwayWin() {
		Match match = new Match("Tottenham Hotspur", "Arsenal", 0, 1);
		
		assertEquals(Match.Result.AWAY_WIN, match.getResult());
	}

	@Test
	public void testGetResultDraw() {
		Match match = new Match("Tottenham Hotspur", "Arsenal", 1, 1);
		
		assertEquals(Match.Result.DRAW, match.getResult());
	}

}
