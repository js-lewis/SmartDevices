package edu.fmarion.chp13.smartDevices.tests;

import java.util.ArrayList;

import edu.fmarion.chp13.smartDevices.messages.TestMessage;
import edu.fmarion.chp13.smartDevices.messages.TestQueue;
import edu.fmarion.chp13.smartDevices.server.key.TestSmartThingServer;

public class Main
{

	public static void main(String[] args)
	{
		ArrayList<UnitTest> unitTests = new ArrayList<UnitTest>();


		// Test the Message class
		unitTests.add(new TestMessage(false));

		// Test the Queue class
		unitTests.add(new TestQueue(3, false));

		// test the SmartThingServer class
		unitTests.add(new TestSmartThingServer(true));

		for ( int i=0; i<unitTests.size(); ++i )
			unitTests.get(i).run();
	}
}
