/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import static com.pulselive.assessment.util.ParamUtils.checkNotNull;
import static com.pulselive.assessment.util.ParamUtils.checkScore;

/**
 * This class represents a (completed) match played between the home and away teams in the league.
 * 
 * @author Paul Parlett
 *
 */
public class Match {
	
	public enum Result { HOME_WIN, DRAW, AWAY_WIN }
	
	private final String homeTeam;
	private final String awayTeam;
	private final int homeScore;
	private final int awayScore;

	/**
	 * Construct a Match object.
	 * @param homeTeam The name of the home team
	 * @param awayTeam The name of the away team
	 * @param homeScore The number of goals scored by home team in the match
	 * @param awayScore The number of goals scored by away team in the match
	 */
	public Match(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
		this.homeTeam = checkNotNull(homeTeam);
		this.awayTeam = checkNotNull(awayTeam);
		this.homeScore = checkScore(homeScore);
		this.awayScore = checkScore(awayScore);
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}
	
	public Result getResult() {
		if (homeScore > awayScore) {
			return Result.HOME_WIN;
		} else if (awayScore > homeScore) {
			return Result.AWAY_WIN;
		}
		
		return Result.DRAW;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String toString() {
		return "Match [homeTeam=" + homeTeam + ", awayTeam=" + awayTeam + ", homeScore=" + homeScore + ", awayScore=" + awayScore + ", getResult()=" + getResult() + "]";
	}
	
	
}