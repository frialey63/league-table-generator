/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.pulselive.assessment.leaguetable.LeagueTable;
import com.pulselive.assessment.leaguetable.LeagueTableEntry;
import com.pulselive.assessment.leaguetable.Match;

/**
 * This class comprises utilities for reading the CSV files containing match results and league tables.
 * TODO simplify using openCSV
 * 
 * @author Paul Parlett
 *
 */
public final class FileUtils {

	private static final String DELIMITER = ",";
	
	private final static Logger LOGGER = Logger.getLogger(FileUtils.class.getName());
	
	/**
	 * @param file The CSV file assumed to be in football-data format
	 * @return The list of matches parsed from the file
	 * @throws IOException The IOException which may occur
	 */
	public static List<Match> readMatchFile(File file) throws IOException {
		final List<Match> result = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			reader.readLine();		// skip first line of column headers
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				try (Scanner dataScanner = new Scanner(line)) {
					dataScanner.useDelimiter(DELIMITER);
					
					String homeTeam = null;
					String awayTeam = null;
					Integer homeScore = null;
					Integer awayScore = null;
					
					int index = 0;
					
					while (dataScanner.hasNext()) {
						if (index == 2) {
							homeTeam = dataScanner.next();
						} else if (index == 3) {
							awayTeam = dataScanner.next();
						} else if (index == 4) {
							homeScore = dataScanner.nextInt();
						} else if (index == 5) {
							awayScore = dataScanner.nextInt();
						} else {
							dataScanner.next();
						}
						
						index++;
					}

					if ((homeTeam != null) && (awayTeam != null) && (homeScore != null) && (awayScore != null)) {
						Match match = new Match(homeTeam, awayTeam, homeScore, awayScore);
						result.add(match);
					} else {
						LOGGER.warning("invalid line in file: " + line);
					}
				}
			}
		}
		
		assert result.size() == LeagueTable.numberOfMatches();
		
		return result;
	}
	
	/**
	 * @param file The CSV file assumed to be in footstats format
	 * @return The list of league table entries parsed from the file
	 * @throws IOException The IOException which may occur
	 */
	public static List<LeagueTableEntry> readLeagueTableFile(File file) throws IOException {
		final List<LeagueTableEntry> result = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			reader.readLine();		// skip first line of column headers
			reader.readLine();		// skip second line of notes
			reader.readLine();		// skip third line of key
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				try (Scanner dataScanner = new Scanner(line)) {
					dataScanner.useDelimiter(DELIMITER);
					
					String teamName = null;
					Integer played = null;
					Integer won = null;
					Integer drawn = null;
					Integer lost = null;
					Integer goalsFor = null;
					Integer goalsAgainst = null;
					Integer goalDifference = null;
					Integer points = null;
					
					int index = 0;
					
					while (dataScanner.hasNext()) {
						if (index == 1) {
							teamName = dataScanner.next();
						} else if (index == 2) {
							played = dataScanner.nextInt();
						} else if (index == 3) {
							won = dataScanner.nextInt();
						} else if (index == 4) {
							drawn = dataScanner.nextInt();
						} else if (index == 5) {
							lost = dataScanner.nextInt();
						} else if (index == 6) {
							goalsFor = dataScanner.nextInt();
						} else if (index == 7) {
							goalsAgainst = dataScanner.nextInt();
						} else if (index == 8) {
							goalDifference = dataScanner.nextInt();
						}  else if (index == 9) {
							points = dataScanner.nextInt();
						} else {
							dataScanner.next();
						}
						
						index++;
					}

					if ((teamName != null) && (played != null) && (won != null) && (drawn != null) && (lost != null) && (goalsFor != null) && (goalsAgainst != null) && (goalDifference != null) && (points != null)) {
						LeagueTableEntry leagueTableEntry = new LeagueTableEntry(teamName, played, won, drawn, lost, goalsFor, goalsAgainst, goalDifference, points);
						result.add(leagueTableEntry);
					} else {
						LOGGER.warning("invalid line in file: " + line);
					}
				}
			}
		}
		
		assert result.size() == LeagueTable.NUMBER_OF_TEAMS;

		return result;
	}
	
	private FileUtils() {
		// prevent instantiation
	}

}