/*
 * cna-kubernetes-cloud-connector:AbstractCloudConnector.java
 */
package edu.hm.cs.fwp.cloud.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.CloudConnector;
import org.springframework.cloud.FallbackServiceInfoCreator;
import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

/**
 * Common base class for all {@code CloudConnector} implementations.
 * <p>
 * Tries to work around some issues with Spring Cloud's base implementation:
 * </p>
 * <ul>
 * <li>Since all {@code ServiceInfoCreator}s are packages in one JAR, we do not
 * need the service lookup feature provided by Spring Cloud's
 * {@code AbstractCloudConnect}</li>
 * <li>Added an abstraction how {@code ServiceData} is resolved from the
 * environment through introduction of {@code ServiceDataResolver}s</li>
 * <li>Added an abstraction how {@code ApplicationInstanceInfo} is created
 * through introduction of {@code ApplicationInstanceInfoCreator}</li>
 * </ul>
 * 
 * @param <SD>
 *            service data type parameter
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public abstract class AbstractCloudConnector<SD> implements CloudConnector {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final EnvironmentAccessor environment;
	private final List<ServiceDataResolver<SD>> serviceDataResolvers = new ArrayList<>();
	private final List<ServiceInfoCreator<ServiceInfo, SD>> serviceInfoCreators = new ArrayList<>();
	private ApplicationInstanceInfoCreator applicationInstanceInfoCreator;

	public AbstractCloudConnector(EnvironmentAccessor environment) {
		this.environment = environment;
	}

	/**
	 * @see org.springframework.cloud.CloudConnector#getApplicationInstanceInfo()
	 */
	@Override
	public ApplicationInstanceInfo getApplicationInstanceInfo() {
		return this.applicationInstanceInfoCreator != null
				? this.applicationInstanceInfoCreator.createApplicationInstanceInfo(this.environment)
				: null;
	}

	@Override
	public abstract boolean isInMatchingCloud();

	public abstract FallbackServiceInfoCreator<ServiceInfo, SD> getFallbackServiceInfoCreator();

	/**
	 * @see org.springframework.cloud.CloudConnector#getServiceInfos()
	 */
	@Override
	public List<ServiceInfo> getServiceInfos() {
		List<ServiceInfo> serviceInfos = new ArrayList<>();
		for (SD serviceData : getServicesData()) {
			serviceInfos.add(getServiceInfo(serviceData));
		}

		return serviceInfos;
	}

	public EnvironmentAccessor getEnvironment() {
		return this.environment;
	}

	protected void registerApplicationInstanceInfoCreator(ApplicationInstanceInfoCreator instanceInfoCreator) {
		this.applicationInstanceInfoCreator = instanceInfoCreator;
	}

	protected void registerServiceDataResolver(ServiceDataResolver serviceDataResolver) {
		this.serviceDataResolvers.add(serviceDataResolver);
	}

	protected void registerServiceInfoCreator(ServiceInfoCreator serviceInfoCreator) {
		this.serviceInfoCreators.add(serviceInfoCreator);
	}

	private List<SD> getServicesData() {
		List<SD> result = new ArrayList<>();
		for (ServiceDataResolver<SD> currentResolver : this.serviceDataResolvers) {
			result.addAll(currentResolver.resolve(this.environment));
		}
		return result;
	}

	private ServiceInfo getServiceInfo(SD serviceData) {
		ServiceInfo result = null;
		for (ServiceInfoCreator<ServiceInfo, SD> currentCreator : this.serviceInfoCreators) {
			if (currentCreator.accept(serviceData)) {
				result = currentCreator.createServiceInfo(serviceData);
				break;
			}
		}
		if (result == null) {
			// Fallback with a warning
			ServiceInfo fallbackServiceInfo = getFallbackServiceInfoCreator().createServiceInfo(serviceData);
			this.logger.warn("No suitable service info creator found for service [" + fallbackServiceInfo.getId()
					+ " Did you forget to add a ServiceInfoCreator?");
			result = fallbackServiceInfo;
		}
		return result;
	}
}
