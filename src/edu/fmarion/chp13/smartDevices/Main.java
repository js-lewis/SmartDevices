package edu.fmarion.chp13.smartDevices;

import edu.fmarion.chp13.smartDevices.appliances.Appliance;
import edu.fmarion.chp13.smartDevices.appliances.SmartFridge;
import edu.fmarion.chp13.smartDevices.apps.FridgeApp;
import edu.fmarion.chp13.smartDevices.apps.SmartThingApp;
import edu.fmarion.chp13.smartDevices.server.ConnectionException;
import edu.fmarion.chp13.smartDevices.server.SmartThing;
import edu.fmarion.chp13.smartDevices.server.SmartThingServer;

public class Main
{

	public static void main(String[] args)
	{
		SmartThingServer server = new SmartThingServer("srv17", "124pS");
		Appliance fridge = new SmartFridge(110, "app17");
		SmartThingApp app = new FridgeApp (
		 "app13", ((SmartThing)fridge).getName(), server
		);

		try
		{
			server.logon((SmartThing)fridge, "124pS");
			server.logon((SmartThing)app, "124pS");
		}
		catch (ConnectionException e)
		{
			// this should not be possible
			e.printStackTrace();
		}

		app.sendMessage(null);
		while ( server.run() );
	}

}
