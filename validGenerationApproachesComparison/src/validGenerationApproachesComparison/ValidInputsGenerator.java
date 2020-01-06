package validGenerationApproachesComparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import utils.TransitionsSet;

public class ValidInputsGenerator {

	private String regularExpression;
	private Automaton automata;

	/**
	 * Constructor thats set regular expression
	 * @param re to set resgularExpression
	 */
	public ValidInputsGenerator(String re) {
		regularExpression = re;
	}

	// ------------------------------
	// BOUNDED EXHAUSTIVE GENERATION
	// ------------------------------

	/**
	 * Method to call boundedExhaustiveGeneration with input generation length limit
	 * @param limit of generated Strings length
	 * @return boundedExhaustiveGeneration result
	 */
	public ArrayList<String> boundedGeneration(int limit) {
		if (regularExpression == null) {
			return null;
		} else {
			RegExp regExp = new RegExp(regularExpression);
			automata = regExp.toAutomaton();

			ArrayList<String> arr = new ArrayList<>();
			arr.add("");

			return boundedExhaustiveGeneration(automata.getInitialState(), arr, limit);
		}
	}

	/**
	 * Generate all valid Strings of length less than or equal to input length limit
	 * @param s current state
	 * @param generatedStrings list 
	 * @param limit length of generated Strings
	 * @return valid Strings
	 */
	private ArrayList<String> boundedExhaustiveGeneration(State s, ArrayList<String> generatedStrings, int limit) {
		ArrayList<String> result = new ArrayList<>();
		if (limit == 0) {
			if (s.isAccept()) {
				return generatedStrings;
			} else {
				return new ArrayList<>();
			}
		} else {
			if (s.isAccept()) {
				result.addAll(generatedStrings);
			}
			// Group transitions with same destination State
			LinkedList<TransitionsSet> listTS = groupTransWithSameDest(s);
			for (TransitionsSet t : listTS) {

				ArrayList<String> symbs = new ArrayList<>();
				for (String sym : t.getSymbols()) {
					symbs.add(sym);
				}

				ArrayList<String> withAddedAtLast = addAtLast(symbs, generatedStrings);
				result.addAll(boundedExhaustiveGeneration(t.getDest(), withAddedAtLast, limit - 1));

			}

			return result;
		}
	}

	/**
	 * Group transitions with same destination State
	 * 
	 * @param origin state
	 * @return a LinkedList with set of transitions with same destination State
	 */
	private LinkedList<TransitionsSet> groupTransWithSameDest(State origin) {

		LinkedList<TransitionsSet> listTS = new LinkedList<>();
		HashMap<State, TransitionsSet> trSets = new HashMap<State, TransitionsSet>();
		State destinationState;

		// Group transitions with same destination state in HashMap
		for (Transition t : origin.getTransitions()) {

			destinationState = t.getDest();

			LinkedList<String> currentSymbols = new LinkedList<>();
			for (int n = t.getMin(); n <= t.getMax(); n++) {
				currentSymbols.add(Character.toString((char) n));
			}

			// If HashMap contains destination state as key only
			// add currenSymbols to TransitionSet obtained
			if (trSets.containsKey(destinationState)) {
				trSets.get(destinationState).addSymbols(currentSymbols);
			} else {
				TransitionsSet ts = new TransitionsSet();
				ts.setDest(destinationState);
				ts.addSymbols(currentSymbols);
				trSets.put(destinationState, ts);
			}

		}

		// Add TransitionSets to LinkedList
		for (TransitionsSet ts : trSets.values()) {
			listTS.add(ts);
		}

		return listTS;
	}

	/**
	 * For every input production add symbols at the end
	 * @param symbs to add at the end of every production
	 * @param prods to add symbols
	 * @return list of productions with symbols added at the end
	 */
	private ArrayList<String> addAtLast(ArrayList<String> symbs, ArrayList<String> prods) {

		ArrayList<String> res = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		for (String production : prods) {
			for (String symb : symbs) {
				sb.append(production);
				sb.append(symb);
				res.add(sb.toString());
				sb.setLength(0);
			}
		}

		return res;
	}

	// ----------------------------
	// FIELD EXHAUSTIVE GENERATION
	// ----------------------------

	/**
	 * Method to call fieldExhaustiveGeneration with input generation length limit
	 * @param limit of generated Strings length
	 * @return fieldExhaustiveGeneration result
	 */
	public ArrayList<String> fieldGeneration(int limit) {
		if (regularExpression == null) {
			return null;
		} else {
			RegExp regExp = new RegExp(regularExpression);
			automata = regExp.toAutomaton();

			ArrayList<String> arr = new ArrayList<>();
			arr.add("");
			return fieldExhaustiveGeneration(automata.getInitialState(), arr, limit);
		}
	}

	/**
	 * Generate valid Strings of length less than or equal to input length limit
	 * @param s current state
	 * @param generatedStrings list 
	 * @param limit length of generated Strings
	 * @return valid Strings
	 */
	private ArrayList<String> fieldExhaustiveGeneration(State s, ArrayList<String> generatedStrings, int limit) {
		ArrayList<String> result = new ArrayList<>();
		if (limit == 0) {
			if (s.isAccept()) {
				return generatedStrings;
			} else {
				return new ArrayList<>();
			}
		} else {
			if (s.isAccept()) {
				result.addAll(generatedStrings);
			}

			// Group transitions with same destination State
			LinkedList<TransitionsSet> listTS = groupTransWithSameDest(s);

			for (TransitionsSet t : listTS) {

				ArrayList<String> currentSymbols = new ArrayList<>();
				for (String sym : t.getSymbols()) {
					currentSymbols.add(sym);
				}

				ArrayList<String> aux = (ArrayList<String>) generatedStrings.clone();
				aux = fieldRandomUnion(aux, currentSymbols);

				ArrayList<String> rec = fieldExhaustiveGeneration(t.getDest(), aux, limit - 1);
				if (rec != null) {
					result.addAll(rec);
				}
			}
		}
		return result;
	}

	/**
	 * Concatenate every input symbol at the end of Strings in generatedStrings randomly 
	 * 
	 * fieldRandomUnion( {"a","b","c"}, {"abb","bbb","cbb","dbb"}) could produce for
	 * example {"babb","cbbb","acbb","bdbb"}
	 * 
	 * @param symbs to add at the end of generatedStrings
	 * @param generatedStrings to add symbols
	 * @return list with new generated Strings
	 */
	private ArrayList<String> fieldRandomUnion(ArrayList<String> symbs, ArrayList<String> generatedStrings) {
		if (symbs == null || generatedStrings == null) {
			return null;
		}

		Collections.shuffle(generatedStrings);
		Collections.shuffle(symbs);

		ArrayList<String> result = new ArrayList<>();

		boolean symbsSizeGreater = false;
		if (symbs.size() > generatedStrings.size()) {
			symbsSizeGreater = true;
		}

		if (symbsSizeGreater) {
			int j = 0;
			for (int i = 0; i < symbs.size(); i++) {
				result.add(symbs.get(i) + generatedStrings.get(j));
				j++;
				if (j == generatedStrings.size()) {
					j = 0;
				}
			}
		} else {
			int j = 0;
			for (int i = 0; i < generatedStrings.size(); i++) {
				result.add(symbs.get(j) + generatedStrings.get(i));
				j++;
				if (j == symbs.size()) {
					j = 0;
				}
			}
		}

		return result;

	}
}
