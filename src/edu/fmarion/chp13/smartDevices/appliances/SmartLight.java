package edu.fmarion.chp13.smartDevices.appliances;

import java.util.UUID;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.server.SmartThing;

public class SmartLight extends Appliance implements SmartThing
{
	private final UUID id = UUID.randomUUID();
	private boolean on = false;

	public SmartLight()
	{
		super(110);
	}

	@Override
	public String getName()
	{
		return id.toString();
	}

	@Override
	public Message receiveMsg(Message msg)
	{
		if (msg.content().contains("TOGGLE"))
		{
			on = !on;
			return new Message(msg.from(), msg.to(), "ACK");
		}
		else if (msg.content().contains("STATUS"))
		{
			return new Message(msg.from(), msg.to(), on ? "ON" : "OFF");
		}

		return null;
	}

}
