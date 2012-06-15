/**
 * 
 */
package br.com.creativesource.tools.storage.hazelcast.krati.serializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;

import krati.io.SerializationException;
import krati.io.Serializer;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.InputSupplier;

/**
 * @author sleipnir
 * @param <T>
 * 
 */
public class SimpleKratiSerializer<T extends Serializable> implements
		Serializer<T> {
	private static final Logger LOG = Logger
			.getLogger(SimpleKratiSerializer.class);

	public byte[] serialize(T object) throws SerializationException {
		Preconditions.checkNotNull(object,
				"Argument Necessary Not Found for method serialize(T object)");

		byte[] result = null;

		ByteArrayDataOutput baos = ByteStreams.newDataOutput();

		ObjectOutputStream oos = null;

		try {
			oos = new ObjectOutputStream((OutputStream) baos);

			oos.writeObject(object);

			result = baos.toByteArray();

		} catch (IOException e) {
			LOG.warn("IOException thrown while closing Object.", e);

		} finally {
			Closeables.closeQuietly(oos);
		}
		return result;
	}

	public T deserialize(byte[] bytes) throws SerializationException {
		Preconditions
				.checkNotNull(bytes,
						"Argument Necessary Not Found for method deserialize(byte[] bytes)");
		T result = null;
		ObjectInputStream ois = null;

		InputSupplier<ByteArrayInputStream>  bais = ByteStreams.newInputStreamSupplier(bytes);
		
		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		try {
			ois = new ObjectInputStream((InputStream) bais) {
				@Override
				public Class<?> resolveClass(ObjectStreamClass desc)
						throws IOException, ClassNotFoundException {
					try {
						return classLoader.loadClass(desc.getName());
					} catch (Exception e) {
					}
					return super.resolveClass(desc);
				}
			};
			result = (T) ois.readObject();
		} catch (IOException e) {
			LOG.warn("Error while deserializing object. ", e);
		} catch (ClassNotFoundException e) {
			LOG.warn(
					"Could not find class while deserializing object. Null will be used.",
					e);
		} finally {
			Closeables.closeQuietly(ois);
		}
		return result;
	}

}
