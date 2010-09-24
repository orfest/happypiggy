package ru.nsu.ccfit.pm.econ.common.controller.scenario;

import java.io.File;

import javax.annotation.Nullable;

/**
 * Primary interface of ScenarioController. Used to load scenarios
 * into engine.
 * <p>Components that may use this interface:
 * <ul><li>ServerUI</li></ul>
 * </p>
 * <p>This interface should be implemented by ScenarioController 
 * component.</p>
 * @author dragonfly
 */
public interface IScenarioController {
	
	/**
	 * Loads scenario into game engine. 
	 * @param filename Name of file to load scenario from.
	 * @return Scenario load status.
	 */
	ScenarioLoadStatus loadScenario(String filename);
	
	/**
	 * Loads scenario into game engine.
	 * @param file File to load scenario from.
	 * @return Scenario load status.
	 */
	ScenarioLoadStatus loadScenario(File file);
	
	/**
	 * Loaded game scenario.
	 * @return Game scenario or <tt>null</tt> if scenario is 
	 * 			not yet available.
	 */
	@Nullable
	IUScenario getScenario();
	
	/**
	 * Result of scenario load operation. 
	 * <p>There is only one successful result - {@link #OK}.</p>
	 * @author dragonfly
	 */
	enum ScenarioLoadStatus {
		/**
		 * Scenario loaded successfully.
		 */
		OK,
		
		/**
		 * Scenario file was not found.
		 */
		FILE_NOT_FOUND,
		
		/**
		 * Access to scenario file is denied for some reason.
		 */
		ACCESS_DENIED,
		
		/**
		 * Scenario file was found and read, but file format version 
		 * is incompatible.
		 */
		INVALID_FILE_VERSION,
		
		/**
		 * Scenario file was found, but has unknown format or is 
		 * corrupted.
		 */
		INVALID_FILE_FORMAT,
		
		/**
		 * Engine is in invalid state with regard to scenario loading.
		 * I.e. engine refused to accept new scenario object.
		 * <p>E.g. game has already started and therefore another 
		 * scenario cannot be loaded.</p> 
		 */
		INVALID_STATE,
		
		/**
		 * Generic failure. I.e. any other failure.
		 */
		GENERIC_FAILURE,
	}

}
