package edu.fmarion.chp13.smartDevices.messages;

public class QueueAccessException extends IndexOutOfBoundsException
{

	private String methodName;

	public QueueAccessException(String methodName)
	{
		super(String.format("Queue is empty. The %s method failed.", methodName));
		this.methodName = methodName;
	}

	public String method()
	{
		return methodName;
	}

}
