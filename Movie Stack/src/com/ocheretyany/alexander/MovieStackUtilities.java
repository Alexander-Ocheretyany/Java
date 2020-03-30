package com.ocheretyany.alexander;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MovieStackUtilities {
	
	private static String DEFAULT_ENCODING = "UTF-8";
	private static String DB_PATH = "./movies.db";
	private static String TMP_DB_PATH = "./tmp.db";
	
	private static File getFile() throws IOException {
		
		File file = new File(DB_PATH);
		
		if(!file.exists()) {	
			file.createNewFile();	
		}
		
		return file;
		
	}
	
	private static int getRandomIdUpToLimit(int limit) {
		
		Random randomGenerator = new Random();
		
		return randomGenerator.nextInt(limit);
		
	}
	
	private static int countNumberOfTitles(File file) throws FileNotFoundException {
		
		Scanner reader = new Scanner(file, DEFAULT_ENCODING);
		int counter = 0;
		
		while(reader.hasNextLine()) {
			reader.nextLine();
			++counter;
		}
		
		reader.close();
		
		return counter;
	}
	
	public static String getMovieTitle() throws IOException, NoMovieTitlesException {
		
		File file = getFile();
		int numOfTitles = countNumberOfTitles(file);
		String movieTitle = null;
		
		if(numOfTitles != 0) {
			Scanner reader = new Scanner(file, DEFAULT_ENCODING);
			int randId = getRandomIdUpToLimit(numOfTitles);
			
			while(randId != 0) {
				reader.nextLine();
				--randId;
			}
			
			movieTitle = reader.nextLine();
			reader.close();
		} else {	
			throw new NoMovieTitlesException();
		}		
		
		return movieTitle;
	}
	
	public static void removeMovieTitle(String title) throws NoMovieTitlesException, IOException {
		
		File file = getFile();
		File tmpFile = new File(TMP_DB_PATH);
		
		Scanner reader = new Scanner(file, DEFAULT_ENCODING);
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(tmpFile), StandardCharsets.UTF_8);
		
		while(reader.hasNextLine()) {
			
			String str = reader.nextLine();
			
			if(str.equals(title)) {
				continue;
			}
			
			writer.write(str + '\n');
			
		}
		
		reader.close();
		writer.close();
		
		file.delete();
		tmpFile.renameTo(file);
		
		System.exit(0);
	}

	public static void addMovieTitle(String movieTitle) throws IOException {
		
		File file = getFile();
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
		writer.write(movieTitle + '\n');
		writer.close();
		
	}
	
	public static void reportErrorAndFinish() {
		JOptionPane.showMessageDialog(null, "Something went wrong!", "Critical error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
