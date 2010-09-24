package ru.nsu.ccfit.pm.econ.view.shared.guice;

import java.util.ArrayList;
import java.util.List;

import com.google.gag.annotation.disclaimer.HandsOff;
import com.google.gag.annotation.remark.Magic;
import com.google.gag.annotation.remark.ShoutOutTo;
import com.google.gag.enumeration.Consequence;
import com.google.gag.enumeration.MagicType;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;

/**
 * Injection visitor implementation for two-stage injection. The first is 
 * "bootstrap" stage that can inject objects in non-Guice created object 
 * hierarchy with already defined bindings. The second stage produces a 
 * real injector with fully defined bindings that can be used by application
 * to inject all objects, including those from traversed hierarchy.
 * @see InjectionVisitor
 * @author dragonfly
 */
@ShoutOutTo("Daniel Martin, http://stackoverflow.com/questions/2856004/")
public class InjectionVisitorImpl implements InjectionVisitor {
	
	private final List<BindRecord<?>> bindings = new ArrayList<BindRecord<?>>();
	private final Injector injector;
	
	/**
	 * Constructs new <tt>InjectionVisitorImpl</tt> instance.
	 * @param injector Injector with bindings for stuff injected into
	 * 					hierarchy that would be traversed using this 
	 * 					visitor. Bindings for stuff that needs objects
	 * 					from yet-to-be-traversed hierarchy to be injected
	 * 					should be avoided.
	 */
	public InjectionVisitorImpl(Injector injector) {
		this.injector = injector;
	}

	@Override
	public void injectInto(Object obj) {
		injector.injectMembers(obj);
	}

	@Override
	public <T> void makeInjectable(Key<T> key, T instance) {
		StackTraceElement source = new RuntimeException().getStackTrace()[1];
		BindRecord<T> bindRecord = new BindRecord<T>(key, instance, source);
		bindings.add(bindRecord);
	}
	
	/**
	 * Constructs a new injector that is inherited from injector passed 
	 * into constructor, but also includes all necessary bindings to 
	 * objects from already traversed object hierarchy. That is, this 
	 * method produces injector with fully defined bindings that may
	 * be used by application.
	 * @param otherModules All additional modules needed by new injector,
	 * 						including stuff that needs objects from 
	 * 						traversed hierarchy to be injected into it.
	 * @return New injector with fully defined bindings.
	 */
	@Magic(type=MagicType.WHITE)
	public Injector createFullInjector(final Module... otherModules) {
		return injector.createChildInjector(new AbstractModule() {
			@Override
			protected void configure() {
				for (Module module : otherModules) {
					install(module);
				}
				
				for (BindRecord<?> binding : bindings) {
					addBinding(binding);
				}
			}
			
			@HandsOff(
					byOrderOf="Nicolay Vizovitin & Daniel Martin",
					onPainOf=Consequence.VOGON_POETRY_RECITAL)
			private <T> void addBinding(BindRecord<T> binding) {
				binder()
					.withSource(binding.bindSource)
					.bind(binding.key)
					.toInstance(binding.value);
			}
		});
	}
	
	private static class BindRecord<T> {
		
		Key<T> key;
		T value;
		StackTraceElement bindSource;
		
		public BindRecord(Key<T> key, T value, StackTraceElement bindSource) {
			this.key = key;
			this.value = value;
			this.bindSource = bindSource;
		}
		
	}

}
