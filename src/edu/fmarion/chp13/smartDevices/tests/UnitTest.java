package edu.fmarion.chp13.smartDevices.tests;

public abstract class UnitTest
{
	protected boolean verbose; // how vocal the tests should be

	/**
	 * Called when verbose is false.
	 */
	public UnitTest()
	{
		verbose = false;
	}

	/**
	 * @param verbose sets verbosity of tests
	 */
	public UnitTest(boolean verbose)
	{
		this.verbose = verbose;
	}


	abstract public void run();

}
