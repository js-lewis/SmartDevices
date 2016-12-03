package edu.fmarion.chp13.smartDevices.server;

import edu.fmarion.chp13.smartDevices.messages.Message;

public class SmartThingTester implements SmartThing
{
	private boolean verbose;

	public SmartThingTester()
	{
		this(false);
	}

	public SmartThingTester(boolean verbose)
	{
		this.verbose = verbose;
	}

	@Override
	public String getName()
	{
		return "tester";
	}

	@Override
	public Message receiveMsg(Message in)
	{
		if ( verbose )
		{
			System.out.println("SmartThingTester Got Message: ");
			System.out.println(in);
		}

		if ( !in.content().contains("ACK") )
		{
			Message out = new Message(in.from(), in.to(), "ACK");

			if ( verbose )
			{
				System.out.println("SmartThingTester Responding with:");
				System.out.println(out);
			}

			return out;
		}
		else
			return null;
	}

}
