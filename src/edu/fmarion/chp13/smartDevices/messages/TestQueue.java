package edu.fmarion.chp13.smartDevices.messages;

import edu.fmarion.chp13.smartDevices.tests.UnitTest;


public class TestQueue extends UnitTest
{
	private int loops;
	private Queue q; // Queue being tested

	/**
	 * This constructor should not be used in TestQueue. Some number of
	 * loops must always be provided
	 */
	@SuppressWarnings("unused")
	private TestQueue() { }

	/**
	 * This constructor should not be used in TestQueue. Some number of
	 * loops must always be provided
	 */
	@SuppressWarnings("unused")
	private TestQueue(boolean verbose) { }

	/**
	 * Use when test verbosity should be minimum
	 *
	 * @param loops must be in [1, 2^31 - 1]
	 *
	 * @throws InvalidArgumentException when loops is not in range
	 */
	public TestQueue(int loops)
	{
		this(loops, false);
	}

	/**
	 *
	 * @param loops must be in [1, 2^31 - 1]
	 * @param verbose controls verbosity of tests
	 *
	 * @throws InvalidArgumentException when loops is not in range
	 */
	public TestQueue(int loops, boolean verbose)
	{
		if ( loops > 0 )
			this.loops = loops;
		else
			throw new IllegalArgumentException
			(
			 "The loops parameter must be greater than 0 when provided. " +
			 "Try using the boolean-only parameter version."
			);

		this.verbose = verbose;
	}


	/**
	 *
	 * @return
	 */
	private boolean testPush()
	{
		boolean good = true;
		for ( int i=0; i<this.loops; ++i )
		{
			q.push(new Message("to", "from", "Message " + i));
			good &= q.size() == i+1;

			if ( this.verbose )
				System.out.println(
				 String.format( "\tpushed: %d, size: %d", i, q.size() )
				);
		}

		return good;
	}


	/**
	 *
	 * @return
	 */
	private boolean testPop()
	{
		boolean good = true;

		// Add several messages to queue
		for ( int i=0; i<this.loops; ++i )
			q.push( new Message( "to", "from", "content" ) );

		// Pop and check size after pop
		for ( int i=0; i<this.loops; ++i )
		{
			good &= this.loops == this.q.size() + i;
			q.pop();

			if ( this.verbose )
				System.out.println(
				 String.format("\tpopped %d, size: %s", i, q.size())
				);

		}

		// Test exception
		try
		{
			q.pop();
		}
		catch (QueueAccessException e)
		{
			good &= e.method().equals("pop");

			if ( this.verbose )
				System.out.println(
				 "\tCaught QueueAccessException for method " + e.method() +
				 " on empty Queue."
				);
		}
		catch (Throwable t)
		{
			good = false;

			if ( this.verbose )
				System.out.println(
				 "\tCalled pop on empty Queue did not throw " +
				  "QueueAccessException."
				);
		}

		return good;
	}

	/**
	 *
	 * @return
	 */
	private boolean testPeak()
	{
		boolean good = true;

		for ( int i=0; i<this.loops; ++i )
		{
			q.push( new Message( "to", "from", Integer.toString(i) ) );

			good &= i == Integer.parseInt( q.peak().content() );

			if ( this.verbose )
				System.out.println(
				 String.format("\tpushed: %d, peaked: %s", i, q.peak() )
				);

			q.pop();

		}

		// Test exception
		try
		{
			System.out.println(q.peak());
		}
		catch (QueueAccessException e)
		{
			good &= e.method().equals("peak");

			if ( this.verbose )
				System.out.println(
				 "\tCaught QueueAccessException for method " + e.method() +
				 " on empty Queue."
				);
		}
		catch (Throwable t)
		{
			good = false;

			if ( this.verbose )
				System.out.println(
				 "\tCalled peak on empty Queue did not throw " +
				  "QueueAccessException."
				);
		}

		return good;
	}


	@Override
	public void run()
	{
		System.out.println("Testing Queue.push(): ");
		this.q = new Queue();
		if ( testPush() )
			System.out.println("\t**push PASSED**\n");
		else
			System.err.println("\t**push FAILED**\n");

		System.out.println("Testing Queue.pop(): ");
		this.q = new Queue();
		if ( testPop() )
			System.out.println("\t**pop PASSED**\n");
		else
			System.err.println("\t**pop FAILED**\n");

		System.out.println("Testing Queue.peak(): ");
		this.q = new Queue();
		if ( testPeak() )
			System.out.println("\t**peak PASSED**\n");
		else
			System.err.println("\t**peak FAILED**\n");

	}

}
