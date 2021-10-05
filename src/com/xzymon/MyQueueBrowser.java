package com.xzymon;

import com.pcbsys.nirvana.base.nHeader;
import com.pcbsys.nirvana.client.*;

import java.util.EnumSet;

public class MyQueueBrowser {

	nQueueReader reader = null;
	nQueueAsyncReader asyncReader = null;
	nQueuePeekContext ctx = null;

	nQueue myQueue = null;

	public MyQueueBrowser() throws Exception {

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

		// create the queue reader

		reader = myQueue.createReader(new nQueueReaderContext());

		ctx = nQueueReader.createContext(10);
	}

	public void start() throws Exception {
		boolean more = true;
		long eid =0;

		while (more) {
			// browse (peek) the queue
			nConsumeEvent[] evts = reader.peek(ctx);
			if (evts != null) {
				for (int x = 0; x < evts.length; x++) {
					go(evts[x]);
				}
				more = ctx.hasMore();
			}
		}

	}

	public void go(nConsumeEvent event) {
		System.out.println("Consumed event "+event.getEventID());
		String evData = new String(event.getEventData());
		System.out.println("Event data: [" + evData + "]");
		if (event.getProperties() != null) {
			int propsSize = event.getProperties().getSize();
			System.out.println("Properties size is: " + propsSize);
		} else {
			System.out.println("No properties");
		}
		nHeader header = event.getHeader();
		printFromBytes("HEADER ApplicationId", header.getApplicationId());
		printFromBytes("HEADER CorrelationId", header.getCorrelationId());
		printFromBytes("HEADER Destination", header.getDestination());
		printFromBytes("HEADER MessageId", header.getMessageId());
		printFromBytes("HEADER PubHost", header.getPubHost());
		printFromBytes("HEADER PubName", header.getPubName());
		printFromBytes("HEADER Type", header.getType());
		printFromBytes("HEADER User", header.getUserId());
		//event.ack();
	}

	public static void main(String[] args) {
		try {
			MyQueueBrowser qbrowse = new MyQueueBrowser();
			//qbrowse.publishMessage("This is second message");
			qbrowse.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printFromBytes(String name, byte[] binaries) {
		if (binaries != null) {
			String stringified = new String(binaries);
			System.out.format("%1$s : [%2$s]%n", name, stringified);
		} else {
			System.out.format("%1$s : No Data%n", name);
		}
	}

	public void publishMessage(String message) {
		try {
			System.out.println("Message publish: " + message);
			myQueue.push(new nConsumeEvent("TAG", message.getBytes()));
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
