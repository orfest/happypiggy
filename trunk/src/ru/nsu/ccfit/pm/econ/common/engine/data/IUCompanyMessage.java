package ru.nsu.ccfit.pm.econ.common.engine.data;

import java.util.Collection;

import javax.annotation.Nullable;

import ru.nsu.ccfit.pm.econ.common.engine.roles.IUPlayer;

/**
 * Unmodifiable interface for company message data objects. 
 * <p>This interface is intended for use in server and scenario editor.
 * Apart from the basic textual information it includes info on 
 * the message effect, its receivers and its state.</p>
 * @see IUTextOnlyCompanyMessage
 * @author dragonfly
 */
public interface IUCompanyMessage extends IUTextOnlyCompanyMessage {
	
	/**
	 * Whether this message is already published or not. 
	 * <p>If it is published:
	 * <ol>
	 * <li>{@link #getReceivers()} provides players that received or 
	 * should receive this message;</li>
	 * <li>{@link IUTextOnlyCompanyMessage#getPublishTime()} provides
	 * publish time (otherwise this value is <tt>null</tt>).</li>
	 * </ol>
	 * </p>
	 * @return <tt>true</tt> if message is already published, 
	 * 			<tt>false</tt> otherwise.
	 */
	boolean isPublished();
	
	/**
	 * @return Modifier <tt>k</tt> of expected profit.
	 * @see {@link #effectOnExpectedProfit(double)}
	 */
	double getCoefficientK();
	
	/**
	 * @return Modifier <tt>c</tt> of expected profit.
	 * @see {@link #effectOnExpectedProfit(double)}
	 */
	double getCoefficientC();
	
	/**
	 * Calculates effect of this message on expected profit.
	 * <p>Effect should be calculated using following formula:
	 * <listing>
	 * 	newExpectedProfit = oldExpectedProfit * k + c
	 * </listing>
	 * </p>
	 * @param oldExpectedProfit Current (old) value of expected profit. 
	 * 						See {@link IUCompany#getExpectedProfit()}.
	 * @return New value of expected company profit.
	 * @see IUTextOnlyCompanyMessage#getCompanyId()
	 * @see #getCoefficientK()
	 * @see #getCoefficientC()
	 */
	double effectOnExpectedProfit(double oldExpectedProfit);
	
	/**
	 * Set of receivers of this message.
	 * @return Set of receivers of this message, or <tt>null</tt> if
	 * 			the data is not available. The latter indicates that 
	 * 			either message is not yet published or that caller in 
	 * 			the current environment should not need the data (i.e.
	 * 			the call happens in the client). 
	 */
	@Nullable
	Collection<? extends IUPlayer> getReceivers();

}
