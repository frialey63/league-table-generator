/*
 * Copyright URL
 */
package com.pulselive.assessment.util;

/**
 * This class comprises parameter checking utilities.
 * TODO replace by Guava
 * 
 * @author Paul Parlett
 *
 */
public final class ParamUtils {

	public static String checkNotNull(final String param) {
		if (param == null) {
			throw new IllegalArgumentException("illegal null parameter");
		}
		
		return param;
	}

	public static int checkNumber(final int param) {
		if (param < 0) {
			throw new IllegalArgumentException("illegal negative number");
		}
		
		return param;
	}


	public static int checkScore(final int param) {
		return checkNumber(param);
	}

	public static int checkGoals(final int param) {
		return checkNumber(param);
	}
	
	private ParamUtils() {
		// prevent instantiation
	}
	
}
