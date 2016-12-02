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
		attributes = new ArrayList<String>();
		attributes.add(attribute);
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
		this.attributes.add(attribute);
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
		String msg = new String();

		for ( int i=0; i<attributes.size()-1; ++i )
			msg += "Missing " + attributes.get(i) + ".\n";

		msg += "Missing " + attributes.get(attributes.size()-1) + ".";

		return msg;
	}

	// Package-level access. This should be okay as the structure is
	// instantiated in both constructors.
	ArrayList<String> attributes; // a list of
}
