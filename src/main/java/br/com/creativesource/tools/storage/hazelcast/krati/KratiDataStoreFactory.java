package br.com.creativesource.tools.storage.hazelcast.krati;

import java.util.HashMap;
import java.util.Map;

import krati.core.segment.ChannelSegmentFactory;
import krati.core.segment.MappedSegmentFactory;
import krati.core.segment.SegmentFactory;
import krati.io.Serializer;
import krati.store.DataStore;

import org.apache.log4j.Logger;

import br.com.creativesource.tools.storage.hazelcast.krati.serializer.DefaultSerializer;

import com.google.common.base.Preconditions;

public class KratiDataStoreFactory {
	static final Logger LOG = Logger.getLogger(KratiDataStoreFactory.class);
	
	protected static Map<String, DSInstancesControl> dataStoreRegistry = new HashMap<String, DSInstancesControl>();

	public static DataStore newMappedSegmentDataStore(String path) throws Exception {
		Preconditions.checkNotNull(path, "Argument path is empty");
		
		SegmentFactory segmentFactory = new MappedSegmentFactory();
		
		Serializer keySerializer = new DefaultSerializer();
		Serializer valueSerializer = new DefaultSerializer();

		DataStore dataStore = null;
		DSInstancesControl registration = dataStoreRegistry.get(path);
		
		if (registration != null) {
			dataStore = registration.getDataStore();
		}
		if (dataStore == null || !dataStore.isOpen()) {
			
			dataStore = KratiDataStore.createDataStore(path, 100, segmentFactory,
					keySerializer, valueSerializer);
			
			dataStoreRegistry.put(path, new DSInstancesControl(dataStore));
		}
		return dataStore;


	}
	
	public static DataStore newChannelSegmentDataStore(String path) throws Exception {
		Preconditions.checkNotNull(path, "Argument path is empty");
		
		SegmentFactory segmentFactory = new ChannelSegmentFactory();
		
		Serializer keySerializer = new DefaultSerializer();
		Serializer valueSerializer = new DefaultSerializer();

		DataStore dataStore = null;
		DSInstancesControl registration = dataStoreRegistry.get(path);
		
		if (registration != null) {
			dataStore = registration.getDataStore();
		}
		if (dataStore == null || !dataStore.isOpen()) {
			
			dataStore = KratiDataStore.createDataStore(path, 100, segmentFactory,
					keySerializer, valueSerializer);
			
			dataStoreRegistry.put(path, new DSInstancesControl(dataStore));
		}
		return dataStore;


	}

}
