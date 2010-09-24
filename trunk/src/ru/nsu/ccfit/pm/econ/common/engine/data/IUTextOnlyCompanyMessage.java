package ru.nsu.ccfit.pm.econ.common.engine.data;


/**
 * Base unmodifiable interface for company message data objects. 
 * Company message may be either official news or rumor.
 * <p>This interface provides only textual information. For methods 
 * that reflect message effect and receivers see 
 * {@link IUCompanyMessage}.</p>
 * <p>Due to the nature of this interface its primary use is in 
 * client code (since client should not require any additional 
 * information).</p>
 * @see IUCompanyMessage
 * @author dragonfly
 */
public interface IUTextOnlyCompanyMessage {
	
	/**
	 * Company message identifier. This identifier should be unique 
	 * within one scenario and one game session.
	 * @return Company message unique identifier.
	 */
	long getId();
	
	/**
	 * Type of message.
	 * @return Type of company message.
	 * @see CompanyMessageType
	 */
	CompanyMessageType getType();
	
	/**
	 * Identifier of the company this message applies to.
	 * @return Company identifier.
	 * @see IUCompany
	 */
	long getCompanyId();
	
	/**
	 * Message title. Short name or description of the message. 
	 * Should fit in one sentence.
	 * <p>This value must not be <tt>null</tt>. If not actually 
	 * present, {@link #getMessage()} value should be returned.</p>
	 * @return Message title.
	 */
	String getTitle();
	
	/**
	 * The actual message. May be quite lengthy and elaborate.
	 * @return Message text.
	 */
	String getMessage();
	
	/**
	 * Message publish time.
	 * @return Publish time or <tt>null</tt> if for whatever reason
	 * 			publish time is not available (this can happen if
	 * 			message is not actually published yet).
	 */
	IUGameTime getPublishTime();

}
