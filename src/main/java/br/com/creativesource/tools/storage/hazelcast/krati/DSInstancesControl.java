package br.com.creativesource.tools.storage.hazelcast.krati;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import krati.store.DataStore;

import org.apache.log4j.Logger;

public class DSInstancesControl {

	private static final Logger LOG = Logger
			.getLogger(DSInstancesControl.class);

	private final DataStore dataStore;

	private final AtomicLong registrationCount = new AtomicLong();

	public DSInstancesControl(DataStore dataStore) {
		this.dataStore = dataStore;
	}

	public Long register() {

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
