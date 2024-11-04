package bikeRouterApi;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)

public class SearchAreaService {
	private MongoDatabase db;

	public SearchAreaService() {
		db = MongoDBConnection.getDatabase();
	}

	@WebMethod(operationName = "SearchArea")
	public RouteListWrapper searchArea(@WebParam(name = "lng1") double lng1, @WebParam(name = "lat1") double lat1,
			@WebParam(name = "lng2") double lng2, @WebParam(name = "lat2") double lat2,
			@WebParam(name = "lng3") double lng3, @WebParam(name = "lat3") double lat3,
			@WebParam(name = "lng4") double lng4, @WebParam(name = "lat4") double lat4) {
		double[][] squareCoordinates2d = { { lng1, lat1 }, { lng2, lat2 }, { lng3, lat3 }, { lng4, lat4 } };
		MongoCollection<Document> collection = db.getCollection("routes");
		MongoDBService service = new MongoDBService(collection);
		RouteListWrapper wrapper = new RouteListWrapper();

		try {
			wrapper.setRoutes(service.getRoutesWithinSquare(squareCoordinates2d));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wrapper;
	}

	@WebMethod(operationName = "Hello")
	public String displayHello(@WebParam(name = "Name") String name) {
		return "Hello everyone from " + name;
	}
}
