/*
 * cna-openshift-cloud-connector:ServiceDataVariable.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Single name value pair representing one configuration parameter passed as an
 * environment variable.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class ServiceDataVariable {

	private final String name;

	private final String value;

	/**
	 * Constructs an immutable instance.
	 * 
	 * @param name
	 *            environment variable name
	 * @param value
	 *            environment variable value
	 */
	public ServiceDataVariable(String name, String value) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Missing required name!");
		}
		if (value == null) {
			throw new IllegalArgumentException(
					String.format("Missing required value for environment variable [%s]!", name));
		}
		this.name = name;
		this.value = value;
	}

	/**
	 * Name of the environment variable.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Value of the environment variable.
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.name.hashCode();
		result = prime * result + this.value.hashCode();
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ServiceDataVariable)) {
			return false;
		}
		ServiceDataVariable other = (ServiceDataVariable) obj;
		if (!this.name.equals(other.name)) {
			return false;
		}
		if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}
}
