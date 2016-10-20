package example.cassandra.itest;

import java.time.Instant;
import java.util.UUID;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.flairbit.example.cassandra.api.Cassandra;

import junit.framework.TestCase;

public class IntegrationTest extends TestCase {
	
	BundleContext context = FrameworkUtil.getBundle(IntegrationTest.class).getBundleContext();
	
	public void test() throws Exception {
		ServiceReference<Cassandra> ref = context.getServiceReference(Cassandra.class);
		assertNotNull("No such service", ref);
		Cassandra cassandra = context.getService(ref);
		assertNotNull("Service object init error", cassandra);
		UUID key = cassandra.trace("test message generate at " + Instant.now());
		assertNotNull(key);
	}
}
