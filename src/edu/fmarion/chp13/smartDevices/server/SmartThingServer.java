package edu.fmarion.chp13.smartDevices.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.fmarion.chp13.smartDevices.messages.Message;
import edu.fmarion.chp13.smartDevices.messages.Queue;

public class SmartThingServer
{
	private String name;
	private String password;

	private Queue inQueue;
	private Queue outQueue;

	// smart things, both listeners and senders
	private ArrayList<SmartThing> things;

	public SmartThingServer(String name, String password)
	{
		// TODO Set name and password from parameters, initialize Queues,
		// and initialize ArrayList.
		// Points:  / 5
		this.name = name;
		this.password = password;

		inQueue = new Queue();
		outQueue = new Queue();

		things = new ArrayList<SmartThing>();
	}

	public SmartThingServer(String configFile) throws FileNotFoundException,
		FileFormatException
	{
		// TODO Create a File object from the String parameter and open a
		// Scanner of the File instance.
		//
		// Points: / 5
		File file = new File(configFile);
		FileFormatException ffe = null;

		Scanner fin = new Scanner(file); // calling context handles exception

		// TODO Ensure that a token exists in the file for this
		// SmartThingServer instance's name. If it exists, read use it to set
		// the instance object's name. If not, begin building a
		// FileFormatException with the missing attribute's name.
		// Points / 10
		if (fin.hasNext())
			this.name = fin.next();
		else
			ffe = new FileFormatException("name");

		// TODO Ensure that a token exists in the file for this
		// SmartThingServer instance's password. If it exists, read use it to
		// set the instance object's password. If not, begin building a
		// FileFormatException with the missing attribute's name.
		// Points / 10
		if (fin.hasNext())
			this.password = fin.next();
		else if (ffe == null)
			ffe = new FileFormatException("password");
		else
			ffe.addAttribute("password");

		fin.close();

		// TODO If any attribute was missing, throw the FileFormatException.
		// Otherwise, instantiate the two queues and the ArrayList.
		// Points: / 10
		if (ffe != null)
			throw ffe;

		inQueue = new Queue();
		outQueue = new Queue();

		things = new ArrayList<SmartThing>();
	}

	public void sendMsg(Message msg)
	{
		// TODO Correctly add Message parameter to correct Queue
		// Points  / 5
		inQueue.push(msg);
	}

	public void logon(SmartThing thing, String password)
		throws ConnectionException
	{
		// TODO When password parameter matches current password, add
		// SmartThing instance to things. Otherwise throw a
		// ConnectionException with message "Password mismatch".
		// Points:  / 5
		if (this.password == password)
			things.add(thing);
		else
			throw new ConnectionException("Password mismatch");
	}

	public boolean updatePassword(String old, String password)
	{
		// TODO When old String parameter matches current password, update
		// password to password String parameter, otherwise return false;
		// Points:  / 5
		if (!this.password.equals(old))
			return false;

		this.password = password;
		return true;
	}

	public boolean run()
	{
		// TODO When the inQueue is not empty, get a message from the correct
		// end and the remove that message. Look through the ArrayList of
		// SmartThing objects and if one has the same name as the to() method
		// of the message, send it the message. If the SmartThing responds to
		// being sent a message, add that message to the outQueue.
		// Points:  / 20
		if ( !inQueue.isEmpty() )
		{
			Message msg = inQueue.peak();
			inQueue.pop();

			for ( int i=0; i<things.size(); ++i )
				if ( things.get(i).getName().equals(msg.to()))
				{
					Message out = things.get(i).receiveMsg(msg);

					if ( out != null )
						outQueue.push(out);
				}
		}

		// TODO When the outQueue is not empty, get a message from the correct
		// end and the remove that message. Look through the ArrayList of
		// SmartThing objects and if one has the same name as the to() method
		// of the message, send it the message. If the SmartThing responds to
		// being sent a message, add that message to the outQueue.
		// Points: / 20
		if ( !outQueue.isEmpty() )
		{
			Message msg = outQueue.peak();
			outQueue.pop();

			for ( int i=0; i<things.size(); ++i )
				if ( things.get(i).getName().equals(msg.to()))
				{
					Message out = things.get(i).receiveMsg(msg);
					if (out != null)
						outQueue.push(out);
				}
		}

		// TODO If either queue is not empty, return true; otherwise, return
		// false.
		// Points / 5
		return inQueue.size() > 0 || outQueue.size() > 0;
	}
}
