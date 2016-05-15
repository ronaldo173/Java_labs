package ru.javalabs.lab7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
	/*
	 * Имя файла с текстом.
	 */
	private String fileWithText = "file_lab7.txt";

	/*
	 * Ищем все предложения в которые входит переданное слово
	 */
	public String[] findInTextSentenceByWord(String word) throws Exception {
		List<String> listOfFoundedSentences = new ArrayList<>();

		StringBuilder stringAllText = getStringFromFile();
		String[] sentences = stringAllText.toString().split("\\.");
		String regexForCheckSentence = ".*\\b" + word + "\\b.*";

		for (String string : sentences) {
			if (string.matches(regexForCheckSentence)) {
				listOfFoundedSentences.add(string);
			}
		}

		return listOfFoundedSentences.toArray(new String[listOfFoundedSentences.size()]);
	}

	/*
	 * Получаем весь текст из файла.
	 */
	private StringBuilder getStringFromFile() throws Exception {
		StringBuilder stringAllText = new StringBuilder();
		File file = new File(fileWithText);
		if (!file.exists()) {
			throw new Exception("No file with text");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				stringAllText.append(line);
			}
		}
		return stringAllText;
	}

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;

		try {
			// создаем сервер на порте
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 9999.");
			return;
		}

		Socket clientSocket = null;
		System.out.println("Жду подключений.....");

		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.err.println("Не подключился");
			return;
		}

		System.out.println("Соединение установлено");

		// при получении строки от клиента ищу её в тексте и возвращаю клиенту
		// найденые предложения
		try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server получил от клиента: " + inputLine);
				System.out.println("Ищу в тексте слово...");
				String[] findInTextSentenceByWord = new Server().findInTextSentenceByWord(inputLine);
				if (findInTextSentenceByWord.length == 0) {
					System.out.println("no word, send to client answer...");
				}
				// записываю массив предложений в поток клиенту

				ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
				outStream.writeObject(findInTextSentenceByWord);

				if (inputLine.equals("end"))
					break;
			}
		}

		clientSocket.close();
		serverSocket.close();
	}
}
