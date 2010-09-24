package ru.nsu.ccfit.pm.econ.common.controller.player;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.data.IUCompany;
import ru.nsu.ccfit.pm.econ.common.engine.data.IUTextOnlyCompanyMessage;
import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Company roster interface. Provides access to company list.
 * @see IUCompany
 *  
 * @author dragonfly
 */
public interface ICompanyRoster {
	
	/**
	 * @return List of all companies, sorted by their identifiers.
	 */
	List<? extends IUCompany> getCompanyList();
	
	/**
	 * Finds company by its identifier.
	 * @param companyId Company identifier.
	 * @return Company with identifier equal to <tt>companyId</tt>
	 * 			or <tt>null</tt> if such company does not exist.
	 */
	@Nullable
	IUCompany getCompanyById(long companyId);
	
	/**
	 * Finds company by its name.
	 * @param companyName Company name.
	 * @return Company with name equal to <tt>companyName</tt>
	 * 			or <tt>null</tt> if such company does not exist.
	 */
	@Nullable
	IUCompany getCompanyByName(String companyName);
	
	void publishOfficialMessage(IUTextOnlyCompanyMessage message);
	
	void publishOfficialMessage(long messageId);
	
	void publishRumorMessage(IUTextOnlyCompanyMessage message, Collection<? extends IUPlayer> receivers);
	
	void publishRumorMessage(long messageId, Collection<Long> receiverIds);

}
