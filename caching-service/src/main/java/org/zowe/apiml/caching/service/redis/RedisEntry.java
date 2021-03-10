/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.caching.service.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zowe.apiml.caching.model.KeyValue;

public class RedisEntry {
    private final String serviceId;
    private final KeyValue entry;

    private final ObjectMapper mapper = new ObjectMapper();

    public RedisEntry(String serviceId, KeyValue entry) {
        this.serviceId = serviceId;
        this.entry = entry;
    }

    public RedisEntry(String serviceId, String redisValue) throws RedisEntryException {
        this.serviceId = serviceId;
        try {
            this.entry = mapper.readValue(redisValue, KeyValue.class);
        } catch (Exception e) {
            throw new RedisEntryException("Failure deserializing the entry to a KeyValue object", e);
        }
    }

    public String getServiceId() {
        return serviceId;
    }

    public KeyValue getEntry() {
        return entry;
    }

    public String getEntryAsString() throws RedisEntryException {
        try {
            return mapper.writeValueAsString(this.entry);
        } catch (Exception e) {
            throw new RedisEntryException("Failure serializing the entry as a String", e);
        }
    }
}
