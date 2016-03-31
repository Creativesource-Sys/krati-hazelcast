package br.com.creativesource.tools.storage.krati.hazelcast.store;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import krati.store.DataStore;

public class DSInstancesControl {

	private static final Logger LOG = LoggerFactory.getLogger(DSInstancesControl.class);

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
