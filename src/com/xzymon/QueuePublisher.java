package com.xzymon;

import com.pcbsys.nirvana.client.*;

import java.io.Console;
import java.util.Scanner;

public class QueuePublisher {

	nQueue myQueue = null;

	public QueuePublisher() throws Exception {

		// construct your session and queue objects here
		String[] RNAME = {"nsp://127.0.0.1:9000"};

		String u = "xzymon";
		String p4 = "XzymonP4";

		nSessionAttributes nsa = new nSessionAttributes(RNAME);

		nSession myNSession= nSessionFactory.create(nsa, u, p4);

		myNSession.init();

		//nDataStreamListener myListener = new SimpleStreamListener();
		//nDataStream myStream = mySession.init(myListener);

		nChannelAttributes cattrib = new nChannelAttributes();
		cattrib.setName("/xzymon/third/created/queue");
		myQueue=myNSession.findQueue(cattrib);
	}

	public static void main(String[] args) {
		try {
			QueuePublisher qpublisher = new QueuePublisher();
			qpublisher.readFromConsole();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readFromConsole() {
		Console console = System.console();
		while(true) {
			String input = console.readLine("Tresc kolejnej wiadomosci: ");
			publishMessage(input);
		}
	}

	public void publishMessage(String message) {
		try {
			System.out.println("Message publish: " + message);
			myQueue.push(new nConsumeEvent("TAG", message.getBytes()));
			System.out.println("Opublikowano");
		} catch (nIllegalArgumentException e) {
			e.printStackTrace();
		} catch (nSessionPausedException e) {
			e.printStackTrace();
		} catch (nSecurityException e) {
			e.printStackTrace();
		} catch (nRequestTimedOutException e) {
			e.printStackTrace();
		} catch (nSessionNotConnectedException e) {
			e.printStackTrace();
		} catch (nMaxBufferSizeExceededException e) {
			e.printStackTrace();
		}
	}
}
