package ru.nsu.ccfit.pm.econ.view.shared.guice;

import com.google.inject.Key;

/**
 * Guice injection visitor interface to enable injection for object
 * hierarchies that were constructed without using Guice.
 * @see InjectionVisitorImpl
 * @see InjectionVisitable
 * @author dragonfly
 */
public interface InjectionVisitor {
	
	/**
	 * Tells visitor to perform inject on this object.
	 * @param obj Object to inject into.
	 */
	void injectInto(Object obj);
	
	/**
	 * Tells visitor to make this object accessible for injection 
	 * into other objects using specified key (class, class plus 
	 * annotation, etc.).
	 * @param <T> Type of object to make injectable.
	 * @param key Key to bind object to.
	 * @param instance Object to make injectable.
	 */
	<T> 
	void makeInjectable(Key<T> key, T instance);

}
