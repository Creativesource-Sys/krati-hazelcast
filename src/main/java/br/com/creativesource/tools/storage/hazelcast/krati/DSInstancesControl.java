package br.com.creativesource.tools.storage.hazelcast.krati;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import krati.store.DataStore;

import org.apache.log4j.Logger;

public class DSInstancesControl {

	private static final Logger LOG = Logger
			.getLogger(DSInstancesControl.class);

	private final DataStore dataStore;

	private final AtomicInteger registrationCount = new AtomicInteger();

	public DSInstancesControl(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public int register() {

		return registrationCount.incrementAndGet();
	}

	public boolean unregister() {
		if (registrationCount.decrementAndGet() <= 0) {
			try {
				dataStore.close();
			} catch (IOException e) {
				LOG.warn("Error while closing datastore.", e);
			}
			return true;
		} else {
			return false;
		}
	}

	public DataStore getDataStore() {
		register();
		return dataStore;
	}
}
