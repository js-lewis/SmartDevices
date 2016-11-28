package edu.fmarion.chp13.smartDevices.server;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@SuppressWarnings("serial")
public class FileFormatException extends NoSuchElementException
{
	/**
	 * Creates a FileFormatException instance with String parameter in
	 * list of missing attributes.
	 *
	 * @param attribute
	 */
	public FileFormatException(String attribute)
	{
		// TODO Initialize attributes ArrayList and add
		//	parameter to list
		// Points:  / 5
	}


	/**
	 * Adds String parameter to end of list of missing attributes.
	 *
	 * @param attribute
	 */
	public void addAttribute(String attribute)
	{
		// TODO Add String parameter to attributes ArrayList
		// Points:  / 5
	}


	/**
	 * Returns a String listing all missing tokens in the form:
	 *
	 *  Missing SmartThingServer <attribute_0>.\n
	 *  Missing SmartThingServer <attribute_1>.
	 *
	 * Where attribute is a missing attribute from SmartThingServer
	 * configuration file.
	 */
	@Override
	public String getMessage()
	{
		// TODO create and return a String of the correct form
		// Points: / 10
		return null;
	}

	// Package-level access. This should be okay as the structure is
	// instantiated in both constructors.
	ArrayList<String> attributes; // a list of
}
