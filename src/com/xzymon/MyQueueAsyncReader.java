package com.xzymon;

import com.pcbsys.nirvana.base.nHeader;
import com.pcbsys.nirvana.client.*;

public class MyQueueAsyncReader implements nEventListener {

	nQueue myQueue = null;

	public MyQueueAsyncReader() throws Exception {

		// construct your session and queue objects here
		String[] RNAME = {"nsp://127.0.0.1:9000"};

		String u = "xzymon";
		String p4 = "XzymonP4";

		nSessionAttributes nsa = new nSessionAttributes(RNAME);

		nSession myNSession= nSessionFactory.create(nsa, u, p4);

		myNSession.init();

		nChannelAttributes cattrib = new nChannelAttributes();
		cattrib.setName("/xzymon/third/created/queue");
		myQueue=myNSession.findQueue(cattrib);

		// begin consuming events from the queue

		nQueueReaderContext ctx = new nQueueReaderContext(this, 10);
		nQueueAsyncReader reader = myQueue.createAsyncReader(ctx);
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
	}

	public void printFromBytes(String name, byte[] binaries) {
		if (binaries != null) {
			String stringified = new String(binaries);
			System.out.format("%1$s : [%2$s]%n", name, stringified);
		} else {
			System.out.format("%1$s : No Data%n", name);
		}
	}

	public static void main(String[] args) {
		try {
			new MyQueueAsyncReader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}