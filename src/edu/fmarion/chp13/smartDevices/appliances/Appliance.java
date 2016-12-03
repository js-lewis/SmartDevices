package edu.fmarion.chp13.smartDevices.appliances;

abstract public class Appliance
{
	private int voltage;

	public Appliance(int voltage)
	{
		if ( voltage < 1 )
			throw new IllegalArgumentException("Voltage must be nonnegative");

		this.voltage = voltage;
	}

	public int voltage()
	{
		return voltage;
	}
}
