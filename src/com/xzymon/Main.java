package com.xzymon;

import com.pcbsys.nirvana.client.*;

public class Main {

	public static void main(String[] args) {
		try {
			doSth();
		} catch (nIllegalArgumentException e) {
			e.printStackTrace();
		} catch (nSessionNotConnectedException e) {
			e.printStackTrace();
		} catch (nSecurityException e) {
			e.printStackTrace();
		} catch (nSessionAlreadyInitialisedException e) {
			e.printStackTrace();
		} catch (nRealmUnreachableException e) {
			e.printStackTrace();
		} catch (nChannelNotFoundException e) {
			e.printStackTrace();
		} catch (nUnexpectedResponseException e) {
			e.printStackTrace();
		} catch (com.pcbsys.nirvana.client.nIllegalChannelMode nIllegalChannelMode) {
			nIllegalChannelMode.printStackTrace();
		} catch (nSessionPausedException e) {
			e.printStackTrace();
		} catch (nUnknownRemoteRealmException e) {
			e.printStackTrace();
		} catch (nRequestTimedOutException e) {
			e.printStackTrace();
		}
	}

	private static void doSth() throws nIllegalArgumentException, nSessionNotConnectedException, nSecurityException, nSessionAlreadyInitialisedException, nRealmUnreachableException, nChannelNotFoundException, nUnexpectedResponseException, nIllegalChannelMode, nSessionPausedException, nUnknownRemoteRealmException, nRequestTimedOutException {
		String[] RNAME = {"nsp://127.0.0.1:9000"};
		nSessionAttributes nsa = new nSessionAttributes(RNAME);

		nSession myNSession= nSessionFactory.create(nsa);

		myNSession.init();

		//nDataStreamListener myListener = new SimpleStreamListener();
		//nDataStream myStream = mySession.init(myListener);

		nChannelAttributes cattrib = new nChannelAttributes();
		cattrib.setName("/xzymon/this/will/be/second");
		nQueue myQueue=myNSession.findQueue(cattrib);
	}


}
