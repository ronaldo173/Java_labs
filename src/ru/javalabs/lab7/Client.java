package ru.javalabs.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		String serverHostname = new String("127.0.0.1");

		System.out.println("Подключение к серверу на порте 9999");

		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket(serverHostname, 9999);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (Exception e) {
			System.err.println("Проблемы при подключениее к: " + serverHostname);
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;

		System.out.print("Введите строку для поиска с помощью сервера: ");
		while ((userInput = stdIn.readLine()) != null) {
			out.println(userInput);
			System.out.println("получил от сервера: ");
			String lineFromServer = "";
			ObjectInputStream inStream = new ObjectInputStream(echoSocket.getInputStream());
			String[] foundedStrings = (String[]) inStream.readObject();

			if (foundedStrings.length == 0) {
				System.out.println("нет предложений.");
			} else {
				for (String string : foundedStrings) {
					System.out.println(string);
				}
			}
			System.out.print("Введите строку для поиска с помощью сервера: ");

		}

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();
	}
}
