/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.redis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zowe.apiml.caching.service.Storage;
import org.zowe.apiml.caching.service.redis.RedisStorage;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
    private final RedisConfig redisConfig;

    @ConditionalOnProperty(name = "caching.storage.mode", havingValue = "redis")
    @Bean
    public Storage redis() {
        // TODO how do we determine and handle if multiple instances are being used
        return new RedisStorage(redisConfig);
    }
}