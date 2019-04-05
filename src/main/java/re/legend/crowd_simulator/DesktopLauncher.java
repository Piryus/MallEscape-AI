package re.legend.crowd_simulator;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String args[]) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 16;
		config.title = "CrowdSimulator";
		config.width = 1366;
		config.height = 768;
		new LwjglApplication(new SimulationApplication(), config);
	}
}
