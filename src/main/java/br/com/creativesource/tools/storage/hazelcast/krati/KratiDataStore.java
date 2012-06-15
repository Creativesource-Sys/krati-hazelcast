/**
 * 
 */
package br.com.creativesource.tools.storage.hazelcast.krati;

import java.io.File;
import java.io.IOException;

import br.com.creativesource.tools.storage.hazelcast.krati.serializer.DefaultSerializer;

import krati.core.segment.MappedSegmentFactory;
import krati.core.segment.MemorySegmentFactory;
import krati.store.DataStore;
import krati.store.DynamicDataStore;
import krati.store.SerializableObjectStore;

import krati.core.StoreConfig;
import krati.core.StoreFactory;

/**
 * @author sleipnir
 * 
 */
public class KratiDataStore {
	private static final int DEFAULT_SEGMENT_SIZE = 0;
	private final int _initialCapacity;
	private final DataStore<byte[], byte[]> _store;
	private File _dir;

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
	public KratiDataStore(File homeDir, int initialCapacity) throws Exception {
		_initialCapacity = initialCapacity;
		_dir = homeDir;
		_store = createDataStore();
	}

	/**
	 * @return the underlying data store.
	 */
	public final DataStore getDataStore() {
		return _store;
	}

	/**
	 * Creates a DataStore instance.
	 * <p>
	 * Subclasses can override this method to provide a specific DataStore
	 * implementation such as DynamicDataStore and IndexedDataStore or provide a
	 * specific SegmentFactory such as ChannelSegmentFactory,
	 * MappedSegmentFactory and WriteBufferSegment.
	 */
	/*protected DataStore createDataStore(File homeDir, int initialCapacity)
			throws Exception {
		StoreConfig config = new StoreConfig(homeDir, initialCapacity);
		config.setSegmentFactory(new MappedSegmentFactory());
		config.setSegmentFileSizeMB(64);

		return StoreFactory.createStaticDataStore(config);
	}

	*/protected DataStore createDataStore() throws Exception {
		DataStore result = null;

		try {

			StoreConfig config = new StoreConfig(this._dir,
					this._initialCapacity);
			config.setSegmentFactory(new MappedSegmentFactory());
			config.setSegmentFileSizeMB(DEFAULT_SEGMENT_SIZE);

			DataStore dynamicDataStore = new DynamicDataStore(config);

			result = new SerializableObjectStore(dynamicDataStore,
					new DefaultSerializer(), new DefaultSerializer());
		} catch (Exception e) {
			throw new RuntimeKratiDSException(
					"Failed to create Krati DataStore.", e);
		}

		return result;
	}

}
