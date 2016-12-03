package edu.fmarion.chp13.smartDevices.tests;

import java.util.ArrayList;

import edu.fmarion.chp13.smartDevices.apps.FridgeAppTest;
import edu.fmarion.chp13.smartDevices.messages.MessageTest;
import edu.fmarion.chp13.smartDevices.messages.QueueTest;
import edu.fmarion.chp13.smartDevices.server.SmartThingServerTest;

public class Main
{

	public static void main(String[] args)
	{
		ArrayList<UnitTest> unitTests = new ArrayList<UnitTest>();

		// Test the Message class
		unitTests.add(new MessageTest(false));

		// Test the Queue class
		unitTests.add(new QueueTest(3, false));

		// test the SmartThingServer class
		unitTests.add(new SmartThingServerTest(false));

		// test the FridgeApp class
		unitTests.add(new FridgeAppTest(false));

		for ( int i=0; i<unitTests.size(); ++i )
			unitTests.get(i).run();
	}
}
