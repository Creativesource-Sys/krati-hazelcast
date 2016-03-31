package br.com.creativesource.tools.storage.krati.hazelcast.store;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import krati.store.DataStore;

public class KratiDataStoreRegistration {

	private static final Logger LOG = LoggerFactory.getLogger(KratiDataStoreRegistration.class);

    private final DataStore dataStore;
    private int registrationCount;

    public KratiDataStoreRegistration(DataStore dataStore) {
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
