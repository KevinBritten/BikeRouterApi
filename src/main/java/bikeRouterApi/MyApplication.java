package bikeRouterApi;

import org.glassfish.jersey.server.ResourceConfig;

import bikeRouterApi.filter.RestResponseFilter;

public class MyApplication extends ResourceConfig {
	public MyApplication() {
		register(RestResponseFilter.class);
	}
}