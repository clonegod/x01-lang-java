package command3;

import command3.command.LightOnCommand;
import command3.invoker.SimpleRemoteController;
import command3.receiver.Light;

/**
 * Client
 *
 */
public class SimpleRemoteControllerClient {
	public static void main(String[] args) {
		SimpleRemoteController remote = new SimpleRemoteController();
		
		Light light = new Light("my light");
		LightOnCommand lightOn = new LightOnCommand(light);
		remote.setCommand(lightOn);
		
		remote.buttonWasPressed();
	}
}
