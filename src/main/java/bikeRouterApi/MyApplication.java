package bikeRouterApi;

import org.glassfish.jersey.server.ResourceConfig;

import bikeRouterApi.filter.RestResponseFilter;

public class MyApplication extends ResourceConfig {
	public MyApplication() {
		// Register the CORS filter
		register(RestResponseFilter.class);

		// Optionally register other resources
		// packages("com.yourapp.resources");
	}
}