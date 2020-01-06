package utils;

import java.util.HashMap;
import java.util.Map;

public class RegularExpression {

	private static String DIGITS = "([0-9]([0-9]*[0-9])?)";

	private final String DECIMAL_LITERAL = "[+-]?(0|[1-9])([0-9]*)?[lL]?";
	private final String HEX_LITERAL = "[+-]?0[xX][0-9a-fA-F][0-9a-fA-F]*[lL]?";
	private final String FLOATING_POINT_LITERAL = "[+-]?(" + DIGITS + "[.]" + DIGITS + "?|[.]" + DIGITS + ")([eE][+-]?"
			+ DIGITS + ")?[fFdD]?" + "|" + DIGITS + "(([eE][+-]?" + DIGITS + ")[fFdD]?|[fFdD])";

	private Map<String, String> regExps = new HashMap<String, String>();

	/**
	 * Default constructor that fill map with available regular expressions
	 */
	public RegularExpression() {
		// Fill regExps map with available regular expressions
		regExps.put("D", DECIMAL_LITERAL);
		regExps.put("H", HEX_LITERAL);
		regExps.put("F", FLOATING_POINT_LITERAL);
	}

	/**
	 * Obtain regular expression by map key
	 * @param regExpKey to use as key
	 * @return regular expression value
	 */
	public String getRegularExp(String regExpKey) {
		return regExps.get(regExpKey.toUpperCase());
	}
}
