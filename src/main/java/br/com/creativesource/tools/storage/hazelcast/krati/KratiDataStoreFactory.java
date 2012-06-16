package br.com.creativesource.tools.storage.hazelcast.krati;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import krati.core.segment.ChannelSegmentFactory;
import krati.core.segment.MappedSegmentFactory;
import krati.core.segment.SegmentFactory;
import krati.io.Serializer;
import krati.store.DataStore;
import br.com.creativesource.tools.storage.hazelcast.krati.serializer.SimpleKratiSerializer;

public class KratiDataStoreFactory {
	
	protected static Map<String, KratiDataStoreRegistration> dataStoreRegistry = new HashMap<String, KratiDataStoreRegistration>();

	public static DataStore newMappedSegmentDataStore(String path) throws Exception {
		Preconditions.checkNotNull(path, "Argument path is empty");
		
		SegmentFactory segmentFactory = new MappedSegmentFactory();
		Serializer keySerializer = new SimpleKratiSerializer();
		Serializer valueSerializer = new SimpleKratiSerializer();

		DataStore dataStore = null;
		KratiDataStoreRegistration registration = dataStoreRegistry.get(path);
		
		if (registration != null) {
			dataStore = registration.getDataStore();
		}
		if (dataStore == null || !dataStore.isOpen()) {
			
			dataStore = KratiDataStore.createDataStore(path, 64, segmentFactory,
					keySerializer, valueSerializer);
			
			dataStoreRegistry.put(path, new KratiDataStoreRegistration(dataStore));
		}
		return dataStore;


	}
	
	public static DataStore newChannelSegmentDataStore(String path) throws Exception {
		Preconditions.checkNotNull(path, "Argument path is empty");
		
		SegmentFactory segmentFactory = new ChannelSegmentFactory();
		Serializer keySerializer = new SimpleKratiSerializer();
		Serializer valueSerializer = new SimpleKratiSerializer();

		DataStore dataStore = null;
		KratiDataStoreRegistration registration = dataStoreRegistry.get(path);
		
		if (registration != null) {
			dataStore = registration.getDataStore();
		}
		if (dataStore == null || !dataStore.isOpen()) {
			
			dataStore = KratiDataStore.createDataStore(path, 64, segmentFactory,
					keySerializer, valueSerializer);
			
			dataStoreRegistry.put(path, new KratiDataStoreRegistration(dataStore));
		}
		return dataStore;


	}

}
