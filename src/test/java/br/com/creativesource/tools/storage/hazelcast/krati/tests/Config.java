package br.com.creativesource.tools.storage.hazelcast.krati.tests;

import java.io.Serializable;

public class Config implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result
				+ ((propertie == null) ? 0 : propertie.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Config other = (Config) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (propertie == null) {
			if (other.propertie != null)
				return false;
		} else if (!propertie.equals(other.propertie))
			return false;
		return true;
	}
	public String getKey() {
		return key;
	}
	public Config setKey(String key) {
		this.key = key;
		return this;
	}
	public String getProperty() {
		return propertie;
	}
	public Config setProperty(String propertie) {
		this.propertie = propertie;
		return this;
	}
	private String key;
	private String propertie;

}
