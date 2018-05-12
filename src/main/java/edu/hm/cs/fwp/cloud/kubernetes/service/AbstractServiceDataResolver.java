/*
 * cna-kubernetes-cloud-connector:AbstractServiceDataResolver.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.common.ServiceDataResolver;
import edu.hm.cs.fwp.cloud.common.ServiceDataVariable;

/**
 * Base implementation of a ServiceDataResolver, that extracts
 * {@code ServiceData} instances from the specified environment.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public class AbstractServiceDataResolver implements ServiceDataResolver<ServiceData> {

	private static final String NO_NAME_SERVICE = "unnamed";

	private final ServiceType serviceType;

	private final String[] servicePropertyPrefices;

	public AbstractServiceDataResolver(ServiceType serviceType, String... serviceDataPrefices) {
		this.serviceType = serviceType;
		this.servicePropertyPrefices = serviceDataPrefices;
	}

	/**
	 * @see edu.hm.cs.fwp.cloud.common.ServiceDataResolver#resolve(org.springframework.cloud.util.EnvironmentAccessor)
	 */
	@Override
	public List<ServiceData> resolve(EnvironmentAccessor environment) {
		List<ServiceDataVariable> serviceDataPairs = extractServiceDataPairs(environment);
		Map<String, List<ServiceDataVariable>> serviceDataPairsByService = groupServiceDataPairsByService(
				serviceDataPairs);
		return map(serviceDataPairsByService);
	}

	private List<ServiceDataVariable> extractServiceDataPairs(EnvironmentAccessor environment) {
		List<ServiceDataVariable> result = new ArrayList<>();
		for (Map.Entry<String, String> currentEntry : environment.getEnv().entrySet()) {
			if (currentEntry.getKey().startsWith(this.serviceType.name())) {
				result.add(new ServiceDataVariable(currentEntry.getKey(), currentEntry.getValue()));
			}
		}
		return result;
	}

	private Map<String, List<ServiceDataVariable>> groupServiceDataPairsByService(List<ServiceDataVariable> pairs) {
		Map<String, List<ServiceDataVariable>> result = new HashMap<>();
		for (ServiceDataVariable currentPair : pairs) {
			for (String currentPrefix : this.servicePropertyPrefices) {
				if (currentPair.getName().equals(currentPrefix)) {
					merge(result, buildDefaultServiceName(), currentPair);
				} else if (currentPair.getName().startsWith(currentPrefix)) {
					merge(result, extractServiceName(currentPrefix, currentPair.getName()),
							new ServiceDataVariable(currentPrefix, currentPair.getValue()));
				}
			}
		}
		return result;
	}

	private List<ServiceData> map(Map<String, List<ServiceDataVariable>> pairsByService) {
		List<ServiceData> result = new ArrayList<>();
		for (Map.Entry<String, List<ServiceDataVariable>> currentEntry : pairsByService.entrySet()) {
			result.add(new ServiceData(this.serviceType, currentEntry.getKey(), currentEntry.getValue()));
		}
		return result;
	}

	private void merge(Map<String, List<ServiceDataVariable>> target, String key, ServiceDataVariable value) {
		List<ServiceDataVariable> values = target.get(key);
		if (values == null) {
			values = new ArrayList<>();
			target.put(key, values);
		}
		values.add(value);
	}

	private String buildDefaultServiceName() {
		return this.serviceType.name().toLowerCase() + "-" + NO_NAME_SERVICE;
	}

	/**
	 * Extracts a service name from an environment variable name.
	 * <p>
	 * The service name is assumed to be a suffix of a environment variable name
	 * starting with a given prefix.
	 * </p>
	 * <p>
	 * Environment variable name {@code MYSQL_DB_HOST_ACCOUNT_SERVICE} is mapped
	 * to a service name {@code account-service}.
	 * </p>
	 * 
	 * @param prefix
	 * @param variableName
	 * @return
	 */
	private String extractServiceName(String prefix, String variableName) {
		String result = variableName.substring(prefix.length());
		result = result.toLowerCase();
		result = result.replace('_', '-');
		return result;
	}
}
