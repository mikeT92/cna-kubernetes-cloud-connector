/*
 * cna-openshift-cloud-connector:AbstractServiceInfoCreator.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.ServiceInfoCreator;
import org.springframework.cloud.service.ServiceInfo;

/**
 * Common base class for all {@code ServiceInfoCreator} implementations provided
 * by this {@code CloudConnector}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public abstract class AbstractServiceInfoCreator<SI extends ServiceInfo>
		implements ServiceInfoCreator<SI, ServiceData> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private final ServiceType serviceType;

	public AbstractServiceInfoCreator(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @see org.springframework.cloud.ServiceInfoCreator#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(ServiceData serviceData) {
		return this.serviceType.equals(serviceData.getType());
	}

	/**
	 * @see org.springframework.cloud.ServiceInfoCreator#createServiceInfo(java.lang.Object)
	 */
	@Override
	public abstract SI createServiceInfo(ServiceData serviceData);
}
