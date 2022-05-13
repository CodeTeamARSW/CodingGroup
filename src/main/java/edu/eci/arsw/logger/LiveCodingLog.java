package edu.eci.arsw.logger;

import com.azure.cosmos.CosmosAsyncClient;
import com.azure.cosmos.CosmosClientBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

//-----------
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Service
/**
 * LiveCodingLog is a class that represents a log of live coding events.
 */
public class LiveCodingLog<DocumentClient> {
    ArrayList<Event> events = new ArrayList<>();

    private CosmosAsyncClient client;

    public LiveCodingLog() {}

    public void saveIntoDB(){

    }
    public String connectToDB() throws Exception {
        client = new CosmosClientBuilder()
                .endpoint(AccountSettings.HOST)
                .key(AccountSettings.MASTER_KEY)
                //  Setting the preferred location to Cosmos DB Account region
                //  West US is just an example. User should set preferred location to the Cosmos DB region closest to the application
                .preferredRegions(Collections.singletonList("West US"))
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                //  Setting content response on write enabled, which enables the SDK to return response on write operations.
                .contentResponseOnWriteEnabled(true)
                .buildAsyncClient();

    }

}
