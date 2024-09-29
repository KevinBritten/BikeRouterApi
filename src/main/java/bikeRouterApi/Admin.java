package bikeRouterApi;

import bikeRouterApi.MockDatabase.User;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class Admin {

	private MockDatabase md;

	public Admin() {
		md = MockDatabase.getInstance();
	}

	@Path("signup/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response signup(@FormParam("username") String username, @FormParam("password") String password) {

		md.addUser(new User(username, password));
		return Response.ok("Signup successful").build();

	}

	@Path("login/")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		User user = md.getUserByName(username);
		if (user != null && password.equals(user.getPassword())) {
			String jsonResponse = "{\"message\":\"Login successful\",\"userId\":" + user.getId() + "}";
			return Response.ok(jsonResponse).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
		}
	}

}
