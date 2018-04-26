package command3;

import command3.command.Command;
import command3.command.LightOffCommand;
import command3.command.LightOnCommand;
import command3.command.MacroCommand;
import command3.command.StereoOffCommand;
import command3.command.StereoOnWithCDCommand;
import command3.invoker.RemoteController;
import command3.receiver.Light;
import command3.receiver.Stereo;

// Client
public class RemoteLoaderClient {
	public static void main(String[] args) {
		
		// Invoker
		RemoteController remoteControl = new RemoteController();
		
		// Receiver
		Light livingRoomLight = new Light("Living Room");
		Light kitchenLight = new Light("Kitchen");
		Stereo stereo = new Stereo("Living Room");
		
		// Command
		LightOnCommand livingRoomOn = new LightOnCommand(livingRoomLight);
		LightOffCommand livingRoomOff = new LightOffCommand(livingRoomLight);
		
		LightOnCommand kitchenOn = new LightOnCommand(kitchenLight);
		LightOffCommand kitchenOff = new LightOffCommand(kitchenLight);
		
		StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
		StereoOffCommand stereoOff = new StereoOffCommand(stereo);
		
		// init
		remoteControl.setCommand(0, livingRoomOn, livingRoomOff);
		remoteControl.setCommand(1, kitchenOn, kitchenOff);
		remoteControl.setCommand(2, stereoOnWithCD, stereoOff);
		
		System.out.println(remoteControl);
		
		// invoke
		for(int i=0; i<7;i++) {
			remoteControl.onButtonWasPressed(i);
			remoteControl.offButtonWasPressed(i);
		}
		
		System.out.println("====================================\n");
		
		Command[] partyOn = {livingRoomOn, kitchenOn, stereoOnWithCD};
		Command[] partyOff = {livingRoomOff, kitchenOff, stereoOff};
		
		MacroCommand partyOnCommand = new MacroCommand(partyOn);
		MacroCommand partyOffCommand = new MacroCommand(partyOff);
		
		remoteControl.setCommand(3, partyOnCommand, partyOffCommand);
		remoteControl.onButtonWasPressed(3);
		remoteControl.offButtonWasPressed(3);
		
	}
}
