package br.com.creativesource.tools.storage.hazelcast.krati;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Queue;

import krati.store.DataStore;

import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class KratiDataStoreTest {
	private HazelcastInstance hazel;

	public KratiDataStoreTest() {
	}

	@Test
	public void hazelcastKratiMapPersistence() {
		hazel = null;
		Config cfg = new Config();
		cfg.setPort(5900);
		cfg.setPortAutoIncrement(false);
		// cfg.setProperty("hazelcast.logging.type", "log4j");

		MapConfig mapCfg = new MapConfig();
		mapCfg.setName("testKratiPersistence");
		mapCfg.setBackupCount(2);
		mapCfg.getMaxSizeConfig().setSize(10000);
		mapCfg.setTimeToLiveSeconds(300);

		MapStoreConfig mapStoreCfg = new MapStoreConfig();
		mapStoreCfg
				.setClassName(
						"br.com.creativesource.tools.storage.hazelcast.krati.KratiHazelcastDS")
				.setEnabled(true);
		mapCfg.setMapStoreConfig(mapStoreCfg);

		cfg.addMapConfig(mapCfg);
		hazel = Hazelcast.init(cfg);

		Map<Integer, String> mapCustomers = Hazelcast.getMap("customers");
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(3, "Avi");

		assertEquals("Joe", mapCustomers.get(1));
		assertEquals("Ali", mapCustomers.get(2));
		assertEquals("Avi", mapCustomers.get(3));
		assertEquals(3, mapCustomers.size());

		Queue<String> queueCustomers = hazel.getQueue("customers");
		queueCustomers.offer("Tom");
		queueCustomers.offer("Mary");
		queueCustomers.offer("Jane");

		assertEquals("Tom", queueCustomers.poll());
		assertEquals("Mary", queueCustomers.peek());
		assertEquals(2, queueCustomers.size());

	}

	@Test
	public void kratiSerialiazer() throws Exception {
		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		System.out.println("Path: " + path);

		DataStore datastore = null;
		try {
			datastore = KratiDataStoreFactory.newMappedSegmentDataStore(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datastore.put(1, "Joe");
		datastore.put(2, "Ali");
		datastore.put(3, "Avi");

		assertEquals("Joe", datastore.get(1));
		assertEquals("Ali", datastore.get(2));
		assertEquals("Avi", datastore.get(3));
	}

}
