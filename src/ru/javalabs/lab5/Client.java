package ru.javalabs.lab5;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {

        String serverHostname = new String ("127.0.0.1");

        System.out.println ("Подключение к серверу на порте 9999");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 9999);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));         
        } catch (Exception e) {
            System.err.println("Проблемы при подключениее к: " + serverHostname);
            System.exit(1);
        }

	BufferedReader stdIn = new BufferedReader(
                                   new InputStreamReader(System.in));
	String userInput;

        System.out.print ("input: ");
	while ((userInput = stdIn.readLine()) != null) {
	    out.println(userInput);
	    System.out.println("получил от сервера: " + in.readLine());
            System.out.print ("input: ");
	}

	out.close();
	in.close();
	stdIn.close();
	echoSocket.close();
    }
}
