package edu.fmarion.chp13.smartDevices.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.tests.UnitTest;

class Device extends Object implements SmartThing
{
	boolean verbose;
	String name;
	int received = 0;
	int replied = 0;

	public Device(String name, boolean verbose)
	{
		this.name = name;
		this.verbose = verbose;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Message receiveMsg(Message msg)
	{
		if ( this.verbose )
			System.out.println("\tDevice received:\n" + msg);

		if ( this.received++ == 0 )
		{
			Message reply =
			 new Message(msg.from(), msg.to(), "Message Received!");

			if ( this.verbose )
				System.out.println("\tDevice replied:\n" + reply);

			++this.replied;
			return reply;
		}
		else
		{
			if ( this.verbose )
				System.out.println("\tDevice replied: null");

			return null;
		}
	}
}

public class TestSmartThingServer extends UnitTest
{
	private SmartThingServer server;

	public TestSmartThingServer(boolean verbose)
	{
		super(verbose);
	}

	private String buildConfigFile()
	{
		String fname = UUID.randomUUID().toString();

		try
		(
			PrintWriter fout = new PrintWriter(fname)
		)
		{
			fout.println("name");
			fout.print("password");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			assert(false);
		}

		return fname;
	}
	private boolean testConfigFile()
	{
		boolean good = true;
		String fname = buildConfigFile();
		try
		{
			server = new SmartThingServer(fname);
			File file = new File(fname);
			if ( this.verbose )
				System.out.println
				(
				 "\tCreating SmartThingServer from file successful."
				);

			// clearing out file
			PrintWriter fout = new PrintWriter(file);
			fout.close();
			server = new SmartThingServer(fname);
		}
		catch(FileNotFoundException e)
		{
			throw new AssertionError("This should not be possible");
		}
		catch(FileFormatException e)
		{
			String exceptMsg = "Missing name.\nMissing password.";
			if ( exceptMsg.equals(e.getMessage()) )
				good &= true;
			else
				good &= false;

			if ( this.verbose )
			{
				System.out.printf("\tExpected:%n>>%s<<%n", exceptMsg);
				System.out.printf("\tReceived:%n>>%s<<%n", e.getMessage());
			}
		}

		return good;
	}

	private boolean testLogon()
	{
		boolean good = true;

		server = new SmartThingServer("name", "password");

		try
		{
			server.logon(new Device("dev", this.verbose), "password");
			if ( this.verbose )
				System.out.println
				(
				 "\tUsed correct password, logon successful."
				);
		}
		catch (ConnectionException e)
		{
			good = false;
			if ( this.verbose )
				System.out.println
				(
				 "\tUsed correct password, got " + e.getMessage()
				);
		}

		try
		{
			server.logon(new Device("dev", this.verbose), "passwor");
			good = false;
			if ( verbose )
				System.out.println
				(
				 "\tUsed incorrect password, logon was successful."
				);
		}
		catch (ConnectionException e)
		{
			if ( this.verbose )
				System.out.println
				(
				 "\tUsed incorrect password, got " + e.getMessage()
				);
		}

		return good;
	}


	private boolean testUpdatePassword()
	{
		boolean good = true;

		server = new SmartThingServer("name", "password");

		good &= server.updatePassword("password", "12345");
		if ( this.verbose )
			System.out.println
			(
				"\tUpdated password from password to 12345"
			);

		good &= !server.updatePassword("password", "12345");
		if ( this.verbose )
			System.out.println
			(
				"\tUpdated password from password to 12345, but password was" +
				 " set to 12345."
			);

		return good;
	}


	private boolean testRun()
	{
		Device thing = new Device("dev", this.verbose);
		server = new SmartThingServer("name", "password");

		try
		{
			server.logon(thing,  "password");
		}
		catch (ConnectionException e)
		{
			throw new AssertionError("This should not be possible.");
		}

		server.sendMsg(new Message("dev", "notDev", "This message is for you."));
		server.sendMsg(new Message("dev", "notDev", "This message is for you, too."));
		while ( server.run() );

		return thing.received==2 && thing.replied==1;
	}


	@Override
	public void run()
	{
		// Test using config file
		System.out.println("Testing SmartThingServer with config file");
		if ( testConfigFile() )
			System.out.println("\tconfigFile **PASSED**");
		else
			System.out.println("\tconfigFile **FAILED**");
		System.out.println();

		// Test logon server
		System.out.println("Testing SmartThingServer.logon()");
		if ( testLogon() )
			System.out.println("\tSmartThingServer.logon() **PASSED**");
		else
			System.out.println("\tSmartThingServer.logon() **FAILED**");
		System.out.println();

		// Test updatePassword server
		System.out.println("Testing SmartThingServer.updatePassword()");
		if ( testUpdatePassword() )
			System.out.println("\tSmartThingServer.updatePassword() **PASSED**");
		else
			System.out.println("\tSmartThingServer.updatePassword() **FAILED**");
		System.out.println();

		// Test running server
		System.out.println("Testing SmartThingServer.run()");
		if ( testRun() )
			System.out.println("\tSmartThingServer.run() **PASSED**");
		else
			System.out.println("\tSmartThingServer.run() **FAILED**");
	}

}
