/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import static com.pulselive.assessment.leaguetable.util.FileUtils.readLeagueTableFile;
import static com.pulselive.assessment.leaguetable.util.FileUtils.readMatchFile;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.pulselive.assessment.leaguetable.util.FileUtils;

/**
 * This class comprises the test cases for the LeagueTable class.
 * 
 * @author Paul Parlett
 *
 */
public class LeagueTableTest {

	/*
	 * Downloaded from http://www.football-data.co.uk/englandm.php
	 */
	private static final File MATCH_FILE = new File("data/english_premier_league_results_2017_2018.csv");

	/*
	 * Downloaded from http://www.footstats.co.uk/index.cfm?task=league_full
	 */
	private static final File TABLE_FILE = new File("data/english_premier_league_table_2017_2018.csv");

	/**
	 * Read the footstats file containing the final league table and construct a reference LeagueTable,
	 * read the football-data containing the results of all matches and construct my LeagueTable,
	 * check that both tables are equal.
	 * 
	 * @throws IOException The IOException which may occur
	 */
	@Test
	public void test() throws IOException {
		LeagueTable referenceTable = new LeagueTable(readLeagueTableFile(TABLE_FILE), 0);
		List<LeagueTableEntry> referenceTableEntries = referenceTable.getTableEntries();
		
		LeagueTable myTable = new LeagueTable(readMatchFile(MATCH_FILE));
		List<LeagueTableEntry> myTableEntries = myTable.getTableEntries();
		
		assertEquals(referenceTableEntries.size(), myTableEntries.size());
		
		for (int i = 0; i < referenceTableEntries.size(); i++) {
			LeagueTableEntry refEntry = referenceTableEntries.get(i);
			LeagueTableEntry myEntry = myTableEntries.get(i);
			
			assertEquals(refEntry, myEntry);
		}
	}

	@Test
	public void prettyPrint() throws IOException {
		LeagueTable referenceTable = new LeagueTable(FileUtils.readLeagueTableFile(TABLE_FILE), 0);
		referenceTable.prettyPrint();
	}
}
