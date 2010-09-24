package ru.nsu.ccfit.pm.econ.view.client;

import org.apache.pivot.wtk.DesktopApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import ru.nsu.ccfit.pm.econ.modules.ClientModule;
import ru.nsu.ccfit.pm.econ.modules.LocalizationModule;
import ru.nsu.ccfit.pm.econ.view.shared.BaseApp;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitable;
import ru.nsu.ccfit.pm.econ.view.shared.guice.InjectionVisitorImpl;
import ru.nsu.ccfit.pm.econ.view.shared.localization.Localization;

/**
 * Main class for client application.
 * @author dragonfly
 */
public class ClientApp extends BaseApp {
	
	static final Logger logger = LoggerFactory.getLogger(ClientApp.class);
	
	private static final String WINDOW_WTKX = "ClientWindow.wtkx";
	
	protected void initInjection(InjectionVisitable root, Localization localization) {
		Injector bootstrapInjector = Guice.createInjector(
				new LocalizationModule(localization),
				new ClientModule()
			);
		InjectionVisitorImpl visitor = new InjectionVisitorImpl(bootstrapInjector);
		root.acceptInjectionVisitor(visitor);
		
		@SuppressWarnings("unused")
		Injector fullInjector = visitor.createFullInjector();
		// TODO instantiate actual "main" class
	}
	
	@Override
	protected String getWindowWTKX() {
		return WINDOW_WTKX;
	}

	public static void main(String[] args) {
		logger.debug("Starting client application");
		DesktopApplicationContext.main(ClientApp.class, args);
	}

}
