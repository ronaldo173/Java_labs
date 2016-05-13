package ru.javalabs.lab3;

import java.io.File;
import java.util.Stack;

public class Lab3 {
	public static void main(String[] args) {

		//файл  - папка
		File folder = new File("C:\\Program Files");
		try {
			Stack<File> stackOfFiles = getStackOfFilesInFolder(folder);
			System.out.println("Список файлов в папке: " + folder);
			for (File file : stackOfFiles) {
				System.out.println(file);
			}
		} catch (Exception e) {
			System.out.println("Не папка была передана в метод!");
		}
	}

	/**
	 * Позволяет получить стек файлов и папок в указанной папке
	 * 
	 * @param file
	 *            ожидает получить папку
	 * @return стек файлов и папок
	 * @throws Exception
	 *             бросаем исключение если передана не папка
	 */
	public static Stack<File> getStackOfFilesInFolder(File folder) throws Exception {
		if (!folder.isDirectory()) {
			throw new Exception("Not a folder!");
		}

		Stack<File> stackOfFiles = new Stack<>();
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			stackOfFiles.add(file);
		}
		return stackOfFiles;
	}

}
