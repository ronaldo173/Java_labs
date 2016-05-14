package ru.javalabs.lab6;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

class Server_UDP {
	public static void main(String args[]) throws Exception {
		try {
			DatagramSocket serverSocket = new DatagramSocket(9999);
			byte[] receiveData = new byte[64];
			byte[] data;
			byte[] sendData;

			while (true) {

				receiveData = new byte[64];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("Жду получить данные от клиента...");
				serverSocket.receive(receivePacket);
				data = new byte[receivePacket.getLength()];
				System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0,
						receivePacket.getLength());

				String sentence = new String(data);
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				System.out.println("From: " + IPAddress + ":" + port);
				System.out.println("Message: " + sentence);

				// проводим расчеты и шлем клиенту
				String result = getAnswerByArguments(sentence);
				sendData = result.getBytes();

				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}

		} catch (SocketException ex) {
			System.out.println("UDP порт 9999 занят");
			System.exit(1);
		}

	}

	private static String getAnswerByArguments(String sentence) throws IOException {
		String[] arrOfArguments = sentence.split(" ");
		int argsXYZ[] = new int[3];
		for (int i = 0; i < argsXYZ.length; i++) {
			argsXYZ[i] = Integer.parseInt(arrOfArguments[i]);
		}

		int x = argsXYZ[0];
		int y = argsXYZ[1];
		int z = argsXYZ[2];

		double funcDelimoe = Math.exp(x - y) * Math.pow(Math.abs(x - y), (x + y));
		double funcDelitet = Math.atan(x) + Math.tan(z);
		double funcSecondPart = Math.sqrt(Math.pow(x, 6) + Math.log(y));
		double funcResult = funcDelimoe / funcDelitet + funcSecondPart;

		// пишем в файл исходные данные и результаты
		writeToFile(argsXYZ, funcResult);

		return Double.toString(funcResult);
	}

	private static void writeToFile(int[] xyz, double funcResult) throws IOException {

		File file = new File("file_lab6.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
			writer.write("Arguments x,y,z: " + Arrays.toString(xyz));
			writer.write(System.getProperty("line.separator"));
			writer.write("Result: " + funcResult);
		}
		System.out.println("Результаты и исходные данные записаны в файл");

	}
}
