package edu.fmarion.chp13.smartDevices.messages;

import java.util.ArrayList;

public class Queue extends ArrayList<Message>
{
	private int start, end;

	public Queue()
	{
		start = end = 0;
	}

	public Message peak()
	{
		if ( end - start == 0 )
			throw new QueueAccessException("peak");

		return get(start);
	}

	public void pop()
	{
		if ( end - start == 0 )
			throw new QueueAccessException("pop");

		// remove element at first position and increment counter
		++start;

		if ( end - start < super.size()/2 )
			shiftQueue();
	}

	private void shiftQueue()
	{
		// Move elements from index start to 0.
		for ( int i=0; i < end - start; ++i )
			set(i, get(start+i));
		// update start and end
		end -= start;
		start = 0;

	}

	public void push(Message msg)
	{
		// If the end of queue is inside the ArrayList, indicating it has been
		// shrunk.
		if ( end < super.size()-1 )
			this.set(end, msg);
		// If the end is not inside the ArrayList
		else
			this.add(msg);

		// Move end to the right
		++end;
	}

	@Override
	public int size()
	{
		return end - start;
	}

	@Override
	public boolean isEmpty()
	{
		return end - start == 0;
	}
}
