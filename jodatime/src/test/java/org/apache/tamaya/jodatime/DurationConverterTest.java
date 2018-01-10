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

import org.apache.tamaya.base.convert.ConversionContext;
import org.joda.time.Duration;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class DurationConverterTest {

    /*
     * I am aware of the 'Parameterized tests' feature of JUnit but
     * decided not to use it. Oliver B. Fischer, 3th April 2015
     */

    private static DurationConverter converter = new DurationConverter();

    private static PeriodFormatter FORMATTER = ISOPeriodFormat.standard();

    @Test
    public void canConvertPropertiesInAllSupportedFormats() {
        Object[][] inputResultPairs = {
                // Duration format
                {"PT72.345S", Duration.parse("PT72.345S")},
                // ISO format
                {"P1D", FORMATTER.parsePeriod("P1DT0H0M0S").toStandardDuration()},
                {"PT1S", FORMATTER.parsePeriod("P0DT0H0M1S").toStandardDuration()},

                // Alternative format
                {"00T00:00:05", FORMATTER.parsePeriod("P0DT0H0M5S").toStandardDuration()},

        };

        for (Object[] pair : inputResultPairs) {
            Duration duration = converter.convert((String) pair[0]);

            assertThat("Converter failed to convert input value " + pair[0], duration, notNullValue());
            assertThat(duration, equalTo((Duration) pair[1]));
        }
    }

    @Test
    public void invalidInputValuesResultInReturningNull() {
        String[] inputValues = {
                "01-03T00:00:05",
                "00:00:05",
                "000011215",
                "-",
                "fooBar",
        };

        for (String input : inputValues) {
            Duration duration = converter.convert(input);

            assertThat(duration, nullValue());
        }
    }

    @Test
    public void allSupportedFormatsAreAddedToTheConversionContext() {
        String name = DurationConverter.class.getSimpleName();

        ConversionContext context = new ConversionContext.Builder(null, Duration.class).build();
        ConversionContext.setContext(context);

        converter.convert("P0DT0H0M0S");
        ConversionContext.reset();
        assertThat(context.getSupportedFormats(), hasSize(5));
        assertThat(context.getSupportedFormats(), hasItem("PdDThHmMsS (" + name + ")"));
        assertThat(context.getSupportedFormats(), hasItem("ddThh:mm:ss (" + name + ")"));
        assertThat(context.getSupportedFormats(), hasItem("PTa.bS ("+name+")"));
    }
}
