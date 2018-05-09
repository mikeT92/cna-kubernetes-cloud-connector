/*
 * cna-openshift-cloud-connector:ServiceData.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.hm.cs.fwp.cloud.common.ServiceDataVariable;

/**
 * Represents generic service data for a specific service.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class ServiceData {

	/**
	 * Builder to simplify creation of {@code ServiceData} instances.
	 */
	public static final class Builder {

		private final ServiceType type;
		private final String name;
		private final List<ServiceDataVariable> pairs = new ArrayList<>();

		public Builder(ServiceType type, String name) {
			this.type = type;
			this.name = name;
		}

		public Builder withVariable(String name, String value) {
			this.pairs.add(new ServiceDataVariable(name, value));
			return this;
		}

		public ServiceData build() {
			if (this.pairs.isEmpty()) {
				throw new IllegalStateException("Cannot create Service Data with an empty list of Service Data Pairs!");
			}
			return new ServiceData(this.type, this.name, this.pairs);
		}
	}

	private final ServiceType type;

	private final String name;

	private final Map<String, String> valuesByName = new HashMap<>();

	/**
	 * Constructs a fully initialized immutable instance.
	 * 
	 * @param type
	 * @param name
	 * @param values
	 */
	public ServiceData(ServiceType type, String name, List<ServiceDataVariable> values) {
		Objects.requireNonNull(name, "Missing required parameter type!");
		Objects.requireNonNull(name, "Missing required parameter name!");
		Objects.requireNonNull(values, "Missing required parameter values!");
		this.type = type;
		this.name = name;
		for (ServiceDataVariable currentPair : values) {
			this.valuesByName.put(currentPair.getName(), currentPair.getValue());
		}
	}

	/**
	 * Type of service represented by this service data.
	 */
	public ServiceType getType() {
		return this.type;
	}

	/**
	 * Unique service name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Value of the service data variable with the given name.
	 * 
	 * @param variableName
	 *            service data variable name.
	 * @return value of the specified service data variable, if a service data
	 *         variable with the specified name can be found; otherwise
	 *         {@code null}.
	 */
	public String getValue(String variableName) {
		return this.valuesByName.get(variableName);
	}

	/**
	 * Integer value of the service data variable with the given name.
	 * <p>
	 * Defaults to {@code 0}, if no variable with the given name can be found.
	 * </p>
	 * 
	 * @param variableName
	 *            service data variable name.
	 * @return integer value of the specified service data variable, if a
	 *         service data variable with the specified name can be found;
	 *         otherwise {@code 0}.
	 * @throws IllegalStateException,
	 *             if the value cannot be parsed into an integer value.
	 */
	public int getIntValue(String variableName) {
		int result = 0;
		String text = getValue(variableName);
		if (text != null) {
			try {
				result = Integer.parseInt(text);
			} catch (NumberFormatException ex) {
				throw new IllegalStateException(
						String.format("Service [%s] variable [%s]: expected numeric value but was [%s]", getName(),
								variableName, text),
						ex);
			}
		}
		return result;
	}

	/**
	 * Value of the service data variable with the given name.
	 * 
	 * @param variableName
	 *            service data variable name.
	 * @return value of the specified service data variable.
	 * @throws IllegalStateException,
	 *             if no variable with the given name can be found.
	 */
	public String getRequiredValue(String variableName) {
		String result = getValue(variableName);
		if (result == null) {
			throw new IllegalStateException(
					String.format("Missing required value of service data variable [%s]", variableName));
		}
		return this.valuesByName.get(variableName);
	}

	/**
	 * Integer value of the service data variable with the given name.
	 * 
	 * @param variableName
	 *            service data variable name.
	 * @return integer value of the specified service data variable, if a
	 *         service data variable with the specified name can be found.
	 * @throws IllegalStateException,
	 *             if the value cannot be parsed into an integer value or no
	 *             variable with the given name can be found.
	 */
	public int getRequiredIntValue(String variableName) {
		int result = 0;
		String text = getValue(variableName);
		if (text != null) {
			try {
				result = Integer.parseInt(text);
			} catch (NumberFormatException ex) {
				throw new IllegalStateException(
						String.format("Service [%s] variable [%s]: expected numeric value but was [%s]!", getName(),
								variableName, text),
						ex);
			}
		} else {
			throw new IllegalStateException(
					String.format("Service [%s] variable [%s]: missing required value!", getName(), variableName));
		}
		return result;
	}
}
