package com.slf.util;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slf.exceptions.BadRequestException;

import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * The environment configuration reader class. Environment configurations can be specified in a properties file.
 * Each environment will be having a separate properties file that is located under the respective folder
 * that is a name of lambda function without environment suffix though.
  For ex. FunctionX/dev.properties for 'DEV'
 *
 * Usage:
 * EnvironmentConfig configObject = new EnvironmentConfig(context);
 * String config_value = configObject.getConfig("config_key");

 * @author
 * @version
 *
 */
public class EnvironmentConfig {

	static final Logger logger = Logger.getLogger(EnvironmentConfig.class);

	private static Properties props = new Properties();
	private static String stage = null;

	public EnvironmentConfig (Context context) throws Exception {
		String fnName = context.getFunctionName();
		String fnNameNoEnv = null;
		String fnNoPrefix = null;

		if(null != fnName) {
			int lastIndx = fnName.lastIndexOf("-");
			fnNoPrefix = fnName.substring(lastIndx+1);
			fnNameNoEnv = fnName.substring(0, lastIndx);
			int preIndx = fnNameNoEnv.lastIndexOf("-");
			if(preIndx < 0) { stage = fnNameNoEnv; }
			else {
				stage = fnNameNoEnv.substring(preIndx+1);
			}

		}

		if(stage.isEmpty()) {
			throw new BadRequestException("Invalid Stage. Can't load ENV configurations");
		}

		String configFile = "/functions/"+fnNoPrefix+"/"+stage+".properties";
		logger.info("Loading configuration file for env..:"+configFile);
		props.load(this.getClass().getResourceAsStream(configFile));
	}

	public String getConfig(String key) {
		if(props != null) {
			String value = props.getProperty(key);
			return value;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Loaded config for "+stage;
	}
}
