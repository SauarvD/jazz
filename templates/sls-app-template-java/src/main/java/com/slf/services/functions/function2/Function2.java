package com.slf.services;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.slf.exceptions.BadRequestException;
import com.slf.model.Response;
import org.apache.log4j.Logger;

/**
 * Java template for authoring AWS Serverless applications with deployment descriptor.
  This implementation is based on the interface
 * 'RequestHandler' with custom POJO Input/Output
 *
 * @author
 * @version 1.2
 * @date
 *
 */

public class Function2 extends BaseRequestHandler {

    /**
     * Override and implement this method from BaseRequestHandler. This method would have the main
     * processing logic to serve the request from User
     */
	 public Response execute(Map<String, Object> input, Context context) {

		/* Request payload will be available in 'this.body' field as a Map. For lambda it will be same as input */
		if(this.body==null || this.body.isEmpty()) {
			throw new BadRequestException("Invalid or empty input payload");
		}

    logger.info("Sample log for function2");
        /* Sample output data */
		HashMap<String, String> data = new HashMap<String, String>();
		String val = (String) body.get("key");
		data.put("name", val);
    data.put("config_key", configObject.getConfig("config_key"));

		return new Response(data, this.body);
	 }
}
