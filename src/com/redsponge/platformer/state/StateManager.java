package com.redsponge.platformer.state;

import com.redsponge.platformer.handler.Handler;
import com.redsponge.redutils.console.ConsoleMSG;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
	
	private static Map<String, AbstractState> states;
	
	private static AbstractState currentState;
	
	private static boolean inited;
	
	public static void init(Handler handler) {
		if(inited) {
			ConsoleMSG.WARNING.info("TRIED TO REINITIATE STATE MANAGER, ABORTING");
			return;
		}
		inited = true;
		states = new HashMap<String, AbstractState>();
		states.put("level", new StateLevel(handler));
	}
	
	public static void setCurrentState(String state) throws StateNotFoundException {
		if(states.get(state) == null) {
			throw new StateNotFoundException(state);
		}
		StateManager.currentState = states.get(state);
	}

	public static StateLevel getLevelState() {
		try {
			return (StateLevel) currentState;
		} catch (Exception e) {
			throw new StateNotStateLevelException();
		}
	}
	
	public static AbstractState getCurrentState() {
		return currentState;
	}
}
