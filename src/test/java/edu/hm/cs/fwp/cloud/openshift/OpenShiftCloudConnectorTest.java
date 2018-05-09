/*
 * cna-openshift-cloud-connector:MySqlServiceDataResolverTest.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.MysqlServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.openshift.OpenShiftCloudConnector;

/**
 * {@code Unit test} for {@link OpenShiftCloudConnector}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public class OpenShiftCloudConnectorTest {

	private static final String MYSQL_DB_HOST_ENVAR_NAME = "MYSQL_DB_HOST";

	private static final String MYSQL_DB_HOST_ENVAR_VALUE = "10.0.4.276";

	private static final String MYSQL_DB_PORT_ENVAR_NAME = "MYSQL_DB_PORT";

	private static final String MYSQL_DB_PORT_ENVAR_VALUE = "3306";

	private static final String MYSQL_DB_NAME_ENVAR_NAME = "MYSQL_DB_NAME";

	private static final String MYSQL_DB_NAME_ENVAR_VALUE = "mydata_accounts_db";

	private static final String MYSQL_DB_USER_ENVAR_NAME = "MYSQL_DB_USER";

	private static final String MYSQL_DB_USER_ENVAR_VALUE = "mydata_accounts_user";

	private static final String MYSQL_DB_PASSWORD_ENVAR_NAME = "MYSQL_DB_PASSWORD";

	private static final String MYSQL_DB_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private static final String MYSQL_SERVICE_URI = "mysql://" + MYSQL_DB_USER_ENVAR_VALUE + ":"
			+ MYSQL_DB_PASSWORD_ENVAR_VALUE + "@" + MYSQL_DB_HOST_ENVAR_VALUE + ":" + MYSQL_DB_PORT_ENVAR_VALUE + "/"
			+ MYSQL_DB_NAME_ENVAR_VALUE;

	private static final String MYSQL_SERVICE_JDBC_URL = "jdbc:mysql://" + MYSQL_DB_HOST_ENVAR_VALUE + ":"
			+ MYSQL_DB_PORT_ENVAR_VALUE + "/" + MYSQL_DB_NAME_ENVAR_VALUE + "?user=" + MYSQL_DB_USER_ENVAR_VALUE
			+ "&password=" + MYSQL_DB_PASSWORD_ENVAR_VALUE;

	@Test
	public void isInMatchingCloudReturnsTrueIfCloudEnvVarIsSetToKubernetes() {
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnvValue(OpenShiftCloudConnector.CLOUD_ENVVAR_NAME))
				.thenReturn(OpenShiftCloudConnector.CLOUD_ENVVAR_VALUE);
		OpenShiftCloudConnector underTest = new OpenShiftCloudConnector(environment);
		assertTrue("CloudConnector.isInMatchingCloud() must return true", underTest.isInMatchingCloud());
	}

	@Test
	public void getServiceInfoReturnMySqlServiceInfoIfMySqlEnvVarsAreSet() {
		Map<String, String> valuesByName = new HashMap<>();
		valuesByName.put(MYSQL_DB_HOST_ENVAR_NAME, MYSQL_DB_HOST_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PORT_ENVAR_NAME, MYSQL_DB_PORT_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_NAME_ENVAR_NAME, MYSQL_DB_NAME_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_USER_ENVAR_NAME, MYSQL_DB_USER_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PASSWORD_ENVAR_NAME, MYSQL_DB_PASSWORD_ENVAR_VALUE);
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnv()).thenReturn(valuesByName);
		OpenShiftCloudConnector underTest = new OpenShiftCloudConnector(environment);
		List<ServiceInfo> serviceInfos = underTest.getServiceInfos();
		assertNotNull("getServiceInfo() must not return null", serviceInfos);
		assertTrue("getServiceInfo() must not return empty list", !serviceInfos.isEmpty());
		MysqlServiceInfo serviceInfo = (MysqlServiceInfo) serviceInfos.get(0);
		assertEquals("service ID must match", "mysql-unnamed", serviceInfo.getId());
		assertEquals("URI must match", MYSQL_SERVICE_URI, serviceInfo.getUri());
		assertEquals("JDBC URL must match", MYSQL_SERVICE_JDBC_URL, serviceInfo.getJdbcUrl());
	}

}
