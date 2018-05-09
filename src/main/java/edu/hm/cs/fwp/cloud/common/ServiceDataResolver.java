/*
 * cna-openshift-cloud-connector:ServiceDataResolver.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.common;

import java.util.List;

import org.springframework.cloud.util.EnvironmentAccessor;

/**
 * Generic resolver of {@code ServiceData} from a given set of environment
 * variables.
 * <p>
 * Concrete {@code ServiceDataResolver} instances are created using Java's
 * service lookup mechanism.
 * </p>
 * 
 * @param <SD>
 *            concrete service data type
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public interface ServiceDataResolver<SD> {

	/**
	 * Resolves {@code ServiceData} of a specific service type based on the
	 * given environment.
	 * 
	 * @param environment
	 *            environment accessor to environment variables and system
	 *            properties.
	 * @return List of service data for a specific service type; may be
	 *         {@code empty}, if the environment does not provide information
	 *         for this particular service type, but is never {@code null}.
	 */
	List<SD> resolve(EnvironmentAccessor environment);
}
