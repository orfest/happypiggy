package ru.nsu.ccfit.pm.econ.common.controller.player;

/**
 * Game event gateway client interface.
 * 
 * @author dragonfly
 */
public interface IGameEventGatewayClient {
	
	/**
	 * Sets gateway. 
	 * <p>Implementations would often use <tt>@Inject</tt> on 
	 * this method.</p>
	 * @param gateway PlayerController game event gateway to set.
	 */
	void setGameEventGateway(IGameEventGateway gateway);

}
