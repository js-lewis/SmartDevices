package edu.fmarion.chp13.smartDevices.server;

import edu.fmarion.chp13.smartDevices.messages.Message;

public interface SmartThing
{
	public String getName();

	public Message receiveMsg(Message msg);
}
