/*
 * cna-kubernetes-cloud-connector:KubernetesFallbackServiceInfoCreator.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service;

import org.springframework.cloud.FallbackServiceInfoCreator;
import org.springframework.cloud.service.BaseServiceInfo;
import org.springframework.cloud.service.ServiceInfo;

/**
 * Fallback {@code ServiceInfoCreator} if no suitable service info creator could
 * be found for the given {@code ServiceData}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class KubernetesFallbackServiceInfoCreator
		extends FallbackServiceInfoCreator<ServiceInfo, ServiceData> {

	/**
	 * @see org.springframework.cloud.ServiceInfoCreator#createServiceInfo(java.lang.Object)
	 */
	@Override
	public ServiceInfo createServiceInfo(ServiceData serviceData) {
		return new BaseServiceInfo(serviceData.getName());
	}
}
