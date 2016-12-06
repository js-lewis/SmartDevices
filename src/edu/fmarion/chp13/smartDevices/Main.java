package edu.fmarion.chp13.smartDevices;

import edu.fmarion.chp13.smartDevices.appliances.Appliance;
import edu.fmarion.chp13.smartDevices.appliances.SmartFridge;
import edu.fmarion.chp13.smartDevices.appliances.SmartLight;
import edu.fmarion.chp13.smartDevices.apps.FridgeApp;
import edu.fmarion.chp13.smartDevices.apps.LightApp;
import edu.fmarion.chp13.smartDevices.apps.SmartThingApp;
import edu.fmarion.chp13.smartDevices.server.ConnectionException;
import edu.fmarion.chp13.smartDevices.server.SmartThing;
import edu.fmarion.chp13.smartDevices.server.SmartThingServer;

public class Main
{

	public static void main(String[] args)
	{
		final String pass = "124pS";
		SmartThingServer server = new SmartThingServer("srv17", pass);

		Appliance fridge = new SmartFridge(110, "app17");
		SmartThingApp app = new FridgeApp (
		 "app13", ((SmartThing)fridge).getName(), server
		);

		Appliance[] lights = new Appliance[2];
		for ( int i=0; i<lights.length; ++i )
			lights[i] = new SmartLight();

		SmartThingApp lightApp = new LightApp("app15", server);
		for ( int i=0; i<lights.length; ++i )
			lightApp.add(( (SmartThing)lights[i]).getName() );

		try
		{
			server.logon((SmartThing)fridge, pass);
			server.logon((SmartThing)app, pass);

			server.logon((SmartThing)lightApp, pass);
			for ( int i=0; i<lights.length; ++i )
				server.logon((SmartThing)lights[i], pass);
		}
		catch (ConnectionException e)
		{
			// this should not be possible
			e.printStackTrace();
		}

		app.sendMessage(null);
		lightApp.sendMessage("STATUS");
		lightApp.sendMessage("SWITCH");
		lightApp.sendMessage("STATUS");
		while ( server.run() );
		System.out.println();
	}

}
