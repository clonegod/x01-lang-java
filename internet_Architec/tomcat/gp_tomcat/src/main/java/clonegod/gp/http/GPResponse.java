package clonegod.gp.http;

import java.io.IOException;
import java.io.OutputStream;

public class GPResponse {
	
	OutputStream out;

	public GPResponse(OutputStream out) {
		this.out = out;
	}
	
	public void write(String content) throws IOException {
		out.write(content.getBytes());
		out.flush();
	}
}
