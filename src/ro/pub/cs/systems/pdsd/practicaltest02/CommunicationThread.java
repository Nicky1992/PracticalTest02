package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class CommunicationThread extends Thread{
	private ServerThread serverThread;
	private Socket       socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				String line = "";
				while((line = bufferedReader.readLine()) != null) {
					String[] operators = line.split(",");
					if (operators[0].equals("add")) {
						Integer op1 = Integer.parseInt(operators[1]);
						Integer op2 = Integer.parseInt(operators[2]);
						Integer res = op1 + op2;
						printWriter.println("" + res);
						printWriter.flush();
					} else if (operators[0].equals("mul")) {
						Integer op1 = Integer.parseInt(operators[1]);
						Integer op2 = Integer.parseInt(operators[2]);
						Integer res = op1 * op2;
						Thread.sleep(3000);
						printWriter.println("" + res);
						printWriter.flush();
					}
				}
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
