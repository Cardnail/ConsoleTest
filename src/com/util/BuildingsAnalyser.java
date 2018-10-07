package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * Вывести количество зданий построенных во всех годах данной статистики
 *     0 		1		 2	    3          4            5         6        7       8           9
 * area_id,full_address,city,address,street_prefix,street_name,building,house_id,area_name,house_year
 */
public class BuildingsAnalyser {

	private static String path = "data/text.txt";

	public static void main(String[] args) {
		run();
	}

	public static void run() {
		// прочитать текст из файла в строку
		File file = new File(path);
		String text = readFile(file);

		// разделить текст на строки
		String[] lines = text.split("\r\n");

		// разделить текст на слова
		// и записать в список набор слов для каждой строки
		List<String[]> lineWords = splitByWords(lines);

		Map<String, Integer> map = countHousesInEveryYear(lineWords);

		show(map);
	}

	private static void show(Map<String, Integer> map) {
		// вывод данных на экран
		for (String year : map.keySet()) {
			System.out.println("Year = " + year + " 	count = " + map.get(year));
		}
	}

	private static List<String[]> splitByWords(String[] lines) {
		List<String[]> lineWords = new ArrayList<>();
		for (String line : lines) {
			String[] words = line.split(",");

			if (isCorrectYear(words[9])) {
				lineWords.add(words);
			}
		}
		return lineWords;
	}

	private static Map<String, Integer> countHousesInEveryYear(List<String[]> lineWords) {
		// посчитать количество элементов с годом house_year
		Map<String, Integer> map = new TreeMap<>();
		for (String[] words : lineWords) {
			if (map.containsKey(words[9])) {
				Integer count = map.get(words[9]);
				count++;
				map.put(words[9], count);
			} else {
				map.put(words[9], 1);
			}
		}
		return map;
	}

	/*
	 * Чтение текста из файла
	 */
	public static String readFile(File file) {
		String text = "";
		try (FileInputStream stream = new FileInputStream(file)) {

			int length = stream.available();
			byte[] data = new byte[length];
			stream.read(data);
			text = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/*
	 * Проверка корректности года
	 */
	public static boolean isCorrectYear(String year) {
		if (year.matches("^\\d{4}")) {
			Integer n = Integer.parseInt(year);
			if (n > 1000 && n < 2018) {
				return true;
			}
		}
		return false;
	}
}
