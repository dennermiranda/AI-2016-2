package lab;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.agent.impl.DynamicAction;

public class AmatriceEnviroment extends AbstractEnvironment {
	
	public int performance;
	// Add new Actions
	public static final Action ACTION_MOVE_FORWARD = new DynamicAction("Forward");
	public static final Action ACTION_GRAB = new DynamicAction("Grab");
	public static final Action ACTION_TAKE_OFF = new DynamicAction("Take Off");
	public static final Action ACTION_TURN_RIGHT = new DynamicAction("Turn Right by 90 degrees");
	public static final Action ACTION_TURN_LEFT = new DynamicAction("Turn Left by 90 degrees");

	public enum LocationState {
		Human, None, Rock
	};

	protected AmatriceEnvironmentState envState = null;

	public AmatriceEnviroment() {
		envState = new AmatriceEnvironmentState();
		performance = 0;
	}

	@Override
	public void executeAction(Agent agent, Action action) {
		if (ACTION_MOVE_FORWARD == action) {
			--performance;
			
			LocationState state = envState.getLocationState(envState.getAgentLocation(agent));
			if (state == LocationState.None) {
				System.out.println("nothing here");
			} else if (state == LocationState.Rock) {
				System.out.println("Meh... a rock");
			}
			
			// get current agent location
			String currentlocation = envState.getAgentLocation(agent);
			
			// increment current agent location
			// remember: its a sequence!
			
			int nextLocation = Integer.parseInt(currentlocation) + 1;
			envState.setLocationState(currentlocation, LocationState.None);
			envState.setAgentLocation(agent, nextLocation + "");

		}
		if (ACTION_GRAB == action) {
			
			String currentLocation = envState.getAgentLocation(agent);

			LocationState state = envState.getLocationState(currentLocation);
			if (state == LocationState.Human) {
				System.out.println("A HUMAN!! WHAT SHOULD I DO??");
				
				

				// update agent performance
				performance = performance +100;
				
				envState.setLocationState(currentLocation, LocationState.None);
				//Agent returns to the origin with the human
				envState.setAgentLocation(agent, "1");
				// delete state
				// (Since you grabbed the human you have to remove it from the
				// environment).
				}

		}
		
		if (ACTION_TURN_LEFT == action){
			--performance;
			
			LocationState state = envState.getLocationState(envState.getAgentLocation(agent));
			
			if (state == LocationState.None) {
				System.out.println("nothing here");
			} else if (state == LocationState.Rock) {
				System.out.println("Meh... a rock");
			}
			
			
			String currentlocation = envState.getAgentLocation(agent);
			int nextLocation = Integer.parseInt(currentlocation) - 20;
			
			if (nextLocation > 0){
				
				envState.setLocationState(currentlocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else{
				System.out.println("You can't turn left right now!");
			}
			
		}
		
		if (ACTION_TURN_RIGHT == action){
			--performance;
			LocationState state = envState.getLocationState(envState.getAgentLocation(agent));
			
			if (state == LocationState.None) {
				System.out.println("nothing here");
			} else if (state == LocationState.Rock) {
				System.out.println("Meh... a rock");
			}
			
			
			String currentlocation = envState.getAgentLocation(agent);
			int nextLocation = Integer.parseInt(currentlocation) +20;
			
			if (nextLocation <= 300){
				
				envState.setLocationState(currentlocation, LocationState.None);
				envState.setAgentLocation(agent, nextLocation + "");
			}
			else{
				System.out.println("You can't turn right right now!");
			}
			
		}
		// Complete the other actions
	}

	@Override
	public void addAgent(Agent a) {
		envState.setAgentLocation(a, "1");
		super.addAgent(a);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		String agentLocation = envState.getAgentLocation(anAgent);
		return new AmatriceEnvironmentPercept(agentLocation, envState.getLocationState(agentLocation));
	}

}
