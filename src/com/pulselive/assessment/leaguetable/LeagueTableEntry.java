/*
 * Copyright URL
 */
package com.pulselive.assessment.leaguetable;

import static com.pulselive.assessment.util.ParamUtils.checkGoals;
import static com.pulselive.assessment.util.ParamUtils.checkNotNull;
import static com.pulselive.assessment.util.ParamUtils.checkNumber;

/**
 * This class represents the league table entry, LeagueTableEntry objects are sorted by points, goal difference, goals for and then team names. 
 * The normal rules for scoring points apply.
 * TODO refactor to remove deprecated setters to reflect rationalised parameters and introduction of atomic update methods {@link LeagueTableEntry#playedAwayMatch(Match)} and {@code LeagueTableEntry#playedHomeMatch(Match)}
 * 
 * @author Paul Parlett
 *
 */
public class LeagueTableEntry implements Comparable<LeagueTableEntry> {
	
	/**
	 * Number of points for a win, assume English football league rules.
	 */
	static final int POINTS_FOR_WIN = 3;
	
	/**
	 * Number of points for a draw, assume English football league rules.
	 */
	static final int POINTS_FOR_DRAW = 1;
	
	/**
	 * Number of points for a loss, assume English football league rules.
	 */
	static final int POINTS_FOR_LOSS = 0;
	
	private final String teamName;
	private int won;
	private int drawn;
	private int lost;
	private int goalsFor;
	private int goalsAgainst;

	/**
	 * Construct a LeagueTableEntry object, useful for testing.
	 * @param teamName The name of the team
	 * @param played Total number of games played
	 * @param won Number of games won
	 * @param drawn Number of games drawn
	 * @param lost Number of games lost
	 * @param goalsFor Total number of goals scored by the team in all matches
	 * @param goalsAgainst Total number of goals conceded by the team in all matches
	 * @param goalDifference The difference between the total number of goals scored and total number of goals conceded
	 * @param points The league points awarded to the team
	 * TODO remove from public API and only use for testing
	 */
	// @VisibleForTesting TODO use Guava
	public LeagueTableEntry(final String teamName, final int played, final int won, final int drawn, final int lost, final int goalsFor, final int goalsAgainst, final int goalDifference, final int points) {
		this.teamName = checkNotNull(teamName);
		this.won = checkNumber(won);
		this.drawn = checkNumber(drawn);
		this.lost = checkNumber(lost);
		this.goalsFor = checkGoals(goalsFor);
		this.goalsAgainst = checkGoals(goalsAgainst);
	}

