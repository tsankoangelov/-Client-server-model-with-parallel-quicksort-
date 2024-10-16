package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int SERVER_PORT = 4444;
	public static int clientId = 0;

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
			System.out.println("Server started...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected.");
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				ConnectionHandler clientHandler = new ConnectionHandler(clientSocket, in, out, clientId);
				Thread thread = new Thread(clientHandler);
				thread.start();
				clientId++;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
