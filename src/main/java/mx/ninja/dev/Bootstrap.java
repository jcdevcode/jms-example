package mx.ninja.dev;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.ninja.dev.cl.Command;

/**
 * Main class of application
 * 
 * @author Julio Bolaños
 *
 */
public class Bootstrap {
	/**
	 * Logger for the class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

	/**
	 * Main method invoked to start the app.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 1) {
			Properties config = loadConfigurationFile(args[0]);
			Command command = extractCommand(args[1]);
			JMSApplication.start(config, command);
		} else {
			LOG.error("Wrong number of arguments.");
			LOG.error("- The path to config file not be null or empty.");
			LOG.error("- The command of application not be null.");
			System.exit(1);
		}
	}
	

	/**
	 * Load properties file.
	 * 
	 * @param args
	 */
	private static Properties loadConfigurationFile(String configFilePath) {
		Properties configuration = null;
		final File configFile = new File(configFilePath);
		if (configFile.exists() && configFile.isFile()) {
			configuration = new Properties();
			try (final FileInputStream input = new FileInputStream(configFile);) {				
				configuration.load(input);
			} catch (IOException ex) {
				LOG.error("Error loading configuration file.", ex);
				System.exit(1);
			}
		} else {
			LOG.error("Configuration file doesn't exists or is not a file.");
			System.exit(1);
		}
		return configuration;
	}

	/**
	 * Extract command from invocation.
	 * 
	 * @param command
	 *            argument
	 * @return
	 */
	private static Command extractCommand(String cmdLine) {
		Command cmd = null;
		cmd = Command.getEnum(cmdLine);
		if(cmd == null){
			LOG.error("The command is not recognized. Commands available: {}", Command.valuesList());
			System.exit(1);
		}
		return cmd;
	}

}
