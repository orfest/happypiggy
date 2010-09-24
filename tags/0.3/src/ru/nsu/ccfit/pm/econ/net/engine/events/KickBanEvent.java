/**
 * 
 */
package ru.nsu.ccfit.pm.econ.net.engine.events;

/**
 * @author orfest
 *
 */
public class KickBanEvent extends GameEvent implements IUKickBanEvent {

	private String reason = "";
	private boolean banned = false;
	
	
	public KickBanEvent() {
	}


	@Override
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public boolean getBanned() {
		return banned;
	}


	public void setBanned(boolean banned) {
		this.banned = banned;
	}

}
