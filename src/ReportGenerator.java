/**
 * @author: Jenny Zhen; jenny.zhen@rit.edu
 * date: 06.17.2013
 * language: Java
 * project: report_generator
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportGenerator {
	private Scanner input;
	private PrintWriter out;
	private ArrayList<String> data;

	public ReportGenerator(String file) {
		try {
			input = new Scanner(new File(file)); // directory, file
		} catch (FileNotFoundException e) {
			System.err.println("Usage: java report-generator.java INPUT.txt");
			System.exit(0);
		}
		parseFile();
	}

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Usage: java report-generator.java INPUT.txt");
			System.exit(0);
		}
		new ReportGenerator(args[0]);
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

		parseTimeCalculations(data.get(0), data.get(1), data.get(2));

		for(int i = 3; i < 12; i++)
			result += parseMemoryCalculations(data.get(i));
	}

	private String parseTimeCalculations(
			String time1, String time2, String time3) {
		String results;

		results = "------------------------------------------------------------\n";
		results += "| Time                                                     |\n";
	}
}