package edu.fmarion.chp13.smartDevices.apps;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.server.SmartThing;
import edu.fmarion.chp13.smartDevices.server.SmartThingServer;

public class FridgeApp extends SmartThingApp implements SmartThing
{
	private String name;
	private String fridge = null;

	/**
	 * Creates a new FridgeApp instance with a fridge connected.
	 *
	 * @param name app name
	 * @param fridge connected fridge name
	 * @param server where the app and its fridge are registered
	 * @throws IllegalArgumentException
	 */
	public FridgeApp(String name, String fridge, SmartThingServer server)
	{
		this(name, server);
		add(fridge);
	}

	/**
	 * Creates a new FridgeApp instance without a fridge connected.
	 *
	 * @param name
	 * @param server
	 * @throws IllegalArgumentException
	 */
	public FridgeApp(String name, SmartThingServer server)
	{
		super(server);

		if ( name==null || name.length()==0 )
			throw new IllegalArgumentException
			(
				"The FridgeApp requires a valid name."
			);

		this.name = name;
	}

	/**
	 *
	 * @param fridge the name of fridge to which app should send
	 * @throws IllegalArgumentException
	 */
	@Override
	public void add(String fridge)
	{
		if ( fridge==null || name.length()==0 )
			throw new IllegalArgumentException
			(
				"The FridgeApp requires a valid fridge name."
			);

		this.fridge = fridge;
	}

	/**
	 * Sends a message its fridge through its server to get a picture. When
	 * there is not fridge connected, it will send a message to itself saying
	 * as much.
	 *
	 * @param function ignored because FridgeApp has one function
	 */
	@Override
	public void sendMessage(String function)
	{
		if ( this.fridge != null )
			this.server.sendMsg(new Message(this.fridge, this.name, "PHOTO"));
		else
			this.server.sendMsg
			(
			 new Message(this.name, this.name, "NO FRIDGE CONNECTED")
			);
	}

	/**
	 * @return FridgeApp's name in for messages
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * If the sending fridge is unknown, tell it so. Otherwise, check the
	 * Message content for an attachment and display if found.
	 *
	 * @param msg
	 * @return
	 */
	@Override
	public Message receiveMsg(Message msg)
	{
		if ( !msg.from().equals(this.fridge) )
			return new Message(msg.from(), msg.to(), "UNKNOWN FRIDGE");

		else if ( msg.content().contains("ATTACHMENT") )
			System.out.println("Recieved:\n" + msg);

		return null;
	}

}
