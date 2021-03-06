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
package org.apache.tamaya.metamodel;

import org.apache.tamaya.spi.ConfigurationContext;
import org.apache.tamaya.spi.FilterContext;
import org.apache.tamaya.spi.PropertyValue;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


public class MaskFilterTest {

    @Test
    public void getSetMatches() {
        MaskFilter filter = new MaskFilter();
        filter.setMatches("*.SEC");
        assertThat(filter.getMatches()).isNotNull().isEqualTo("*.SEC");
    }

    @Test
    public void getSetRoles() {
        MaskFilter filter = new MaskFilter();
        filter.setRoles("a", "b");
        assertThat(Arrays.asList("a", "b")).isEqualTo(filter.getRoles());

        filter = new MaskFilter();
        filter.setRoles(Arrays.asList("a", "b"));
        assertThat(Arrays.asList("a", "b")).isEqualTo(filter.getRoles());
    }

    @Test
    public void getSetMask() {
        MaskFilter filter = new MaskFilter();
        filter.setMask("*");
        assertThat(filter.getMask()).isEqualTo("*");
    }

    @Test
    public void setGetFilterAllValues() {
        MaskFilter filter = new MaskFilter();
        filter.setFilterAllValues(true);
        assertThat(filter.isFilterAllValues()).isTrue();
    }

    @Test
    public void setGetFilterSingleValues() {
        MaskFilter filter = new MaskFilter();
        filter.setFilterSingleValues(true);
        assertThat(filter.isFilterSingleValues()).isTrue();
    }


    @Test
    public void filterProperty() {
        MaskFilter filter = new MaskFilter();
        filter.setMatches(".*\\.SEC");
        assertThat(filter.getMatches()).isEqualTo(".*\\.SEC");
        PropertyValue value = PropertyValue.createValue("foo.SEC", "someValue");
        PropertyValue filtered = filter.filterProperty(value,
                new FilterContext(value, Collections.emptyMap(), ConfigurationContext.EMPTY));
        assertThat(filtered).isNotNull();
        assertThat(filtered.getValue()).isNotNull().isEqualTo("*****");
    }

    @Test
    public void testToString() {
        MaskFilter filter = new MaskFilter();
        filter.setMatches(".*\\.SEC");
        filter.setMask("*****");
        filter.setFilterSingleValues(true);
        filter.setMask("123");
        assertThat(filter.toString()).isEqualTo("MaskFilter{matches='.*\\.SEC', mask='123', roles='[]', filterAllValues='true', " +
                "filterSingleValues='true'}");
    }
}