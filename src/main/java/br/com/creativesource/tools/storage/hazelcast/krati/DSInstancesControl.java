package br.com.creativesource.tools.storage.hazelcast.krati;

import java.io.IOException;

import krati.store.DataStore;

import org.apache.log4j.Logger;


public class DSInstancesControl {

	private static final Logger LOG = Logger
			.getLogger(DSInstancesControl.class);
	
    private final DataStore dataStore;
    private int registrationCount;

    public DSInstancesControl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public int register() {
        return ++registrationCount;
    }

    public boolean unregister() {
        if (--registrationCount <= 0) {
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
