package edu.eci.arsw.logger;

/**import com.azure.cosmos.*;
import com.azure.cosmos.models.CosmosContainerResponse;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosPatchOperations;
import com.azure.cosmos.models.PartitionKey;*/
import com.azure.data.cosmos.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

//-----------
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
/**
 * LiveCodingLog is a class that represents a log of live coding events.
 */
public class LiveCodingLog<DocumentClient> {
    HashMap<String, ArrayList<Event>> events = new HashMap<>();

    //Nombre y contenedor de la DBNoSQL
    private final String dbname = "ToDoList";
    private final String containername = "Items";

    //Base de datos y contenedor
    private CosmosDatabase database;
    private CosmosContainer container = null;
    private CosmosClient client;

    public LiveCodingLog() {}

    public void connectToDB() throws Exception {
        ConnectionPolicy connectionPolicy = new ConnectionPolicy();
        connectionPolicy.connectionMode(ConnectionMode.DIRECT);
        CosmosClient cosmosClient = CosmosClient.builder()
                .endpoint("AccountEndpoint=https://livecoding-nosql.documents.azure.com:443/;AccountKey=WrvVYD6AC1sEe3VwonIuWSCoyzWAIpBIpXoOobgsSSuq7fwrIBdHkV57u7euFXX9iNnJZTJ5cblZamRqkAQSyQ==;")
                .key("WrvVYD6AC1sEe3VwonIuWSCoyzWAIpBIpXoOobgsSSuq7fwrIBdHkV57u7euFXX9iNnJZTJ5cblZamRqkAQSyQ==")
                .connectionPolicy(connectionPolicy)
                .build();
        /**client = new CosmosClientBuilder()
                .endpoint("https://livecoding-nosql.documents.azure.com:443/")
                .key("WrvVYD6AC1sEe3VwonIuWSCoyzWAIpBIpXoOobgsSSuq7fwrIBdHkV57u7euFXX9iNnJZTJ5cblZamRqkAQSyQ==")
                //  Setting the preferred location to Cosmos DB Account region
                //  West US is just an example. User should set preferred location to the Cosmos DB region closest to the application
                .preferredRegions(Collections.singletonList("East US"))
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                //  Setting content response on write enabled, which enables the SDK to return response on write operations.
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();*/
    }

    public void saveIntoDB(String idRoom) throws Exception {
        connectToDB();
        database = client.getDatabase(dbname);
        container = database.getContainer(containername);
        System.out.println("<=============Antes de convertir en JSONArray method saveIntoDB==============>");
        System.out.println(events.get(idRoom));
        JSONObject eJSON = new JSONObject(events.get(idRoom));
        eJSON.put("id", idRoom);
        Map<String, Object> tempCache = eJSON.toMap();
        System.out.println("<----------A rezar que la cosmos sirva---------->");
        
        System.out.println("<----------------Lo guardo el DBNoSQL (espero)---------------->");
        client.close();
    }

    public void createEvent(String idRoom, String activity, String user, String type){
        Event evt = new Event(activity,user,type);
        System.out.println("<==========Guardando el evento en cache antes del if=========>");
        System.out.println(evt.toString());
        if (events.get(idRoom)!=null){
            System.out.println("<==========Guardando el evento en cache despues del if=========>");
            System.out.println(evt.toString());
            events.get(idRoom).add(evt);
        } else {
            System.out.println("<==========Guardando el evento en cache en el ELSE=========>");
            System.out.println(evt.toString());
            ArrayList<Event> evts = new ArrayList<>();
            evts.add(evt);
            events.put(idRoom,evts);
        }

        System.out.println("<=============Antes de convertir en JSONArray==============>");
        System.out.println(events.get(idRoom));

    }

}