/*
 * cna-kubernetes-cloud-connector:MySqlServiceInfoCreatorTest.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.mysql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.cloud.service.common.MysqlServiceInfo;

import edu.hm.cs.fwp.cloud.common.ServiceDataVariable;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;
import edu.hm.cs.fwp.cloud.kubernetes.service.mysql.MySqlServiceInfoCreator;

/**
 * {@code Unit test} for {@link MySqlServiceInfoCreator}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public class MySqlServiceInfoCreatorTest {

	private final MySqlServiceInfoCreator underTest = new MySqlServiceInfoCreator();

	@Test
	public void createServiceInfoWithCompleteServiceDataReturnValidServiceInfo() {
		ServiceData serviceData = createCompleteServiceData();
		MysqlServiceInfo serviceInfo = this.underTest.createServiceInfo(serviceData);
		assertNotNull(serviceInfo);
		assertEquals(serviceData.getName(), serviceInfo.getId());
		assertEquals(buildCanonicalUri(serviceData), serviceInfo.getUri());
		assertEquals(buildJdbcUrl(serviceData), serviceInfo.getJdbcUrl());
	}

	private ServiceData createCompleteServiceData() {
		List<ServiceDataVariable> pairs = new ArrayList<>();
		pairs.add(new ServiceDataVariable("MYSQL_DB_HOST", "10.0.4.56"));
		pairs.add(new ServiceDataVariable("MYSQL_DB_PORT", "3306"));
		pairs.add(new ServiceDataVariable("MYSQL_DB_NAME", "mysql_accounts_db"));
		pairs.add(new ServiceDataVariable("MYSQL_DB_USER", "mysql_accounts_user"));
		pairs.add(new ServiceDataVariable("MYSQL_DB_PASSWORD", "mydata!2017"));
		return new ServiceData(ServiceType.MYSQL, "mysql-accounts-service", pairs);
	}

	private String buildCanonicalUri(ServiceData serviceData) {
		return "mysql://" + serviceData.getRequiredValue("MYSQL_DB_USER") + ":"
				+ serviceData.getRequiredValue("MYSQL_DB_PASSWORD") + "@"
				+ serviceData.getRequiredValue("MYSQL_DB_HOST") + ":" + serviceData.getRequiredValue("MYSQL_DB_PORT")
				+ "/" + serviceData.getRequiredValue("MYSQL_DB_NAME");
	}

	private String buildJdbcUrl(ServiceData serviceData) {
		return "jdbc:mysql://" + serviceData.getRequiredValue("MYSQL_DB_HOST") + ":"
				+ serviceData.getRequiredValue("MYSQL_DB_PORT") + "/" + serviceData.getRequiredValue("MYSQL_DB_NAME")
				+ "?user=" + serviceData.getRequiredValue("MYSQL_DB_USER") + "&password="
				+ serviceData.getRequiredValue("MYSQL_DB_PASSWORD");
	}
}
