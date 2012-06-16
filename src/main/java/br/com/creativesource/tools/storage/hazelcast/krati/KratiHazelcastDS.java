/**
 * 
 */
package br.com.creativesource.tools.storage.hazelcast.krati;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hazelcast.core.MapStore;
import krati.store.DataStore;

/**
 * @author sleipnir
 * 
 */
public class KratiHazelcastDS implements MapStore<Object, Object> {
	static final Logger LOG = Logger.getLogger(KratiHazelcastDS.class);

	private DataStore datastore;
	private String _path;

	public KratiHazelcastDS() throws Exception {
		String path = System.getProperty("user.home");
		path = path + "/.krati/data";
		this.datastore = KratiDataStoreFactory.newMappedSegmentDataStore(path);
	}

	public KratiHazelcastDS(String _path) throws Exception {
		this._path = _path;
		this.datastore = KratiDataStoreFactory.newMappedSegmentDataStore(_path);
	}

	public DataStore getDatastore() {
		return datastore;
	}

	public void setDatastore(DataStore datastore) {
		this.datastore = datastore;
	}

	public String get_path() {
		return _path;
	}

	public void set_path(String _path) {
		this._path = _path;
	}

	public Object load(Object key) {

		return datastore.get(key);
	}

	public Map<Object, Object> loadAll(Collection<Object> keys) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Iterator<Object> keyIterator = keys.iterator();

		while (keyIterator.hasNext()) {
			Object key = keyIterator.next();
			Object value = datastore.get(key);
			map.put(key, value);
		}
		return map;
	}

	public Set<Object> loadAllKeys() {
		Set<Object> setKeys = new HashSet<Object>();

		Iterator<Object> keys = datastore.keyIterator();

		while (keys.hasNext()) {
			Object key = keys.next();
			setKeys.add(key);
		}
		return setKeys;
	}

	public void store(Object key, Object value) {
		try {
			datastore.put(key, value);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Persisting value for the specified key");
				LOG.debug("Key: " + key + " For value: " + value);
			}
		} catch (Exception e) {
			LOG.error(
					"An error occurred while persisting the map for the specified key. Key: "
							+ key, e);
		}

	}

	public void storeAll(Map<Object, Object> map) {
		LOG.info("Store all objects for a total of " + map.size() + " keys.");

		for (Entry<Object, Object> entry : map.entrySet()) {
			try {
				datastore.put(entry.getKey(), entry.getValue());
				if (LOG.isDebugEnabled()) {
					LOG.debug(entry.getKey() + " - " + entry.getValue());
				}

			} catch (Exception e) {
				LOG.error(
						"An error occurred while persisting the map for the specified key. ",
						e);
			}

		}

	}

	public void delete(Object key) {
		try {
			datastore.delete(key);
			if (LOG.isDebugEnabled()) {
				LOG.info("Erasing data for specified key. Key " + key);
			}
		} catch (Exception e) {
			LOG.error(
					"An error occurred while erasing map for the specified key. Key: "
							+ key, e);
		}

	}

	public void deleteAll(Collection<Object> keys) {
		LOG.info("Erasing all objects for a total of " + keys.size() + " keys.");

		Iterator<Object> keyAll = keys.iterator();

		while (keyAll.hasNext()) {
			Object key = keyAll.next();
			try {
				datastore.delete(key);
			} catch (Exception e) {
				LOG.error(
						"An error occurred during attempts to delete all entries in map ",
						e);
			}
		}

	}

}
