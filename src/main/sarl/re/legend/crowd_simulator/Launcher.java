package re.legend.crowd_simulator;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import io.sarl.bootstrap.SRE;
import re.legend.crowd_simulator.agents.environment.Environment;

public class Launcher {
	public static void main(String[] args) throws Exception {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.samples = 16;
	config.title = "CrowdSimulator";
	config.width = 1366;
	config.height = 768;
	
	SRE.getBootstrap().startAgent(Environment.class, config);
	}
}