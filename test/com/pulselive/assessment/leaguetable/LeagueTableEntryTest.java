/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class comprises the test cases for the LeagueTableEntry class.
 * 
 * @author Paul Parlett
 *
 */
public class LeagueTableEntryTest {

	@Test
	public void testCompareToPoints() {
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal",         0, 0, 1);
		LeagueTableEntry spurs = new LeagueTableEntry("Tottenham Hotspur", 0, 0, 0);
		
		assertTrue(arsenal.compareTo(spurs) > 0);
		assertTrue(spurs.compareTo(arsenal) < 0);
	}

	@Test
	public void testCompareToSamePointsGoalDifference() {
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal",         0, 1, 1);
		LeagueTableEntry spurs = new LeagueTableEntry("Tottenham Hotspur", 0, 0, 1);
		
		assertTrue(arsenal.compareTo(spurs) > 0);
		assertTrue(spurs.compareTo(arsenal) < 0);
	}

	@Test
	public void testCompareToSamePointsAndGoalDifferenceGoalsFor() {
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal",         1, 1, 1);
		LeagueTableEntry spurs = new LeagueTableEntry("Tottenham Hotspur", 0, 1, 1);
		
		assertTrue(arsenal.compareTo(spurs) > 0);
		assertTrue(spurs.compareTo(arsenal) < 0);
	}

	@Test
	public void testCompareToSamePointsAndGoalDifferenceAndGoalsFor() {
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal",         1, 1, 1);
		LeagueTableEntry spurs = new LeagueTableEntry("Tottenham Hotspur", 1, 1, 1);
		
		assertTrue(arsenal.compareTo(spurs) < 0);
		assertTrue(spurs.compareTo(arsenal) > 0);
	}
	
	@Test
	public void testGetPlayed() {
		int won = 8;
		int drawn = 2;
		int lost = 0;
		int goalsFor = 20;
		int goalsAgainst = 5;
		
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal", won, drawn, lost, goalsFor, goalsAgainst);
		
		assertEquals((won + drawn + lost), arsenal.getPlayed());
	}
	
	@Test
	public void testGetGoalDifference() {
		int won = 8;
		int drawn = 2;
		int lost = 0;
		int goalsFor = 20;
		int goalsAgainst = 5;
		
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal", won, drawn, lost, goalsFor, goalsAgainst);
		
		assertEquals((goalsFor - goalsAgainst), arsenal.getGoalDifference());
	}
	
	@Test
	public void testGetPoints() {
		int won = 8;
		int drawn = 2;
		int lost = 0;
		int goalsFor = 20;
		int goalsAgainst = 5;
		
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal", won, drawn, lost, goalsFor, goalsAgainst);
		
		assertEquals((won * LeagueTableEntry.POINTS_FOR_WIN + drawn * LeagueTableEntry.POINTS_FOR_DRAW + lost * LeagueTableEntry.POINTS_FOR_LOSS), arsenal.getPoints());
	}
	
	@Test
	public void testPlayedHomeMatch() {
		// Given
		
		int won = 8;
		int drawn = 2;
		int lost = 0;
		int goalsFor = 20;
		int goalsAgainst = 5;
		
		LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal", won, drawn, lost, goalsFor, goalsAgainst);
		int pointsBefore = arsenal.getPoints();
		
		Match match = new Match("Arsenal", "Tottenham Hotspur", 3, 1);
		
		// When
		
		arsenal.playedHomeMatch(match);
		
		// Then
		
		assertEquals(drawn + 0, arsenal.getDrawn());
		assertEquals(goalsFor - goalsAgainst + 3 - 1, arsenal.getGoalDifference());
		assertEquals(goalsAgainst + 1, arsenal.getGoalsAgainst());
		assertEquals(goalsFor + 3, arsenal.getGoalsFor());
		assertEquals(lost + 0, arsenal.getLost());
		assertEquals(won + drawn + lost + 1, arsenal.getPlayed());
		assertEquals(pointsBefore + LeagueTableEntry.POINTS_FOR_WIN, arsenal.getPoints());
		assertEquals(won + 1, arsenal.getWon());
	}
	
	@Test
	public void testPlayedAwayMatch() {
		// Given
		
		int won = 0;
		int drawn = 3;
		int lost = 7;
		int goalsFor = 8;
		int goalsAgainst = 25;
		
		LeagueTableEntry spurs = new LeagueTableEntry("Tottenham Hotspur", won, drawn, lost, goalsFor, goalsAgainst);
		int pointsBefore = spurs.getPoints();
		
		Match match = new Match("Arsenal", "Tottenham Hotspur", 3, 1);
		
		// When
		
		spurs.playedAwayMatch(match);
		
		// Then
		
		assertEquals(drawn + 0, spurs.getDrawn());
		assertEquals(goalsFor - goalsAgainst + 1 - 3, spurs.getGoalDifference());
		assertEquals(goalsAgainst + 3, spurs.getGoalsAgainst());
		assertEquals(goalsFor + 1, spurs.getGoalsFor());
		assertEquals(lost + 1, spurs.getLost());
		assertEquals(won + drawn + lost + 1, spurs.getPlayed());
		assertEquals(pointsBefore + LeagueTableEntry.POINTS_FOR_LOSS, spurs.getPoints());
		assertEquals(won + 0, spurs.getWon());
	}

}
