package br.com.creativesource.tools.storage.hazelcast.krati.tests;

import static org.junit.Assert.assertEquals;
import krati.store.DataStore;

import org.junit.Test;

import br.com.creativesource.tools.storage.hazelcast.krati.KratiDataStoreFactory;

public class ConfigChannelSegmentFactoryTest {
	@Test
	public void simpleConfigTest() throws Exception {
		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		System.out.println("Path: " + path);

		Config conf = new Config();

		conf.setKey("user").setPropertie("Adriano");

		DataStore datastore = null;
		try {
			datastore = KratiDataStoreFactory.newChannelSegmentDataStore(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datastore.put(conf.getKey(), conf.getPropertie());

		assertEquals("Adriano", datastore.get(conf.getKey()));
	}

	@Test
	public void valueIsObject() throws Exception {
		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		System.out.println("Path: " + path);

		Config conf = new Config();

		conf.setKey("user").setPropertie("Adriano");

		DataStore datastore = null;
		try {
			datastore = KratiDataStoreFactory.newChannelSegmentDataStore(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datastore.put(1, conf);
        
		assertEquals(conf, datastore.get(1));

	}

}
