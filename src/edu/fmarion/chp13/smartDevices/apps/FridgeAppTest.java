package edu.fmarion.chp13.smartDevices.apps;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.server.ConnectionException;
import edu.fmarion.chp13.smartDevices.server.SmartThingServer;
import edu.fmarion.chp13.smartDevices.server.SmartThingTester;
import edu.fmarion.chp13.smartDevices.tests.UnitTest;

public class FridgeAppTest extends UnitTest
{


	private boolean construtorTest()
	{
		boolean good = true;
		SmartThingServer server = new SmartThingServer("serv01", "12345");

		try
		{
			FridgeApp app = new FridgeApp("name", null);
			good = false;
			if ( verbose )
				System.out.println("\tNo exception generated on null server");
		}
		catch ( IllegalArgumentException e)
		{
		}

		try
		{
			FridgeApp app = new FridgeApp(null, server);
			good &= false;
			if ( verbose )
				System.out.println("\tNo exception generated on null name");
		}
		catch ( IllegalArgumentException e)
		{
			good &= true;
		}

		return good;
	}


	private boolean addTest()
	{
		boolean good = true;
		SmartThingServer server = new SmartThingServer("serv01", "12345");

		FridgeApp app = new FridgeApp("app01", server);

		try
		{
			app.add("fri02");
			if ( verbose )
				System.out.println("\tFridge name successfully added.");
		}
		catch(IllegalArgumentException e)
		{
			good = false;
			if ( verbose )
				System.out.println("\tException generated on valid fridge");
		}

		try
		{
			app.add(null);
			good &= false;
			if ( verbose )
				System.out.println("\tNo exception generated on null fridge");
		}
		catch(IllegalArgumentException e)
		{
			good &= true;
			if ( verbose )
				System.out.println("\tException generated on null fridge");
		}

		return good;
	}


	private boolean sendMessageTest()
	{
		boolean good = true;
		SmartThingServer server = new SmartThingServer("srv13", "2345");

		SmartThingTester fridge = new SmartThingTester(this.verbose);
		FridgeApp app = new FridgeApp("app07", fridge.getName(), server);

		try
		{
			server.logon(fridge, "2345");
			good = true;
		}
		catch (ConnectionException e)
		{
			e.printStackTrace();
			good = false;
		}

		if ( verbose )
			System.out.println("\tSending message:");

		app.sendMessage(null);
		Message[] msgs = server.getInMsgs("2345");
		if ( msgs.length == 1 )
		{
			good &= msgs[0].from().equals(app.getName()) &
			 msgs[0].to().equals(fridge.getName()) &
			 msgs[0].content().equals("PHOTO");

			if ( verbose )
			{
				System.out.println("\t sent:" + fridge.getName() +
					 ", got:" + msgs[0].to());
				System.out.println("\t sent:" + app.getName() +
					 ", got:" + msgs[0].from());
				System.out.println("\t sent:PHOTO, got:" + msgs[0].content());
			}

		}

		if ( verbose )
			System.out.println("\tSending message without set Fridge");
		app = new FridgeApp("app07", server);
		app.sendMessage(null);
		msgs = server.getInMsgs("2345");
		if ( msgs.length == 2 )
		{
			good &= msgs[1].from().equals(app.getName()) &
			 msgs[1].to().equals(app.getName()) &
			 msgs[1].content().equals("NO FRIDGE CONNECTED");

			if ( verbose )
			{
				System.out.println("\t sent:" + app.getName() +
				 ", got:" + msgs[1].to());
				System.out.println("\t sent:" + app.getName() +
				 ", got:" + msgs[1].from());
				System.out.println("\t sent:NO FRIDGE CONNECTED, got:" +
				 msgs[1].content());
			}
		}

		return good;
	}

	public FridgeAppTest(boolean verbose)
	{
		super(verbose);
	}

	@Override
	public void run()
	{
		// test the app's constructors
		System.out.println("Testing FridgeApp's constructors:");
		if ( construtorTest() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// test adding fridge to app
		System.out.println("Testing adding fridge to FridgeApp:");
		if ( addTest() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// test sending messages from app
		System.out.println("Testing sending msg via SmartThingServer:");
		if ( sendMessageTest() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

	}

}
