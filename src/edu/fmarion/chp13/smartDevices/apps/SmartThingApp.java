package edu.fmarion.chp13.smartDevices.apps;

import edu.fmarion.chp13.smartDevices.server.SmartThingServer;

public abstract class SmartThingApp
{
	protected SmartThingServer server = null;

	public SmartThingApp(SmartThingServer server)
	{
		if ( server==null )
			throw new IllegalArgumentException
			(
				"The FridgeApp requires a valid server."
			);

		this.server = server;
	}

	/**
	 * Should be overridden to accept the name of the SmartThing it will be
	 * sending messages.
	 *
	 * @param thingName SmartThing message recipient
	 */
	abstract public void add(String thingName);


	/**
	 * Override to send a message corresponding to the function parameter. If
	 * child app does not have multiple functions, it may ignore the String
	 * parameter.
	 *
	 * @param function the message type which should be sent
	 */
	abstract public void sendMessage(String function);
}
