package ru.nsu.ccfit.pm.econ.modules;

import ru.nsu.ccfit.pm.econ.engine.AnswerSender;
import ru.nsu.ccfit.pm.econ.engine.BaseHandlerPort;
import ru.nsu.ccfit.pm.econ.engine.EngineDispatcher;
import ru.nsu.ccfit.pm.econ.engine.IAnswerSender;
import ru.nsu.ccfit.pm.econ.engine.IEngineDispatcher;
import ru.nsu.ccfit.pm.econ.engine.NetworkingPort;
import ru.nsu.ccfit.pm.econ.engine.PlayerControllerPort;
import ru.nsu.ccfit.pm.econ.engine.ScenarioControllerPort;
import ru.nsu.ccfit.pm.econ.modules.names.ToNetworking;
import ru.nsu.ccfit.pm.econ.modules.names.ToPlayerController;
import ru.nsu.ccfit.pm.econ.modules.names.ToScenarioController;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 * Binds classes inside Engine component.
 * @author dragonfly
 */
class EngineModule extends AbstractModule {

	@Override
	protected void configure() {
		// It is probably better to make default visibility for AnswerSender
		// and move this module to appropriate engine package. Ah, well... 
		bind(IAnswerSender.class)
				.to(AnswerSender.class);
		bind(IEngineDispatcher.class)
				.to(EngineDispatcher.class);
		
		bind(BaseHandlerPort.class)
				.annotatedWith(ToNetworking.class)
				.to(NetworkingPort.class);
		bind(BaseHandlerPort.class)
				.annotatedWith(ToPlayerController.class)
				.to(PlayerControllerPort.class);
		bind(BaseHandlerPort.class)
				.annotatedWith(ToScenarioController.class)
				.to(ScenarioControllerPort.class);
		
		singletoniateEmUp();
	}
	
	private void singletoniateEmUp() {
		bind(AnswerSender.class).in(Scopes.SINGLETON);
		bind(EngineDispatcher.class).in(Scopes.SINGLETON);
		
		bind(NetworkingPort.class).in(Scopes.SINGLETON);
		bind(PlayerControllerPort.class).in(Scopes.SINGLETON);
		bind(ScenarioControllerPort.class).in(Scopes.SINGLETON);
	}

}
