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
package org.apache.tamaya.jodatime;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.MutablePeriod;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.junit.Test;

import javax.config.Config;
import javax.config.ConfigProvider;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.joda.time.format.ISODateTimeFormat.dateTime;

public class FullStackIT {

    @Test
    public void retrieveJodaTimeValuesFromConfiguration() {

        Config configuration = ConfigProvider.getConfig();

        String dateTimeString = configuration.getValue("dateTimeValue", String.class);
        DateTime dateTimeValue = configuration.getValue("dateTimeValue", DateTime.class);

        assertThat(dateTimeString, notNullValue());
        assertThat(dateTimeString, equalTo("2010-08-08T14:00:15.5+10:00"));
        assertThat(dateTimeValue, notNullValue());
        assertThat(dateTimeValue.getMillis(), equalTo(dateTime().parseDateTime(dateTimeString).getMillis()));
    }

    @Test
    public void retrieveDateTimeZoneValueFromConfiguration() {
        Config configuration = ConfigProvider.getConfig();

        String zoneAAsString = configuration.getValue("dateTimeZoneValueA", String.class);
        DateTimeZone zoneA = configuration.getValue("dateTimeZoneValueA", DateTimeZone.class);

        assertThat(zoneAAsString, equalTo("UTC"));
        assertThat(zoneA, equalTo(DateTimeZone.forID("UTC")));

        String zoneBAsString = configuration.getValue("dateTimeZoneValueB", String.class);
        DateTimeZone zoneB = configuration.getValue("dateTimeZoneValueB", DateTimeZone.class);

        assertThat(zoneBAsString, equalTo("+01:00"));
        assertThat(zoneB, equalTo(DateTimeZone.forOffsetHours(1)));
    }

    @Test
    public void retrievePeriodValueFromConfiguration() {
        Config configuration = ConfigProvider.getConfig();

        MutablePeriod referenceValue = new MutablePeriod();

        ISOPeriodFormat.standard().getParser().parseInto(referenceValue, "P1Y1M1W1DT1H1M1S", 0,
                                                         Locale.ENGLISH);

        String periodAsString = configuration.getValue("periodValueA", String.class);
        Period period = configuration.getValue("periodValueA", Period.class);

        assertThat(periodAsString, equalTo("P1Y1M1W1DT1H1M1S"));
        assertThat(period, equalTo(referenceValue.toPeriod()));
    }
}
