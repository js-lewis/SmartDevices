package edu.fmarion.chp13.smartDevices.appliances;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.server.SmartThing;

public class SmartFridge extends Appliance implements SmartThing
{
	private String name;

	public SmartFridge(int voltage, String name)
	{
		super(voltage);

		if ( name==null || name.length() == 0)
			throw new IllegalArgumentException("Fridge needs a valid name");

		this.name = name;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public Message receiveMsg(Message msg)
	{
		if ( msg.content().contains("PHOTO") )
		{
			String photo = "ATTACHMENT\n" +
			 "-------\n" +
			 "-     -\n" +
			 "-     -\n" +
			 "-------\n" +
			 "-     -\n" +
			 "-     -\n" +
			 "-     -\n" +
			 "-     -\n" +
			 "-------\n";
			return new Message(msg.from(), msg.to(), photo);
		}
		else
			return null;
	}

}
