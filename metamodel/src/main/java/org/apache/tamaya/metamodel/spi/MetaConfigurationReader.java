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
package org.apache.tamaya.metamodel.spi;

import org.apache.tamaya.format.ConfigurationData;
import org.apache.tamaya.spi.ConfigurationBuilder;

/**
 * Reader that reads getMeta configuration from the getMeta configuration XML source.
 * This SPI allows to allow different aspects to be configured by different modules.
 */
public interface MetaConfigurationReader {

    /**
     * Reads meta-configuration from the given document and configures the current
     * configuration builder. The priority of readers is determined by the priorization policy
     * implemented by the {@link org.apache.tamaya.spi.ServiceContext},
     * @param metaConfig the meta-configuration data, not null.
     * @param configBuilder the context builder to use.
     */
    void read(ConfigurationData metaConfig, ConfigurationBuilder configBuilder);

}
