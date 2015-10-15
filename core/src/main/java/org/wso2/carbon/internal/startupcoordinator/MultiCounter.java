/*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.wso2.carbon.internal.startupcoordinator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO fix this
 *
 * @param <K> k
 */
public class MultiCounter<K> {
    private static final Logger logger = LoggerFactory.getLogger(MultiCounter.class);

    Map<K, AtomicInteger> counterMap = new ConcurrentHashMap<>();

    /**
     * make this thread safe TODO  fix this
     *
     * @param key
     */
    public int incrementAndGet(K key) {
        logger.info("!!!!! Increment {}", key);
        if (!counterMap.containsKey(key)) {
            counterMap.put(key, new AtomicInteger(1));
            return 1;
        }
        return counterMap.get(key).incrementAndGet();
    }

    /**
     * make this Thread safe
     *
     * @param key
     * @return
     */
    public int decrementAndGet(K key) {
        if (counterMap.containsKey(key)) {
            int tally = counterMap.get(key).decrementAndGet();
            if (tally == 0) {
                counterMap.remove(key);
                return tally;
            }
            return tally;
        }
        return -1;
    }

    public int get(K key) {
        return counterMap.get(key).get();
    }

    public List<K> getAllKeys() {
        return new ArrayList<>(counterMap.keySet());
    }
}