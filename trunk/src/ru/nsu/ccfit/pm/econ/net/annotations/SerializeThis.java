package ru.nsu.ccfit.pm.econ.net.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to store serialization metadata.
 * <p>Fields that should be serialized are annotated with this 
 * annotation. Optionally getter and setter names can be 
 * specified.</p>
 * @author dragonfly
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SerializeThis {
	
	/**
	 * Getter name for the annotated field. 
	 * <p>Default value is empty string, which denotes that internal 
	 * getter name generation scheme should be applied.</p>
	 */
	String get() default "";
	
	/**
	 * Setter name for the annotated field. 
	 * <p>Default value is empty string, which denotes that internal 
	 * setter name generation scheme should be applied.</p>
	 */
	String set() default "";

}
