package validGenerationApproachesComparison;

import java.util.ArrayList;

import utils.RegularExpression;

public class ApproachesComparison {
	
	public static void main(String[] args) {
		
		if (args.length != 3) {
            System.err.println("Wrong arguments");
            System.exit(1);
        }
		
		// Obtain parameters values
		String generationApproach = String.valueOf(args[0]);
		String regExpKey = String.valueOf(args[1]);
        int stringLenghtLimit = Integer.valueOf(args[2]);
		
        // Get regular expression
        RegularExpression regularExpression = new RegularExpression();
        String regExp = regularExpression.getRegularExp(regExpKey);
        
        if(regExp != null) {
        	if(generationApproach.equalsIgnoreCase("b")) {
            	boundedExhaustiveStats(regExp, stringLenghtLimit);
            }
        	
            if(generationApproach.equalsIgnoreCase("f")) {
            	fieldExhaustiveStats(regExp, stringLenghtLimit);
            }
        }
	}
	
	/**
	 * Execute Bounded Exhaustive Generation Approach
	 * @param regularExp to use in generation
	 * @param limit of generated Strings
	 */
	private static void boundedExhaustiveStats(String regularExp, int limit) {
		System.out.println("Obtaining Bounded Exhaustive Generation Stats");
		ValidInputsGenerator generator = new ValidInputsGenerator(regularExp);
		
		long start = System.currentTimeMillis();
		ArrayList<String> generatedEntries = generator.boundedGeneration(limit);
		long end = System.currentTimeMillis();
		
		System.out.println("Generated entries quantity = "+generatedEntries.size());
		System.out.println("Generation time (seconds) = "+ (end - start) / 1000F);
	}
	
	/**
	 * Execute Field Exhaustive Generation Approach
	 * @param regularExp to use in generation
	 * @param limit of generated Strings
	 */
	private static void fieldExhaustiveStats(String regularExp, int limit) {
		System.out.println("Obtaining Field Exhaustive Generation Stats");
		ValidInputsGenerator generator = new ValidInputsGenerator(regularExp);
		
		long start = System.currentTimeMillis();
		ArrayList<String> generatedEntries = generator.fieldGeneration(limit);
		long end = System.currentTimeMillis();
		
		System.out.println("Generated entries quantity = "+generatedEntries.size());
		System.out.println("Generation time (seconds) = "+ (end - start) / 1000F);
	}
}
