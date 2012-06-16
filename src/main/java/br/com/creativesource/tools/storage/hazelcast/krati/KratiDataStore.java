/**
 * 
 */
package br.com.creativesource.tools.storage.hazelcast.krati;

import java.io.File;

import org.apache.log4j.Logger;

import br.com.creativesource.tools.storage.hazelcast.krati.serializer.SimpleKratiSerializer;

import krati.core.StoreConfig;
import krati.core.segment.SegmentFactory;
import krati.io.Serializer;
import krati.store.DataStore;
import krati.store.DynamicDataStore;
import krati.store.SerializableObjectStore;

/**
 * @author sleipnir
 * 
 */
public class KratiDataStore {
	static final Logger LOG = Logger.getLogger(KratiDataStore.class);
	
	private static final int DEFAULT_SEGMENT_SIZE = 64;
	private static final int DEFAULT_INITIAL_CAPACITY = 100;
	
	
	/**
	 * Constructs KratiDataStore.
	 * 
	 * @param homeDir
	 *            the home directory of DataStore.
	 * @param initialCapacity
	 *            the initial capacity of DataStore.
	 * @throws Exception
	 *             if a DataStore instance can not be created.
	 */
	private KratiDataStore() {}

	/**
	 * @return the underlying data store.
	 */


	/**
	 * Creates a DataStore instance.
	 * <p>
	 * Subclasses can override this method to provide a specific DataStore
	 * implementation such as DynamicDataStore and IndexedDataStore or provide a
	 * specific SegmentFactory such as ChannelSegmentFactory,
	 * MappedSegmentFactory and WriteBufferSegment.
	 */
	
	public static DataStore createDataStore(String path, int initialCapacity,
			SegmentFactory segmentFactory, Serializer keySerializer,
			Serializer valueSerializer) throws Exception {
		
		DataStore result = null;
		
		File homeDir = new File(path);
		homeDir.mkdirs();
		LOG.info("Creating a permanent storage area: " + path);
		
		int init;
		
		if (initialCapacity > 0)
			init = initialCapacity;
		else
			init = DEFAULT_INITIAL_CAPACITY;
		
		try {
			StoreConfig storeConfig = new StoreConfig(homeDir, init);
			storeConfig.setSegmentFactory(segmentFactory);
			storeConfig.setSegmentFileSizeMB(DEFAULT_SEGMENT_SIZE);
			
			DataStore dynamicDataStore = new DynamicDataStore(storeConfig);

			result = new SerializableObjectStore(dynamicDataStore,
					keySerializer, valueSerializer);
		} catch (Exception e) {
			throw new RuntimeKratiDSException(
					"Failed to create Krati DataStore.", e);
		}
		return result;
	}



}
