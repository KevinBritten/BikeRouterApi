package bikeRouterApi;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)

public class SearchAreaService {
//	private MongoDatabase db;
//
//	public SearchAreaService() {
//		db = MongoDBConnection.getDatabase();
//	}
//
//	@WebMethod(operationName = "SearchArea")
//	public String searchArea(@WebParam(name = "squareCoordinates") double[][] squareCoordinates) {
//		MongoCollection<Document> collection = db.getCollection("routes");
//		MongoDBService service = new MongoDBService(collection);
//		return "s";
//		try {
//			return service.getRoutesWithinSquare(squareCoordinates);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

	@WebMethod(operationName = "SearchArea")
	public String displayHello(@WebParam(name = "Name") String name) {
		return "Hello everyone from " + name;
	}
}
