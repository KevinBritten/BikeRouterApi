package bikeRouterApi.filter;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "http://localhost:3000");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Headers",
				"Content-Type, Accept, X-Requested-With");
	}
}