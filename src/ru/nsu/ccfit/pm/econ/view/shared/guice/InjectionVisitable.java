package ru.nsu.ccfit.pm.econ.view.shared.guice;

/**
 * Visitable interface for classes that require injection on their 
 * instances. Should be implemented by all objects in non-Guice 
 * hierarchy where certain objects need injection via Guice. More 
 * precisely, only objects that require injection or are used to 
 * access those objects should implement this interface.
 * @see InjectionVisitor
 * @author dragonfly
 */
public interface InjectionVisitable {
	
	/**
	 * Accepts injection visitor.
	 * <p>Usual implementation (assuming it is implemented by 
	 * <tt>FooContainer</tt> class) looks like:
	 * <listing>
	 * public void acceptInjectionVisitor(InjectionVisitor visitor) {
	 *   visitor.needsInjection(this);
	 *   visitor.makeInjectable(Key.get(FooContainer.class), this);
	 *   for (InjectionVisitable child : children) {
	 *     child.acceptInjectionVisitor(visitor);
	 *   }
	 * }
	 * </listing>
	 * </p>
	 * @param visitor
	 */
	void acceptInjectionVisitor(InjectionVisitor visitor);

}
