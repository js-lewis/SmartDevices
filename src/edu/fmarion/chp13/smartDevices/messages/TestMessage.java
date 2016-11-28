package edu.fmarion.chp13.smartDevices.messages;

import edu.fmarion.chp13.smartDevices.tests.UnitTest;

public class TestMessage extends UnitTest
{

	public TestMessage(boolean verbose)
	{
		super(verbose);
	}

	private String getHexString()
	{

		long base = (long)( (Math.pow(2.0, 32.0) + 1.0) * Math.random() );

		return Long.toHexString(base);
	}

	private boolean test()
	{
		String to = getHexString();
		String from = getHexString();
		String content = getHexString();

		// build correct toString string
		String toString = "To:\t" + to + "\n" +
		 "From:\t" + from + "\n" +
		 "Content:\t" + content + "\n";

		Message msg = new Message(to, from, content);

		boolean good = to.equals( msg.to() ) &&
		 from.equals( msg.from() ) &&
		 content.equals( msg.content() ) &&
		 toString.equals( msg.toString() );

		if ( this.verbose )
		{
			System.out.printf("\tSet to: %s,\tread: %s%n", to, msg.to());
			System.out.printf("\tSet from: %s,\tread: %s%n", from, msg.from());
			System.out.printf
			(
			 "\tSet content: %s,\tread: %s%n",
			 content,
			 msg.content()
			);

			System.out.println("\tGenerated toString:\n" + toString);
			System.out.println("\tMessage.toString():\n" + msg.toString());
		}

		return good;
	}

	private boolean ExceptionTest()
	{
		boolean good = true;

		try
		{
			@SuppressWarnings("unused")
			Message m = new Message(null, null, null);
			good = false;
			if ( verbose )
				System.out.println
				(
				 "\tPassed Message null values and was none were caught."
				);
		}
		catch ( IllegalArgumentException e )
		{
			if ( verbose )
				System.out.println
				(
				 "\tPassed Message null values and " +
				 "\"" + e.getMessage() + "\" was caught."
				);
		}

		try
		{
			@SuppressWarnings("unused")
			Message m = new Message("", "", "");
			good = false;
			if ( verbose )
				System.out.println
				(
				 "\tPassed Message null to parameter and was not caught."
				);
		}
		catch ( IllegalArgumentException e )
		{
			if ( verbose )
				System.out.println
				(
				 "\tPassed Message empty string as to parameter and " +
				 "\"" + e.getMessage() + "\" was caught."
				);
		}

		return good;
	}


	@Override
	public void run()
	{
		System.out.println("Testing Message class constructor and getters:");
		if ( test() )
			System.out.println("\t**construtor and getters PASSED**");
		else
			System.out.println("\t**construtor and getters FAILED**");
		System.out.println();


		System.out.println("Testing Message constructor exceptions");
		if ( ExceptionTest() )
			System.out.println("\t**construtor exceptions PASSED**");
		else
			System.out.println("\t**construtor exceptions FAILED**");

		System.out.println();
	}

}
