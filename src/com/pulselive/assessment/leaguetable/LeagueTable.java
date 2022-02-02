/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class represents the league table, it can take a list of completed matches and produce a sorted list of LeagueTableEntry objects.
 * 
 * @author Paul Parlett
 *
 */
public class LeagueTable {
	
	/**
	 * The number of teams in the league, assume English Premier League.
	 * TODO could be parameterised or injected for more generality
	 */
	public final static int NUMBER_OF_TEAMS = 20;
	
	private final static Logger LOGGER = Logger.getLogger(LeagueTable.class.getName());
	
	/**
	 * @return The number of matches, each team plays every other team home and away
	 */
	public static final int numberOfMatches() {
		return NUMBER_OF_TEAMS * (NUMBER_OF_TEAMS - 1);
	}
	
	private Map<String, LeagueTableEntry> map = new HashMap<>();
	
	/**
	 * Construct a LeagueTable from a list of completed matches.
	 * @param matches
	 */
	public LeagueTable(final List<Match> matches) {
		matches.forEach(match -> {
			updateHomeTeamWithMatchResult(match);
			
			updateAwayTeamWithMatchResult(match);
		});
	}
	
	/**
	 * Construct a LeagueTable from a list of league table entries, useful for testing.
	 * @param tableEntries The list of LeagueTableEntry objects
	 * @param ignored A dummy parameter to avoid the "Erasure of method is the same as another method in type" compilation error
	 */
	// @VisibleForTesting TODO use Guava
	LeagueTable(final List<LeagueTableEntry> tableEntries, final int ignored) {
		map = tableEntries.stream().collect(Collectors.toMap(LeagueTableEntry::getTeamName, Function.identity()));
	}
 
	private void updateAwayTeamWithMatchResult(Match match) {
		String awayTeam = match.getAwayTeam();
		
		LOGGER.fine(() -> "updating the away team with match results " + awayTeam);
		
		LeagueTableEntry awayTeamEntry = map.computeIfAbsent(awayTeam, k -> new LeagueTableEntry(k));
		
		awayTeamEntry.playedAwayMatch(match);
	}

	private void updateHomeTeamWithMatchResult(Match match) {
		String homeTeam = match.getHomeTeam();
		
		LOGGER.fine(() -> "updating the home team with match results " + homeTeam);
		
		LeagueTableEntry homeTeamEntry = map.computeIfAbsent(homeTeam, k -> new LeagueTableEntry(k));
		
		homeTeamEntry.playedHomeMatch(match);
	}

	/**
	 * Get the ordered list of league table entries for this league table.
	 **
	 * @return The ordered list of league table entries
	 */
	public List<LeagueTableEntry> getTableEntries() {
		List<LeagueTableEntry> result = new ArrayList<>(map.values());
		
		LOGGER.fine(() -> "sorting the league table entries");
		
		Collections.sort(result, Comparator.reverseOrder());
		
		return result;
	}
	
	/**
	 * Pretty print this league table to the console.
	 */
	public void prettyPrint() {
		System.out.printf("Team                Played    Won       Drawn     Lost      For       Against   GD        Points    %n");
		
		getTableEntries().forEach(tableEntry -> tableEntry.prettyPrint());
	}

}
