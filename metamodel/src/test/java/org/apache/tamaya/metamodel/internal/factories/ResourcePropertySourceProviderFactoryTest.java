/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.tamaya.metamodel.internal.factories;

import org.apache.tamaya.spi.PropertySourceProvider;
import org.junit.Test;

import javax.config.spi.ConfigSourceProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Created by atsticks on 18.04.17.
 */
public class ResourcePropertySourceProviderFactoryTest {

    private static ResourcePropertySourceProviderFactory f = new ResourcePropertySourceProviderFactory();

    @Test
    public void getName() throws Exception {
        assertEquals("resources", f.getName());
    }

    @Test
    public void create() throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("location", "GLOBAL.properties");
        ConfigSourceProvider prov = f.create(params);
        assertNotNull(prov);
    }

    @Test
    public void create_Error() throws Exception {
        Map<String,String> params = new HashMap<>();
        ConfigSourceProvider prov = f.create(Collections.<String, String>emptyMap());
        assertNull("Should return null for missing location.", prov);
    }

    @Test
    public void getType() throws Exception {
        assertEquals(ConfigSourceProvider.class, f.getType());
    }

}