package dev.darrenmatthews.csgo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import uk.oczadly.karl.csgsi.GSIObserver;
import uk.oczadly.karl.csgsi.GSIServer;
import uk.oczadly.karl.csgsi.config.GSIConfig;
import uk.oczadly.karl.csgsi.config.SteamDirectoryException;
import uk.oczadly.karl.csgsi.config.SteamUtils;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		createConfig();

		startServer();

		Thread.currentThread().join(); // Prevent application exit by waiting for thread interrupt (for testing only)
	}

	private static void startServer() throws IOException {
		// Create a new observer (for this example, using a lambda)
		GSIObserver observer = new GameObserver();

		// Configure server on port 1337, requiring the specified "password" auth token
		GSIServer server = new GSIServer(1337, Map.of("password", "Q79v5tcxVQ8u"));
		server.registerObserver(observer); // Register our observer object
		server.startServer(); // Start the server in a new thread (on the above specified port)

		System.out.println("Server started. Listening for state data...");
	}

	private static void createConfig() {
		GSIConfig config = new GSIConfig(1337).setDescription("Test service for CSGO-GSI").setTimeoutPeriod(1.0)
				.setBufferPeriod(0.5).setAuthToken("password", "Q79v5tcxVQ8u").setAllDataComponents();

		try {
			// Locate the CSGO configuration folder
			Path configPath = SteamUtils.locateCsgoConfigFolder();

			if (configPath != null) {
				// Create the service config file
				GSIConfig.createConfig(configPath, config, "testing");
				System.out.println("Config successfully created!");
			} else {
				System.out.println("Couldn't locate CS:GO directory");
			}
		} catch (SteamDirectoryException e) {
			System.out.println("Couldn't locate Steam installation directory");
		} catch (IOException e) {
			System.out.println("Couldn't write configuration file");
		}

	}

}
