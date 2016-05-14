package ru.javalabs.lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Lab4 {
	public static void main(String[] args) {

		// пути к файлам
		String path1 = "file1_lab4.txt";
		String path2 = "file2_lab4.txt";
		String path3 = "file3_lab4.txt";

		try {
			workWithFiles(path1, path2, path3);
			System.out.println("Закончена работа с файлами!");
		} catch (IOException e) {
			System.out.println("Проблемы с файлами!");
		}
	}

	/**
	 * Ожидаем 3 файла, 1 с исходными данными. Если 2 и 3 не существуют -
	 * создадим их и запишем преобразованную информацию из 1 файла.
	 * 
	 * @param path1
	 * @param path2
	 * @param path3
	 * @throws IOException
	 */
	public static void workWithFiles(String path1, String path2, String path3) throws IOException {
		// исходный файл
		File file = new File(path1);
		// результирующие
		File file2 = new File(path2);
		File file3 = new File(path3);
		// создаем файлы если не существуют
		if (!file2.exists())
			file2.createNewFile();
		if (!file3.exists())
			file3.createNewFile();

		// список строк из 1 файла
		List<String> listOfStrings = new ArrayList<>();

		// считываем из файла в список
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				listOfStrings.add(line);
			}
		}

		// записываем в 2 и 3 файлы
		try (Writer writer2File = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path2), "utf-8"));
				Writer writer3File = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path3), "utf-8"))) {

			// записываем в 2 файл в обратном порядке строки
			for (int i = listOfStrings.size() - 1; i >= 0; i--) {
				writer2File.write(listOfStrings.get(i));
				writer2File.write(System.getProperty("line.separator"));
			}

			// записываем в 3 файл исходную и преобразованную информацию
			for (String string : listOfStrings) {
				writer3File.write(string);
				writer3File.write(System.getProperty("line.separator"));
			}
			for (int i = listOfStrings.size() - 1; i >= 0; i--) {
				writer3File.write(listOfStrings.get(i));
				writer3File.write(System.getProperty("line.separator"));
			}
		}

	}

}
