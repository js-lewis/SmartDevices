package edu.fmarion.chp13.smartDevices.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
	}

	public SmartThingServer(String configFile) throws FileNotFoundException,
		FileFormatException
	{
		// TODO Create a File object from the String parameter and open a
		// Scanner of the File instance.
		//
		// Points: / 5

		// TODO Ensure that a token exists in the file for this
		// SmartThingServer instance's name. If it exists, read use it to set
		// the instance object's name. If not, begin building a
		// FileFormatException with the missing attribute's name.
		// Points / 10

		// TODO Ensure that a token exists in the file for this
		// SmartThingServer instance's password. If it exists, read use it to
		// set the instance object's password. If not, begin building a
		// FileFormatException with the missing attribute's name.
		// Points / 10

		// TODO If any attribute was missing, throw the FileFormatException.
		// Otherwise, instantiate the two queues and the ArrayList.
		// Points: / 10

	}

	public void sendMsg(Message msg)
	{
		// TODO Correctly add Message parameter to correct Queue
		// Points  / 5
	}

	public void logon(SmartThing thing, String password)
		throws ConnectionException
	{
		// TODO When password parameter matches current password, add
		// SmartThing instance to things. Otherwise throw a
		// ConnectionException with message "Password mismatch".
		// Points:  / 5
	}

	public boolean updatePassword(String old, String password)
	{
		// TODO When old String parameter matches current password, update
		// password to password String parameter, otherwise return false;
		// Points:  / 5
		return false;
	}

	public boolean run()
	{
		// TODO When the inQueue is not empty, get a message from the correct
		// end and the remove that message. Look through the ArrayList of
		// SmartThing objects and if one has the same name as the to() method
		// of the message, send it the message. If the SmartThing responds to
		// being sent a message, add that message to the outQueue.
		// Points:  / 20


		// TODO When the outQueue is not empty, get a message from the correct
		// end and the remove that message. Look through the ArrayList of
		// SmartThing objects and if one has the same name as the to() method
		// of the message, send it the message. If the SmartThing responds to
		// being sent a message, add that message to the outQueue.
		// Points: / 20

		// TODO If either queue is not empty, return true; otherwise, return
		// false.
		// Points / 5
		return false;
	}
}
