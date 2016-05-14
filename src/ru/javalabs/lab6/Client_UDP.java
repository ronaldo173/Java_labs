package ru.javalabs.lab6;

import java.io.*;
import java.net.*;

class Client_UDP {
	public static void main(String args[]) throws Exception {
		DatagramSocket clientSocket = null;
		try {
			String serverHostname = new String("127.0.0.1");
			clientSocket = new DatagramSocket();

			InetAddress IPAddress = InetAddress.getByName(serverHostname);
			System.out.println("Подключаюсь к " + IPAddress + "на порт 9999");

			byte[] sendData;
			byte[] receiveData = new byte[64];
			byte[] data;

			System.out.println("Введите аргументы через пробел: x y z ");
			String sentence = "";
			try (BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in))) {
				sentence = inFromUser.readLine();
			}
			sendData = sentence.getBytes();

			System.out.println("Шлю данные серверу..");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9999);
			clientSocket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			System.out.println("...сервер производит вычисления");
			clientSocket.setSoTimeout(10000);

			try {
				clientSocket.receive(receivePacket);
				data = new byte[receivePacket.getLength()];
				System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0,
						receivePacket.getLength());
				String resultFunction = new String(data);

				System.out.println("Получили значение функции от сервера: " + resultFunction);
			} catch (SocketTimeoutException ste) {
				System.out.println("Timeout Occurred: Packet assumed lost");
			}

		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			clientSocket.close();
		}
	}
}
