package ru.nsu.ccfit.pm.econ.common.engine.data;


/**
 * Possible company message types.
 * @see IUTextOnlyCompanyMessage
 * @see IUTextOnlyCompanyMessage#getType()
 * @author dragonfly
 */
public enum CompanyMessageType {
	
	/**
	 * The company message is rumor.
	 * <p>Rumors can be true, false or only partially true. Such
	 * messages may be delivered to any subset of players.</p>
	 */
	RUMOR,
	
	/**
	 * The company message is official news.
	 * <p>Official news are always true. Such messages should be 
	 * delivered to all players.</p>
	 */
	OFFICIAL,
	
}