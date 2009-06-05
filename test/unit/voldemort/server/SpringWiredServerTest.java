package voldemort.server;

import junit.framework.TestCase;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;

public class SpringWiredServerTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        new ClassPathXmlApplicationContext("voldemort/config/spring-server-ctx.xml");
    }

    public void testSpringWiredServer() {
        ClientConfig config = new ClientConfig();
        config.setBootstrapUrls("tcp://localhost:6666");
        StoreClientFactory storeClientFactory = new SocketStoreClientFactory(config);
        StoreClient<String, String> storeClient = storeClientFactory.getStoreClient("test");
        storeClient.put("foo", "bar");
        String value = storeClient.getValue("foo");
        assertEquals("bar", value);
    }

}
