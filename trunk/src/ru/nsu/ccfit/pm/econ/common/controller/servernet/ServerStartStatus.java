package ru.nsu.ccfit.pm.econ.common.controller.servernet;

/**
 * Enumerates possible server start results.
 * @author dragonfly
 */
public enum ServerStartStatus {
	
	/**
	 * Server started normally.
	 */
	OK,
	
	/**
	 * Server started normally, but autolocate support is not 
	 * enabled or failed to initialize.
	 */
	OK_NO_AUTOLOCATE,
	
	/**
	 * Failed to bind server socket.
	 */
	CANNOT_BIND,
	
	/**
	 * Server socket cannot be created due to security restrictions.
	 */
	ACCESS_DENIED,
	
	/**
	 * Attempted to start server from an invalid system state.
	 * <p>E.g. server already started.</p> 
	 */
	INVALID_STATE,

}
