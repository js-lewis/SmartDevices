package edu.fmarion.chp13.smartDevices.messages;

public class Message
{
	private String to;
	private String from;
	private String content;

	/**
	 *
	 * @param to must not be null or empty
	 * @param from must not be null or empty
	 * @param content must not be null or empty
	 *
	 * @throws IllegalArgumentException if any parameter is null or empty.
	 */
	public Message(String to, String from, String content)
	{
		if ( to != null && to.length() > 0 )
			this.to = to;
		else
			throw new IllegalArgumentException
			(
			 "The to parameter is null or empty."
			);

		if ( from != null && from.length() > 0 )
			this.from = from;
		else
			throw new IllegalArgumentException
			(
			 "The from parameter is null or empty."
			);

		if ( content != null && content.length() > 0 )
			this.content = content;
		else
			throw new IllegalArgumentException
			(
			 "The content parameter is null or empty."
			);
	}

	public String to()
	{
		return to;
	}

	public String from()
	{
		return from;
	}

	public String content()
	{
		return content;
	}

	@Override
	public String toString()
	{
		String message = new String();
		message += "==========================\n";
		message += "To:\t" + to + "\n";
		message += "From:\t" + from + "\n";
		message += "-----------------------\n";
		message += "Content:\n\t" + content + "\n";
		message += "==========================\n";

		return message;
	}
}
