package command3.command;

import command3.receiver.Stereo;

public class StereoOnWithCDCommand implements Command {

	Stereo stereo;
	
	public StereoOnWithCDCommand(Stereo stereo) {
		super();
		this.stereo = stereo;
	}

	@Override
	public void execute() {
		stereo.on();
		stereo.setCD();
		stereo.setVolumn(10);
	}

}
