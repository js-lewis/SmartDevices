package edu.fmarion.chp13.smartDevices.apps;

import java.util.ArrayList;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.server.SmartThing;
import edu.fmarion.chp13.smartDevices.server.SmartThingServer;

public class LightApp extends SmartThingApp implements SmartThing
{
	private String name;
	private ArrayList<String> lights;

	public LightApp(String name, SmartThingServer server)
	{
		this(name, server, null);
	}

	public LightApp(String name, SmartThingServer server, String light)
	{
		super(server);

		this.name = name;

		lights = new ArrayList<String>();
		if ( light != null )
			lights.add(light);
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public Message receiveMsg(Message msg)
	{
		if ( msg.content().contains("ACK") )
			System.out.println("Light " + msg.from() + " Switched!");

		else if ( msg.content().contains("O") )
			System.out.println
			(
			 "Light " + msg.from() + " is currently " + msg.content()
			);

		return null;
	}

	@Override
	public void add(String thingName)
	{
		lights.add(thingName);
	}

	@Override
	public void sendMessage(String function)
	{
		if ( function.contains("SWITCH") )
			for ( int i=0; i<lights.size(); ++i )
				server.sendMsg(new Message(lights.get(i), name, "TOGGLE"));

		else if ( function.contains("STATUS") )
			for ( int i=0; i<lights.size(); ++i )
				server.sendMsg(new Message(lights.get(i), name, "STATUS"));
	}

}
