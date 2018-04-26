package command3.command;

import command3.receiver.Stereo;

public class StereoOffCommand implements Command {

	Stereo stereo;
	
	public StereoOffCommand(Stereo stereo) {
		super();
		this.stereo = stereo;
	}

	@Override
	public void execute() {
		stereo.off();
	}

}
