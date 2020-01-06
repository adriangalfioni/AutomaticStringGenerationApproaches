package utils;

import java.util.LinkedList;

import dk.brics.automaton.State;
import dk.brics.automaton.Transition;

public class TransitionsSet {

	private State dest;
	private LinkedList<String> symbols;
	
	/**
	 * Default constructor
	 */
	public TransitionsSet() {
		symbols = new LinkedList<>(); 
	}
	
	/**
	 * Add symbols in list
	 * @param symbolsToAdd 
	 */
	public void addSymbols(LinkedList<String> symbolsToAdd) {
		symbols.addAll(symbolsToAdd);
	}
	
	
	/**
	 * Obtain destination state
	 * @return destination state
	 */
	public State getDest() {
		return dest;
	}
	

	/**
	 * Set destination state
	 * @param newDest to set destination state
	 */
	public void setDest(State newDest) {
		dest = newDest;
	}
	
	/**
	 * Obtain symbols list
	 * @return symbols list
	 */
	public LinkedList<String> getSymbols() {
		return symbols;
	}

	/**
	 * Set symbols list
	 * @param symbols list
	 */
	public void setSymbols(LinkedList<String> symbols) {
		this.symbols = symbols;
	}
	
}
