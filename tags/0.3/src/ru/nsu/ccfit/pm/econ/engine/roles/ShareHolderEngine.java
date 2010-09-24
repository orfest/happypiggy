package ru.nsu.ccfit.pm.econ.engine.roles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUShareHolding;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPersonDescription;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUShareholder;
import ru.nsu.ccfit.pm.econ.engine.data.ShareHoldingEngine;

/**
 * IUShareholder implementation for engine
 * 
 * @author pupuatenko
 * 
 * @see IUShareholder
 */
public class ShareHolderEngine implements IUShareholder {

	private Collection<ShareHoldingEngine> shareList;
	private long id;
	private PersonDescriptionEngine personDescription;

	public ShareHolderEngine(Collection<ShareHoldingEngine> shareList, long id,
			PersonDescriptionEngine personDescription) {
		this.shareList = shareList;
		this.id = id;
		this.personDescription = personDescription;
	}

	public ShareHolderEngine(IUShareholder toCopy) {
		shareList = new ArrayList<ShareHoldingEngine>(toCopy
				.getUnmodifiableShareList().size());
		for (IUShareHolding sh : toCopy.getUnmodifiableShareList())
			shareList.add(new ShareHoldingEngine(sh));
		id = toCopy.getId();
		personDescription = new PersonDescriptionEngine(toCopy
				.getUnmodifiablePersonDescription());
	}

	@Override
	public Collection<? extends IUShareHolding> getUnmodifiableShareList() {
		return Collections.unmodifiableCollection(shareList);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public IUPersonDescription getUnmodifiablePersonDescription() {
		return (IUPersonDescription) personDescription;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<ShareHoldingEngine> getShareList() {
		return shareList;
	}

	public PersonDescriptionEngine getPersonDescription() {
		return personDescription;
	}

	public void setShareList(Collection<ShareHoldingEngine> shareList) {
		this.shareList = shareList;
	}

	public void setPersonDescription(PersonDescriptionEngine personDescription) {
		this.personDescription = personDescription;
	}

}
