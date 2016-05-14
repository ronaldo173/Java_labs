package ru.javalabs.lab5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
	public static void main(String[] args) throws IOException {
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

		// считываем строки из файла
		String path1 = "file1_lab4.txt";
		List<String> listOfStrings = getStringsFromFile(path1);

		System.out.println("Шлю случайную строку из файла клиенту.....");

		// при получении любого сообщения кроме "end" шлю клиенту рандомную
		// строку
		try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server получил от клиента: " + inputLine);
				String strToClient = listOfStrings.get(new Random().nextInt(listOfStrings.size()));
				out.println(strToClient);

				if (inputLine.equals("end"))
					break;
			}
		}

		clientSocket.close();
		serverSocket.close();
	}

	private static List<String> getStringsFromFile(String path) throws IOException {
		List<String> listOfStrings = new ArrayList<>();
		// считываем из файла в список
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				listOfStrings.add(line);
			}
		}
		return listOfStrings;
	}
}