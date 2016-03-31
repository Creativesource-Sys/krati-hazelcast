package br.com.creativesource.tools.storage.krati.hazelcast.tests;

import static org.junit.Assert.*;
import krati.store.DataStore;

import org.junit.Test;

import br.com.creativesource.tools.storage.krati.hazelcast.factory.KratiDataStoreFactory;

public class ConfigMappedSegmentFactoryTest {

	@Test
	public void simpleConfigTest() throws Exception {

		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		System.out.println("Path: " + path);

		Config conf = new Config();

		conf.setKey("user").setProperty("Adriano");

		DataStore datastore = null;
		try {
			datastore = KratiDataStoreFactory.newMappedSegmentDataStore(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datastore.put(conf.getKey(), conf.getProperty());

		assertEquals("Adriano", datastore.get(conf.getKey()));
	}

	@Test
	public void valueIsObject() throws Exception {
		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		System.out.println("Path: " + path);

		Config conf = new Config();

		conf.setKey("user").setProperty("Adriano");

		DataStore datastore = null;
		try {
			datastore = KratiDataStoreFactory.newMappedSegmentDataStore(path);
		} catch (Exception e) {
			e.printStackTrace();
		}

		datastore.put(1, conf);

		assertEquals(conf, datastore.get(1));

	}

}
