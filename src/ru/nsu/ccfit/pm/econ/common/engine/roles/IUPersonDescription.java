package ru.nsu.ccfit.pm.econ.common.engine.roles;

import javax.annotation.Nullable;

/**
 * Unmodifiable person description data object interface.
 * 
 * @author dragonfly
 */
public interface IUPersonDescription {
	
	/**
	 * Person name. Usually in the form "Last_name Initials", but this format is not enforced.
	 * <p>E.g. this method could return <tt>"Визовитин Н.В."</tt>.</p>
	 * <p>This field can't be <tt>null</tt>.</p>
	 * @return Person name.
	 */
	String getName();
	
	/**
	 * Optional student group associated with a person. 
	 * Usually group is 4 or 3-digit number, but may be as well a String.
	 * <p>E.g. this method could return <tt>5201<tt>.</p> 
	 * @return Student group or <tt>null</tt> if none is specified. 
	 */
	@Nullable
	String getGroup();

}
