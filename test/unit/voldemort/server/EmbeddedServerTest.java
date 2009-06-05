package voldemort.server;

import junit.framework.TestCase;
import voldemort.client.*;
import voldemort.cluster.Cluster;
import voldemort.cluster.Node;
import voldemort.routing.RoutingStrategyType;
import voldemort.serialization.SerializerDefinition;
import voldemort.store.StoreDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmbeddedServerTest extends TestCase {

    private final Properties configProperties = new Properties();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        configProperties.put("node.id", "0");
        configProperties.put("voldemort.home", "/tmp");
        configProperties.put("bdb.cache.size", "50MB");

        List<Integer> partitions = new ArrayList<Integer>();
        partitions.add(0);
        partitions.add(1);
        Node node = new Node(0, "localhost", 8080, 6666, 8081, partitions);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        Cluster cluster = new Cluster("testCluster", nodes);

        List<StoreDefinition> storeDefs = new ArrayList<StoreDefinition>();
        SerializerDefinition keySerializer = new SerializerDefinition("json", "\"string\"");
        SerializerDefinition valSerializer = new SerializerDefinition("json", "\"string\"");
        StoreDefinition storeDef = new StoreDefinition("test", "memory", keySerializer, valSerializer, RoutingTier.CLIENT, RoutingStrategyType.CONSISTENT_STRATEGY, 1, 1, 1, 1, 1, 1);
        storeDefs.add(storeDef);

        VoldemortConfig config = new VoldemortConfig(configProperties);
        VoldemortMetadata metadata = new VoldemortMetadata(cluster, storeDefs, 0);

        VoldemortServer server = new VoldemortServer(config, metadata);
        server.start();
    }

    public void testEmbeddedServer() {
        ClientConfig config = new ClientConfig();
        config.setBootstrapUrls("tcp://localhost:6666");
        StoreClientFactory storeClientFactory = new SocketStoreClientFactory(config);
        StoreClient<String, String> storeClient = storeClientFactory.getStoreClient("test");
        storeClient.put("foo", "bar");
        String value = storeClient.getValue("foo");
        assertEquals("bar", value);
    }

}
