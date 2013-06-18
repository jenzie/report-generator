/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.17.2013
 * language: Java
 * project: report_generator
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ReportGenerator.
 * Generates reports for the xml-parser.
 */
public class ReportGenerator {
	private Scanner input; // reads input
	private PrintWriter out; // writes output

	/**
	 * Constructor.
	 * Checks if arguments are valid files; exits, if false.
	 * @param inputFile file with input data
	 * @param outputFile file to write results to
	 */
	public ReportGenerator(String inputFile, String outputFile) {

		// create reader for input file
		try {
			input = new Scanner(new File(inputFile));
		} catch (FileNotFoundException fnfe) {
			System.err.println(
				"Usage: java report-generator.java INPUT.txt OUTPUT.txt");
			System.exit(0);
		}

		// create writer for output file
		try {
			out = new PrintWriter(new FileWriter(new File(outputFile)));
		} catch (IOException ioe) {
			System.err.println("IOException: " +
					"Could not create print writer for /results/" + outputFile);
			System.exit(0);
		}

		// produce and write output
		parseFile();
	}

	/**
	 * Checks the argument count before proceeding with the program.
	 * @param args the input/output file directories and names.
	 */
	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println(
				"Usage: java report-generator.java INPUT.txt OUTPUT.txt");
			System.exit(0);
		}
		new ReportGenerator(args[0], args[1]);
	}

	/**
	 * Extracts data from the input file and calls functions to get the result.
	 */
	private void parseFile() {
		String result = "";
		ArrayList<String> data = new ArrayList<String>();

		// extract data from file by line
		while(input.hasNextLine())
			data.add(input.nextLine());

		// ensure validity of input file
		if(data.size() < 12) {
			System.err.println(
				"Error: Invalid input file.\n" +
					"Example:\n" +
					"\ttime\n\ttime\n\ttime\n" +
					"\tlowest memory\n\taverage memory\n\thighest memory\n" +
					"\tlowest memory\n\taverage memory\n\thighest memory\n" +
					"\tlowest memory\n\taverage memory\n\thighest memory\n");
			System.exit(0);
		}

		// get results
		result += "I. Result.\n\n";
		result += "II. Time.\n";
		result += parseTimeCalculations(data.subList(0, 3));
		result += "III. Base, Average, and Peak Memory.\n";
		result += parseMemoryCalculations(data.subList(3, data.size()));

		// write results
		writeResults(result);
	}

	/**
	 * Return string for results from time calculation as a table.
	 * @param times input data to be used in the results.
	 */
	private String parseTimeCalculations(List<String> times) {
		String results = " ";

		// divides each row in the table
		String divider = String.format("%-60s", "|").replace(' ', '-') + "|\n";

		// construct the table as a string
		results += String.format("%60s", "\n").replace(' ', '-')
			+ String.format("%-60s", "| Time (milliseconds)")
				+ "|\n" + divider
			+ String.format("%-60s", "| Naive Strategy  | " + times.get(0))
				+ "|\n" + divider
			+ String.format("%-60s", "| Random Strategy | " + times.get(1))
				+ "|\n" + divider
			+ String.format("%-60s", "| Loop Strategy   | " + times.get(2))
				+ "|\n "
			+ String.format("%60s", "\n").replace(' ', '-');

		// return the table
		return results;
	}

	/**
	 * Return string for results from the memory calculations as a table.
	 * @param memories input data to be used in the results.
	 */
	private String parseMemoryCalculations(List<String> memories) {
		String results = " ";

		// divides each row in the table
		String divider = String.format("%-60s", "|").replace(' ', '-') + "|\n";

		// construct the table as a string
		results += String.format("%60s", "\n").replace(' ', '-')
			+ String.format("%-60s", "| Memory (megaflops)")
				+ "|\n" + divider + "|"
			+ String.format("%59s", "| Base        | Average     | Peak        ")
				+ "|\n" + divider
			+ String.format("%-18s", "| Naive Strategy") + "| "
				+ String.format("%-12s", memories.get(0)) + "| "
				+ String.format("%-12s", memories.get(1)) + "| "
				+ String.format("%-12s", memories.get(2))
					+ "|\n" + divider
			+ String.format("%-18s", "| Random Strategy") + "| "
				+ String.format("%-12s", memories.get(3)) + "| "
				+ String.format("%-12s", memories.get(4)) + "| "
				+ String.format("%-12s", memories.get(5))
					+ "|\n" + divider
			+ String.format("%-18s", "| Loop Strategy") + "| "
				+ String.format("%-12s", memories.get(6)) + "| "
				+ String.format("%-12s", memories.get(7)) + "| "
				+ String.format("%-12s", memories.get(8))
					+ "|\n "
			+ String.format("%60s", "\n").replace(' ', '-');

		// return the table
		return results;
	}

	/**
	 * Writes the output string to the output file specified from the
	 * arguments used to run the program.
	 * @param results output to write to file.
	 */
	private void writeResults(String results) {
		out.flush();
		out.write(results);
		out.flush();
		out.close();
	}
}