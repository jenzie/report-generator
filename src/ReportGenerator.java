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

public class ReportGenerator {
	private Scanner input;
	private PrintWriter out;
	private ArrayList<String> data;

	public ReportGenerator(String inputFile, String outputFile) {
		try {
			input = new Scanner(new File(inputFile));
		} catch (FileNotFoundException fnfe) {
			System.err.println(
				"Usage: java report-generator.java INPUT.txt OUTPUT.txt");
			System.exit(0);
		}
		try {
			out = new PrintWriter(new FileWriter(new File(outputFile)));
		} catch (IOException ioe) {
			System.err.println("IOException: " +
					"Could not create print writer for /results/" + outputFile);
			System.exit(0);
		}
		parseFile();
		// System.out.println("Report written to /results/" + outputFile);
	}

	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println(
				"Usage: java report-generator.java INPUT.txt OUTPUT.txt");
			System.exit(0);
		}
		new ReportGenerator(args[0], args[1]);
	}

	private void parseFile() {
		String result = "";
		this.data = new ArrayList<String>();

		while(input.hasNextLine())
			this.data.add(input.nextLine());

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

		result += "I. Result.\n\n";
		result += "II. Time.\n";
		result += parseTimeCalculations(data.subList(0, 3));
		result += "III. Base, Average, and Peak Memory.\n";
		result += parseMemoryCalculations(data.subList(3, data.size()));
		writeResults(result);
	}

	private String parseTimeCalculations(List<String> times) {
		String results = " ";
		String divider = String.format("%-60s", "|").replace(' ', '-') + "|\n";

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
		return results;
	}

	private String parseMemoryCalculations(List<String> memories) {
		String results = " ";
		String divider = String.format("%-60s", "|").replace(' ', '-') + "|\n";

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
		return results;
	}

	private void writeResults(String results) {
		out.flush();
		out.write(results);
		out.flush();
		out.close();
	}
}