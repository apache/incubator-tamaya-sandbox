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

import org.apache.tamaya.base.configsource.EnvironmentConfigSource;
import org.apache.tamaya.spisupport.propertysource.EnvironmentPropertySource;
import org.apache.tamaya.spi.PropertySource;
import org.junit.Test;

import javax.config.spi.ConfigSource;
import java.util.Collections;

import static org.junit.Assert.*;


/**
 * Created by atsticks on 18.04.17.
 */
public class EnvPropertiesFactoryTest {

    private static EnvPropertiesFactory f = new EnvPropertiesFactory();

    @Test
    public void getName() throws Exception {
        assertEquals("env-properties", f.getName());
    }

    @Test
    public void create() throws Exception {
        ConfigSource ps = f.create(Collections.EMPTY_MAP);
        assertNotNull(ps);
        assertTrue(ps instanceof EnvironmentConfigSource);
    }

    @Test
    public void getType() throws Exception {
        assertEquals(ConfigSource.class, f.getType());
    }

}