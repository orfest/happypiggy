package ru.nsu.ccfit.pm.econ.net.engine.roles;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;
import ru.nsu.ccfit.pm.econ.net.annotations.ProtoClass;
import ru.nsu.ccfit.pm.econ.net.annotations.SerializeThis;
import ru.nsu.ccfit.pm.econ.net.protos.AllGameEventsProtos.PlayerProto;

/**
 * @author orfest
 *
 */
@ProtoClass(value=PlayerProto.class)
public class Player implements IUPlayer {

	@SerializeThis
	private long id = Long.MAX_VALUE;
	@SerializeThis
	private IUPersonDescription unmodifiablePersonDescription = new PersonDescription();

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return unmodifiablePersonDescription;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUnmodifiablePersonDescription(
			IUPersonDescription unmodifiablePersonDescription) {
		this.unmodifiablePersonDescription = unmodifiablePersonDescription;
	}

}
