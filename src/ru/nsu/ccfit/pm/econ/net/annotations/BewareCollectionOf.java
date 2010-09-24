package ru.nsu.ccfit.pm.econ.net.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that the annotated field is a collection
 * of certain base type.
 * <p>This annotation is used together with {@link SerializeThis}
 * annotation to provide metadata to serialization mechanism.</p>
 * @author dragonfly
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface BewareCollectionOf {
	
	/**
	 * Base class of elements in the collection.
	 */
	Class<? extends Object> value();

}
