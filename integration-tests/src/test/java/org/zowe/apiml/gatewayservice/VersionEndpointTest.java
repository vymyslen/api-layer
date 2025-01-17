/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.gatewayservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zowe.apiml.util.TestWithStartedInstances;
import org.zowe.apiml.util.categories.GeneralAuthenticationTest;
import org.zowe.apiml.util.categories.TestsNotMeantForZowe;import org.zowe.apiml.util.config.ConfigReader;
import org.zowe.apiml.util.config.GatewayServiceConfiguration;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.zowe.apiml.gatewayservice.SecurityUtils.getConfiguredSslConfig;

@TestsNotMeantForZowe
@GeneralAuthenticationTest
class VersionEndpointTest implements TestWithStartedInstances {

    private String requestString;

    @BeforeAll
    static void beforeClass() {
        RestAssured.useRelaxedHTTPSValidation();
    }

    @BeforeEach
    void setUp() {
        GatewayServiceConfiguration serviceConfiguration = ConfigReader.environmentConfiguration().getGatewayServiceConfiguration();
        String scheme = serviceConfiguration.getScheme();
        String host = serviceConfiguration.getHost();
        int port = serviceConfiguration.getPort();
        requestString = String.format("%s://%s:%s%s", scheme, host, port, "/api/v1/gateway/version");
    }

    @Test
    void shouldCallVersionEndpointAndReceivedVersion() {
        RestAssured.config = RestAssured.config().sslConfig(getConfiguredSslConfig());
        given()
            .when()
            .get(requestString)
            .then()
            .body(containsString("apiml"))
            .statusCode(is(SC_OK));
    }
}
