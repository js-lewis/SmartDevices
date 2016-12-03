package edu.fmarion.chp13.smartDevices.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.tests.UnitTest;


public class SmartThingServerTest extends UnitTest
{

	/**
	 * Builds a test configFile for creating SmartThingServer from file input.
	 *
	 * @return file name of created configuration file
	 */
	private String buildConfigFile()
	{
		// generate a random file name for config file
		String fname = UUID.randomUUID().toString()+".cfg";

		// try-with-resources
		try
		(
			// opened only in the context of this try block
			PrintWriter fout = new PrintWriter(fname)
		)
		{
			// write a server name and password out to file
			fout.println("serv01");
			fout.print("12345");
			// file is auto closed at the end of this try block context (here)
		}
		catch (FileNotFoundException e)
		{
			// this shouldn't be possible
			e.printStackTrace();
			assert(false);
		}

		return fname;
	}
	private boolean testConfigFile()
	{
		boolean good = true; // assume test is good

		String fname = buildConfigFile(); // get a temp file for test

		try
		{
			SmartThingServer server = new SmartThingServer(fname);
			File file = new File(fname);
			if ( this.verbose )
				System.out.println
				(
				 "\tCreating SmartThingServer from file successful."
				);
			server.sendMsg(new Message("to", "from","content"));

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
		finally
		{
			File file = new File(fname);
			if ( file.exists() )
				file.delete();
		}

		return good;
	}

	private boolean testLogon()
	{
		boolean good = true;
		SmartThingServer server = new SmartThingServer("name", "password");

		try
		{
			server.logon(new SmartThingTester(), "password");
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
			server.logon(new SmartThingTester(this.verbose), "passwor");
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
		SmartThingServer server = new SmartThingServer("name", "password");

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


	private boolean testMsgGetters()
	{
		boolean good = true;
		SmartThingServer server = new SmartThingServer("srv17", "2468");

		Message[] msgs = {
			new Message("to0", "from0", "content0"),
			new Message("to1", "from1", "content1")
		};

		for ( int i=0; i<msgs.length; ++i )
			server.sendMsg(msgs[i]);

		Message[] srvMsgs;
		try
		{
			if ( verbose )
				System.out.println("\tTesting getter w/ incorrect password");
			srvMsgs = server.getInMsgs("246");
			good = false;
		}
		catch (IllegalArgumentException e)
		{
			if ( verbose )
				System.out.println("\tException correctly generated.");
			srvMsgs = server.getInMsgs("2468");
		}

		good &= srvMsgs.length == msgs.length;

		for ( int i=0; i<srvMsgs.length; ++i )
		{
			good &= srvMsgs[i].to().equals(msgs[i].to()) &&
			 srvMsgs[i].from().equals(msgs[i].from()) &&
			 srvMsgs[i].content().equals(msgs[i].content());

			if ( verbose )
			{
				System.out.print("\tExpected: \n" + msgs[i]);
				System.out.print("\tGot: \n" + srvMsgs[i]);
			}
		}

		return good;
	}


	private boolean testRun()
	{
		SmartThing thing = new SmartThingTester(this.verbose);
		SmartThingServer server = new SmartThingServer("name", "password");

		try
		{
			server.logon(thing,  "password");
		}
		catch (ConnectionException e)
		{
			throw new AssertionError("This should not be possible.");
		}

		server.sendMsg(
		 new Message
		 (
		  thing.getName(), thing.getName(), "This message is for you."
		 )
		);
		while ( server.run() );

		return true;
	}


	public SmartThingServerTest(boolean verbose)
	{
		super(verbose);
	}

	@Override
	public void run()
	{
		// Test using config file
		System.out.println("Testing SmartThingServer with config file");
		if ( testConfigFile() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// Test logon server
		System.out.println("Testing SmartThingServer.logon()");
		if ( testLogon() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// Test updatePassword server
		System.out.println("Testing SmartThingServer.updatePassword()");
		if ( testUpdatePassword() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// Test Message Queue Getter
		System.out.println("Testing SmartThingServer.getIn/OutMsgs()");
		if ( testMsgGetters() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		// Test running server
		System.out.println("Testing SmartThingServer.run()");
		if ( testRun() )
			System.out.println("\t**PASSED**");
		else
			System.out.println("\t**FAILED**");

		System.out.println();
	}

}
