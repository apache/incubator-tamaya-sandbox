/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya.osgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Dictionary;
import java.util.Hashtable;

import static org.junit.Assert.*;

/**
 * Created by atsticks on 10.12.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class TamayaConfigPluginTest extends  AbstractOSGITest{

    @Test
    public void pluginLoaded() throws Exception {
        assertNotNull(bundleContext.getService(bundleContext.getServiceReference(TamayaConfigPlugin.class)));
    }

    @Test
    public void testOperationMode() throws Exception {
        OperationMode om = tamayaConfigPlugin.getDefaultOperationMode();
        tamayaConfigPlugin.setDefaultOperationMode(OperationMode.EXTEND);
        assertEquals(OperationMode.EXTEND, tamayaConfigPlugin.getDefaultOperationMode());
        tamayaConfigPlugin.setDefaultOperationMode(OperationMode.OVERRIDE);
    }

    @Test
    public void testAutoUpdate() throws Exception {
        boolean autoUpdate = tamayaConfigPlugin.isAutoUpdateEnabled();
        tamayaConfigPlugin.setAutoUpdateEnabled(!autoUpdate);
        assertEquals(tamayaConfigPlugin.isAutoUpdateEnabled(),!autoUpdate);
        tamayaConfigPlugin.setAutoUpdateEnabled(autoUpdate);
        assertEquals(tamayaConfigPlugin.isAutoUpdateEnabled(),autoUpdate);
    }

    @Test
    public void testDefaulEnabled() throws Exception {
        boolean enabled = tamayaConfigPlugin.isTamayaEnabledByDefault();
        tamayaConfigPlugin.setTamayaEnabledByDefault(!enabled);
        assertEquals(tamayaConfigPlugin.isTamayaEnabledByDefault(),!enabled);
        tamayaConfigPlugin.setTamayaEnabledByDefault(enabled);
        assertEquals(tamayaConfigPlugin.isTamayaEnabledByDefault(),enabled);
    }

    @Test
    public void testSetPluginConfig() throws Exception {
        Dictionary<String,Object> config = new Hashtable<>();
        tamayaConfigPlugin.setPluginConfig(config);
        assertEquals(tamayaConfigPlugin.getPluginConfig(), config);
    }

    @Test
    public void testSetGetConfigValue() throws Exception {
        Dictionary<String,Object> config = new Hashtable<>();
        String val = (String)tamayaConfigPlugin.getConfigValue("foo");
        tamayaConfigPlugin.setConfigValue("bar", "foo");
        assertEquals(tamayaConfigPlugin.getConfigValue("bar"), "foo");
    }

    @Test
    public void getTMUpdateConfig() throws Exception {
        org.apache.tamaya.Configuration config = tamayaConfigPlugin.getTamayaConfiguration("java.");
        assertNotNull(config);
        assertNull(config.get("jlkjllj"));
        assertEquals(config.get("home"),System.getProperty("java.home"));
    }

    @Test
    public void getUpdateConfig() throws Exception {
        Dictionary<String, Object> config = tamayaConfigPlugin.updateConfig(TamayaConfigPlugin.COMPONENTID);
        assertNotNull(config);
        assertEquals(config.get("java.home"), System.getProperty("java.home"));
    }

    @Test
    public void getUpdateConfig_DryRun() throws Exception {
        Dictionary<String, Object> config = tamayaConfigPlugin.updateConfig(TamayaConfigPlugin.COMPONENTID, true);
        assertNotNull(config);
        assertEquals(config.get("java.home"), System.getProperty("java.home"));
    }

    @Test
    public void getUpdateConfig_Explicit_DryRun() throws Exception {
        Dictionary<String, Object> config = tamayaConfigPlugin.updateConfig(TamayaConfigPlugin.COMPONENTID, OperationMode.EXTEND, true, true);
        assertNotNull(config);
        assertEquals(config.get("java.home"), System.getProperty("java.home"));
    }

    @Test
    public void getPluginConfig() throws Exception {
        Dictionary<String, Object> config = tamayaConfigPlugin.getPluginConfig();
        assertNotNull(config);
        assertEquals(config, super.getProperties(TamayaConfigPlugin.COMPONENTID));
    }

    @Test
    public void getDefaultOperationMode() throws Exception {
        OperationMode om = tamayaConfigPlugin.getDefaultOperationMode();
        assertNotNull(om);
        Dictionary<String,Object> pluginConfig = super.getProperties(TamayaConfigPlugin.COMPONENTID);
        pluginConfig.put(OperationMode.class.getSimpleName(), OperationMode.UPDATE_ONLY.toString());
        TamayaConfigPlugin plugin = new TamayaConfigPlugin(bundleContext);
        om = plugin.getDefaultOperationMode();
        assertNotNull(om);
        assertEquals(om, OperationMode.UPDATE_ONLY);
        pluginConfig.put(OperationMode.class.getSimpleName(), OperationMode.OVERRIDE.toString());
        plugin = new TamayaConfigPlugin(bundleContext);
        om = plugin.getDefaultOperationMode();
        assertNotNull(om);
        assertEquals(om, OperationMode.OVERRIDE);
    }

}