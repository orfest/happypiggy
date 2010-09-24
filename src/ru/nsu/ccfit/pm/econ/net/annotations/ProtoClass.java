package ru.nsu.ccfit.pm.econ.net.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation specifies to which proto-class given class should be serialized
 * 
 * @author orfest
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface ProtoClass {
	
	/**
	 * Specifies proto-class which represents given class during network
	 * communication
	 * 
	 */
	Class<?> value();
}