	/**
	 * Construct a LeagueTableEntry object.
	 * @param teamName The name of the team
	 */
	public LeagueTableEntry(final String teamName) {
		this(teamName, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	/**
	 * Construct a LeagueTableEntry object, useful for testing.
	 * @param teamName The name of the team
	 * @param won Number of games won
	 * @param drawn Number of games drawn
	 * @param lost Number of games lost
	 * @param goalsFor Total number of goals scored by the team in all matches
	 * @param goalsAgainst Total number of goals conceded by the team in all matches
	 */
	// @VisibleForTesting TODO use Guava
	LeagueTableEntry(final String teamName, final int won, final int drawn, final int lost, final int goalsFor, final int goalsAgainst) {
		this.teamName = checkNotNull(teamName);
		this.won = checkNumber(won);
		this.drawn = checkNumber(drawn);
		this.lost = checkNumber(lost);
		this.goalsFor = checkGoals(goalsFor);
		this.goalsAgainst = checkGoals(goalsAgainst);
	}

	/**
	 * Construct a LeagueTableEntry object, useful for testing.
	 * @param teamName The name of the team
	 * @param goalsFor Total number of goals scored by the team in all matches
	 * @param goalsAgainst Total number of goals conceded by the team in all matches
	 * @param points The league points awarded to the team
	 */
	// @VisibleForTesting TODO use Guava
	LeagueTableEntry(final String teamName, final int goalsFor, final int goalDifference, final int points) {
		this.teamName = teamName;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsFor - goalDifference;
		
		// this is used for testing only
		this.won = points / POINTS_FOR_WIN;
		this.drawn = points % POINTS_FOR_WIN;
	}

	public String getTeamName() {
		return teamName;

	}

	@Deprecated
	public void setTeamName(String teamName) {
		throw new UnsupportedOperationException("cannot modify team name");
	}

	public int getPlayed() {
		return won + drawn + lost;
	}

	@Deprecated
	public void setPlayed(int played) {
		// ignored
	}

	public int getWon() {
		return won;
	}

	@Deprecated
	public void setWon(int won) {
		this.won = won;
	}

	public int getDrawn() {
		return drawn;
	}

	@Deprecated
	public void setDrawn(int drawn) {
		this.drawn = drawn;
	}

	public int getLost() {
		return lost;
	}

	@Deprecated
	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	@Deprecated
	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	@Deprecated
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getGoalDifference() {
		return goalsFor - goalsAgainst;
	}

	@Deprecated
	public void setGoalDifference(int goalDifference) {
		// ignored
	}

	public int getPoints() {
		return won * POINTS_FOR_WIN + drawn * POINTS_FOR_DRAW + lost * POINTS_FOR_LOSS;
	}

	@Deprecated
	public void setPoints(int points) {
		// ignored
	}
	
	/**
	 * Update this team's league table entry with the result of a home match. 
	 * @param match A completed home match
	 */
	public void playedHomeMatch(final Match match) {
		if (!teamName.equals(match.getHomeTeam())) {
			throw new IllegalArgumentException("not a home match for this team");
		}
		
		switch (match.getResult()) {
		case HOME_WIN:
			won++;
			break;
		case AWAY_WIN:
			lost++;
			break;
		case DRAW:
			drawn++;
			break;
		}
		
		goalsFor += match.getHomeScore();
		goalsAgainst += match.getAwayScore();
	}
	 
	/**
	 * Update this team's league table entry with the result of aa away match. 
	 * @param match A completed away match
	 */
	public void playedAwayMatch(final Match match) {
		if (!teamName.equals(match.getAwayTeam())) {
			throw new IllegalArgumentException("not an away match for this team");
		}
		
		switch (match.getResult()) {
		case HOME_WIN:
			lost++;
			break;
		case AWAY_WIN:
			won++;
			break;
		case DRAW:
			drawn++;
			break;
		}
		
		goalsFor += match.getAwayScore();
		goalsAgainst += match.getHomeScore();
	}
	
	/**
	 * Pretty print this league table entry to the console.
	 */
	public void prettyPrint() {
		System.out.printf("%-20s%-10d%-10d%-10d%-10d%-10d%-10d%-10d%-10d %n", teamName, 
				getPlayed(), won, drawn, lost, goalsFor, goalsAgainst, getGoalDifference(), getPoints());
	}
	
	// CHECKSTYLE:OFF auto-generated
	 
	/**
	 * {@inheritDoc}
	 * Define on attributes points, goalDifference, goalsFor and teamName.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getGoalDifference();
		result = prime * result + goalsFor;
		result = prime * result + getPoints();
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * Define on attributes points, goalDifference, goalsFor and teamName.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeagueTableEntry other = (LeagueTableEntry) obj;
		if (getGoalDifference() != other.getGoalDifference())
			return false;
		if (goalsFor != other.goalsFor)
			return false;
		if (getPoints() != other.getPoints())
			return false;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		return true;
	}
	
	// CHECKSTYLE:ON

	/**
	 * {@inheritDoc}
	 * Define on attributes points, goalDifference, goalsFor and teamName.
	 */
	@Override
	public int compareTo(LeagueTableEntry o) {
		if (getPoints() < o.getPoints()) {
			return -1;
		} else if (getPoints() > o.getPoints()) {
			return 1;
		} else if (getGoalDifference() < o.getGoalDifference()) {
			return -1;
		} else if (getGoalDifference() > o.getGoalDifference()) {
			return 1;
		} else if (goalsFor < o.goalsFor) {
			return -1;
		} else if (goalsFor > o.goalsFor) {
			return 1;
		}
		
		return teamName.compareTo(o.teamName);	// conventional Alphabetical ordering
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "LeagueTableEntry [teamName=" + teamName + ", played=" + getPlayed() + ", won=" + won + ", drawn=" + drawn
				+ ", lost=" + lost + ", goalsFor=" + goalsFor + ", goalsAgainst=" + goalsAgainst + ", goalDifference=" + getGoalDifference() + ", points=" + getPoints() + "]";
	}
	
}