package ru.nsu.ccfit.pm.econ.common.engine.roles;

/**
 * Base unmodifiable interface for all players.
 * @author dragonfly
 */
public interface IUPlayer {
	
	/**
	 * @return Unmodifiable person description.
	 */
	IUPersonDescription getUnmodifiablePersonDescription();
	
	/**
	 * Player id. The id should be unique within one game session.
	 * <p>This id is used primarily for identifying players within the engine, but may also be used 
	 * for other applications, such as identifying reconnecting players.</p>
	 * @return Player unique identifier.
	 */
	long getId();

}
