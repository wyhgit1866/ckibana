/*
 * Copyright (c) 2023 LY.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ly.ckibana.converter;

import com.ly.ckibana.CommonTest;
import org.junit.Test;

/**
 * queryString语法测试-结合DATE_HISTOGRAM测试
 *
 * @author zl11357
 * @since 2023/10/19 17:00
 */
public class QueryStringClauseTest extends CommonTest {
    public static final String TEST_STRING_FIELD = "STRING_FIELD";
    public static final String TEST_STRING_FIELD_KEYWORD = "STRING_FIELD_KEYWORD";
    public static final String TEST_STRING_FIELD_IGNORE_CASE = "STRING_FIELD_IGNORE_CASE";
    public static final String TEST_STRING_FIELD_KEYWORD_IGNORE_CASE = "STRING_FIELD_KEYWORD_IGNORECASE";
    public static final String TEST_IP_TYPE_STRING = "IP_TYPE_STRING";
    public static final String TEST_IP_TYPE_IPV4 = "IP_TYPE_IPV4";
    public static final String TEST_IP_TYPE_IPV6 = "IP_TYPE_IPV6";
    public static final String TEST_NUMBER = "NUMBER";


    /**
     * queryString-testStringField
     * eq notequal like notlike in notin 基于原字段查询测试.包括字符串模糊规则
     * 如s1:"s1value%" AND NOT s2:"%s2value" AND (s2:"s2value1" OR s2:"s2value2") AND s3:("s3value1","s3value2") AND NOT s3:("s3value3","s3value4")
     */
    @Test
    public void testStringField() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1:\\\"s1value%\\\" AND NOT s2:\\\"%s2value\\\" AND (s2:\\\"s2value1\\\" OR s2:\\\"s2value2\\\") AND s3:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1:\\\"s1value%\\\" AND NOT s2:\\\"%s2value\\\" AND (s2:\\\"s2value1\\\" OR s2:\\\"s2value2\\\") AND s3:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1697879605153,\"lte\":1697880505153,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  `s1` like 's1value%' AND NOT  `s2` like '%s2value' AND ( `s2` like '%s2value1%' OR  `s2` like '%s2value2%') AND  (`s3` like '%s3value1%' OR `s3` like '%s3value2%') AND NOT  (`s3` like '%s3value3%' OR `s3` like '%s3value4%') )  AND  (  `s1` like 's1value%' AND NOT  `s2` like '%s2value' AND ( `s2` like '%s2value1%' OR  `s2` like '%s2value2%') AND  (`s3` like '%s3value1%' OR `s3` like '%s3value2%') AND NOT  (`s3` like '%s3value3%' OR `s3` like '%s3value4%') ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1697880500000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1697879600000  ))\"\n" +
                "            ]";
        doTest(TEST_STRING_FIELD, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testStringFieldKeyword
     * eq notequal like notlike in notin 基于字段.keyword精确查询测试
     * 如s1.keyword:"s1value" AND NOT s2.keyword:"s2value" AND (s2.keyword:"s2value1" OR s2.keyword:"s2value2") AND s3.keyword:("s3value1","s3value2") AND NOT s3.keyword:("s3value3","s3value4")
     */
    @Test
    public void testStringFieldKeyword() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1.keyword:\\\"s1value\\\" AND NOT s2.keyword:\\\"s2value\\\" AND (s2.keyword:\\\"s2value1\\\" OR s2.keyword:\\\"s2value2\\\") AND s3.keyword:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3.keyword:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1.keyword:\\\"s1value\\\" AND NOT s2.keyword:\\\"s2value\\\" AND (s2.keyword:\\\"s2value1\\\" OR s2.keyword:\\\"s2value2\\\") AND s3.keyword:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3.keyword:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1697879515585,\"lte\":1697880415585,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  (`s1` = 's1value') AND NOT  (`s2` = 's2value') AND ( (`s2` = 's2value1') OR  (`s2` = 's2value2')) AND  ((`s3` = 's3value1') OR (`s3` = 's3value2')) AND NOT  ((`s3` = 's3value3') OR (`s3` = 's3value4')) )  AND  (  (`s1` = 's1value') AND NOT  (`s2` = 's2value') AND ( (`s2` = 's2value1') OR  (`s2` = 's2value2')) AND  ((`s3` = 's3value1') OR (`s3` = 's3value2')) AND NOT  ((`s3` = 's3value3') OR (`s3` = 's3value4')) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1697880410000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1697879510000  ))\"\n" +
                "            ]";
        doTest(TEST_STRING_FIELD_KEYWORD, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testStringFieldCaseIgnore
     * eq notequal like notlike in notin 基于字段.caseIgnore 忽略大小写模糊查询测试
     * 如s1.caseIgnore:"s1value" AND NOT s2.caseIgnore:"s2value" AND (s2.caseIgnore:"s2value1" OR s2.caseIgnore:"s2value2") AND s3.caseIgnore:("s3value1","s3value2") AND NOT s3.caseIgnore:("s3value3","s3value4")
     */
    @Test
    public void testStringFieldCaseIgnore() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1.caseIgnore:\\\"s1value\\\" AND NOT s2.caseIgnore:\\\"s2value\\\" AND (s2.caseIgnore:\\\"s2value1\\\" OR s2.caseIgnore:\\\"s2value2\\\") AND s3.caseIgnore:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3.caseIgnore:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1.caseIgnore:\\\"s1value\\\" AND NOT s2.caseIgnore:\\\"s2value\\\" AND (s2.caseIgnore:\\\"s2value1\\\" OR s2.caseIgnore:\\\"s2value2\\\") AND s3.caseIgnore:(\\\"s3value1\\\",\\\"s3value2\\\") AND NOT s3.caseIgnore:(\\\"s3value3\\\",\\\"s3value4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1697879405576,\"lte\":1697880305576,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  (positionCaseInsensitive(`s1`, 's1value') != 0) AND NOT  (positionCaseInsensitive(`s2`, 's2value') != 0) AND ( (positionCaseInsensitive(`s2`, 's2value1') != 0) OR  (positionCaseInsensitive(`s2`, 's2value2') != 0)) AND  ((positionCaseInsensitive(`s3`, 's3value1') != 0) OR (positionCaseInsensitive(`s3`, 's3value2') != 0)) AND NOT  ((positionCaseInsensitive(`s3`, 's3value3') != 0) OR (positionCaseInsensitive(`s3`, 's3value4') != 0)) )  AND  (  (positionCaseInsensitive(`s1`, 's1value') != 0) AND NOT  (positionCaseInsensitive(`s2`, 's2value') != 0) AND ( (positionCaseInsensitive(`s2`, 's2value1') != 0) OR  (positionCaseInsensitive(`s2`, 's2value2') != 0)) AND  ((positionCaseInsensitive(`s3`, 's3value1') != 0) OR (positionCaseInsensitive(`s3`, 's3value2') != 0)) AND NOT  ((positionCaseInsensitive(`s3`, 's3value3') != 0) OR (positionCaseInsensitive(`s3`, 's3value4') != 0)) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1697880300000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1697879400000  ))\"\n" +
                "            ]";
        doTest(TEST_STRING_FIELD_IGNORE_CASE, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testStringFieldCaseIgnoreKeyword
     * eq notequal like notlike in notin基于字段.keyword.caseIgnore 忽略大小 精确查询测试。clickhouse不支持
     * 如s1.caseIgnore.keyword:"s1VALUe" AND NOT s2.caseIgnore.keyword:"s2VALUe" AND (s2.caseIgnore.keyword:"s2VALUe1" OR s2.caseIgnore.keyword:"s2VALUe2") AND s3.caseIgnore.keyword:("s3VALUe1","s3VALUe2") AND NOT s3.caseIgnore.keyword:("s3VALUe3","s3VALUe4")
     */
    @Test
    public void testStringFieldCaseIgnoreKeyword() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1.caseIgnore.keyword:\\\"s1VALUe\\\" AND NOT s2.caseIgnore.keyword:\\\"s2VALUe\\\" AND (s2.caseIgnore.keyword:\\\"s2VALUe1\\\" OR s2.caseIgnore.keyword:\\\"s2VALUe2\\\") AND s3.caseIgnore.keyword:(\\\"s3VALUe1\\\",\\\"s3VALUe2\\\") AND NOT s3.caseIgnore.keyword:(\\\"s3VALUe3\\\",\\\"s3VALUe4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1.caseIgnore.keyword:\\\"s1VALUe\\\" AND NOT s2.caseIgnore.keyword:\\\"s2VALUe\\\" AND (s2.caseIgnore.keyword:\\\"s2VALUe1\\\" OR s2.caseIgnore.keyword:\\\"s2VALUe2\\\") AND s3.caseIgnore.keyword:(\\\"s3VALUe1\\\",\\\"s3VALUe2\\\") AND NOT s3.caseIgnore.keyword:(\\\"s3VALUe3\\\",\\\"s3VALUe4\\\")\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1697879117903,\"lte\":1697880017903,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  (lower('s1') = 's1value') AND NOT  (lower('s2') = 's2value') AND ( (lower('s2') = 's2value1') OR  (lower('s2') = 's2value2')) AND  ((lower('`s3`') = 's3value1') OR (lower('`s3`') = 's3value2')) AND NOT  ((lower('`s3`') = 's3value3') OR (lower('`s3`') = 's3value4')) )  AND  (  (lower('s1') = 's1value') AND NOT  (lower('s2') = 's2value') AND ( (lower('s2') = 's2value1') OR  (lower('s2') = 's2value2')) AND  ((lower('`s3`') = 's3value1') OR (lower('`s3`') = 's3value2')) AND NOT  ((lower('`s3`') = 's3value3') OR (lower('`s3`') = 's3value4')) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1697880010000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1697879110000  ))\"\n" +
                "            ]";
        doTest(TEST_STRING_FIELD_KEYWORD_IGNORE_CASE, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testIpWithStringType
     * ip Ipv4类型。equal notequal range not range in notin 包括ipv4
     * 如s1:"s1value1" AND  (ip:"1.1.1.1" AND NOT ip:"2.2.2.2" AND ip:["3.3.3.3" TO "4.4.4.4"] AND NOT ip:["5.5.5.5" TO "6.6.6.6"])  OR ( ip:"1.1.1.1" AND NOT ip:"2.2.2.2" AND ip:["3.3.3.3" TO "4.4.4.4"] AND NOT ip:["5.5.5.5" TO "6.6.6.6"] ) AND  (ip:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND NOT ip:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND ip:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] AND NOT ip:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"])  OR ( ip:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND NOT ip:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND ip:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] AND NOT ip:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] ) AND  ip:("8.8.8.8","9.9.9.9") AND ip: ("2001:0db8:85a3:0000:0000:8a2e:0370:7334","2001:0db8:85a3:0000:0000:8a2e:0370:7334")
     */
    @Test
    public void testIpWithStringType() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ip:\\\"1.1.1.1\\\" AND NOT ip:\\\"2.2.2.2\\\" AND ip:[\\\"3.3.3.3\\\" TO \\\"4.4.4.4\\\"] AND NOT ip:[\\\"5.5.5.5\\\" TO \\\"6.6.6.6\\\"])  OR (ip:(\\\"7.7.7.7\\\",\\\"8.8.8.8\\\")  AND ip:(\\\"9.9.9.9\\\",\\\"10.10.10.10\\\") ) AND  (ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"])  OR ( ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] ) AND ip:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\") AND NOT ( ip:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ip:\\\"1.1.1.1\\\" AND NOT ip:\\\"2.2.2.2\\\" AND ip:[\\\"3.3.3.3\\\" TO \\\"4.4.4.4\\\"] AND NOT ip:[\\\"5.5.5.5\\\" TO \\\"6.6.6.6\\\"])  OR (ip:(\\\"7.7.7.7\\\",\\\"8.8.8.8\\\")  AND ip:(\\\"9.9.9.9\\\",\\\"10.10.10.10\\\") ) AND  (ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"])  OR ( ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ip:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ip:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] ) AND ip:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\") AND NOT ( ip:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1698054551914,\"lte\":1698055451914,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  `s1` like '%s1value1%' AND ( `ip` like '%1.1.1.1%' AND NOT  `ip` like '%2.2.2.2%' AND  ( IPv4StringToNumOrDefault(`ip`) >= IPv4StringToNum('3.3.3.3') AND IPv4StringToNumOrDefault(`ip`) <= IPv4StringToNum('4.4.4.4')) AND NOT  ( IPv4StringToNumOrDefault(`ip`) >= IPv4StringToNum('5.5.5.5') AND IPv4StringToNumOrDefault(`ip`) <= IPv4StringToNum('6.6.6.6'))) OR ( (`ip` like '%7.7.7.7%' OR `ip` like '%8.8.8.8%') AND  (`ip` like '%9.9.9.9%' OR `ip` like '%10.10.10.10%')) AND ( `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND NOT  `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) OR ( `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND NOT  `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) AND  (`ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' OR `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%') AND NOT ( (`ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' OR `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%')) )  AND  (  `s1` like '%s1value1%' AND ( `ip` like '%1.1.1.1%' AND NOT  `ip` like '%2.2.2.2%' AND  ( IPv4StringToNumOrDefault(`ip`) >= IPv4StringToNum('3.3.3.3') AND IPv4StringToNumOrDefault(`ip`) <= IPv4StringToNum('4.4.4.4')) AND NOT  ( IPv4StringToNumOrDefault(`ip`) >= IPv4StringToNum('5.5.5.5') AND IPv4StringToNumOrDefault(`ip`) <= IPv4StringToNum('6.6.6.6'))) OR ( (`ip` like '%7.7.7.7%' OR `ip` like '%8.8.8.8%') AND  (`ip` like '%9.9.9.9%' OR `ip` like '%10.10.10.10%')) AND ( `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND NOT  `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) OR ( `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND NOT  `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' AND  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( IPv6StringToNumOrDefault(`ip`) >= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND IPv6StringToNumOrDefault(`ip`) <= IPv6StringToNum('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) AND  (`ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' OR `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%') AND NOT ( (`ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%' OR `ip` like '%2001:0db8:85a3:0000:0000:8a2e:0370:7334%')) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1698055450000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1698054550000  ))\"\n" +
                "            ]";
        doTest(TEST_IP_TYPE_STRING, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testIpWithIpv4Type
     * ipv4 IPv4类型。equal notequal range not range in notin
     *s1:"s1value1" AND  (ipv4:"1.1.1.1" AND NOT ipv4:"2.2.2.2" AND ipv4:["3.3.3.3" TO "4.4.4.4"] AND NOT ipv4:["5.5.5.5" TO "6.6.6.6"])  OR ( ipv4:("7.7.7.7","8.8.8.8")  AND NOT ipv4:("9.9.9.9.9","10.10.10.10"))
     */
    @Test
    public void testIpWithIpv4Type() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ipv4:\\\"1.1.1.1\\\" AND NOT ipv4:\\\"2.2.2.2\\\" AND ipv4:[\\\"3.3.3.3\\\" TO \\\"4.4.4.4\\\"] AND NOT ipv4:[\\\"5.5.5.5\\\" TO \\\"6.6.6.6\\\"])  OR ( ipv4:(\\\"7.7.7.7\\\",\\\"8.8.8.8\\\")  AND NOT ipv4:(\\\"9.9.9.9.9\\\",\\\"10.10.10.10\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ipv4:\\\"1.1.1.1\\\" AND NOT ipv4:\\\"2.2.2.2\\\" AND ipv4:[\\\"3.3.3.3\\\" TO \\\"4.4.4.4\\\"] AND NOT ipv4:[\\\"5.5.5.5\\\" TO \\\"6.6.6.6\\\"])  OR ( ipv4:(\\\"7.7.7.7\\\",\\\"8.8.8.8\\\")  AND NOT ipv4:(\\\"9.9.9.9.9\\\",\\\"10.10.10.10\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1698054288267,\"lte\":1698055188267,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  `s1` like '%s1value1%' AND ( (`ipv4` = IPv4StringToNumOrDefault('1.1.1.1')) AND NOT  (`ipv4` = IPv4StringToNumOrDefault('2.2.2.2')) AND  ( `ipv4` >= IPv4StringToNumOrDefault('3.3.3.3') AND `ipv4` <= IPv4StringToNumOrDefault('4.4.4.4')) AND NOT  ( `ipv4` >= IPv4StringToNumOrDefault('5.5.5.5') AND `ipv4` <= IPv4StringToNumOrDefault('6.6.6.6'))) OR ( `ipv4` in (IPv4StringToNumOrDefault('7.7.7.7'),IPv4StringToNumOrDefault('8.8.8.8')) AND NOT  `ipv4` in (IPv4StringToNumOrDefault('9.9.9.9.9'),IPv4StringToNumOrDefault('10.10.10.10'))) )  AND  (  `s1` like '%s1value1%' AND ( (`ipv4` = IPv4StringToNumOrDefault('1.1.1.1')) AND NOT  (`ipv4` = IPv4StringToNumOrDefault('2.2.2.2')) AND  ( `ipv4` >= IPv4StringToNumOrDefault('3.3.3.3') AND `ipv4` <= IPv4StringToNumOrDefault('4.4.4.4')) AND NOT  ( `ipv4` >= IPv4StringToNumOrDefault('5.5.5.5') AND `ipv4` <= IPv4StringToNumOrDefault('6.6.6.6'))) OR ( `ipv4` in (IPv4StringToNumOrDefault('7.7.7.7'),IPv4StringToNumOrDefault('8.8.8.8')) AND NOT  `ipv4` in (IPv4StringToNumOrDefault('9.9.9.9.9'),IPv4StringToNumOrDefault('10.10.10.10'))) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1698055180000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1698054280000  ))\"\n" +
                "            ]";
        doTest(TEST_IP_TYPE_IPV4, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testIpWithIpv6Type
     * ipv6 IPv6类型。equal notequal range not range in notin
     * s1:"s1value1" AND  (ipv6:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND NOT ipv6:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND ipv6:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] AND NOT ipv6:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"])  OR ( ipv6:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND NOT ipv6:"2001:0db8:85a3:0000:0000:8a2e:0370:7334" AND ipv6:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] AND NOT ipv6:["2001:0db8:85a3:0000:0000:8a2e:0370:7334" TO "2001:0db8:85a3:0000:0000:8a2e:0370:7334"] ) AND ipv6:("2001:0db8:85a3:0000:0000:8a2e:0370:7334","2001:0db8:85a3:0000:0000:8a2e:0370:7334") AND NOT ( ipv6:("2001:0db8:85a3:0000:0000:8a2e:0370:7334","2001:0db8:85a3:0000:0000:8a2e:0370:7334"))
     */
    @Test
    public void testIpWithIpv6Type() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"])  OR ( ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] ) AND ipv6:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\") AND NOT ( ipv6:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"s1:\\\"s1value1\\\" AND  (ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"])  OR ( ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND NOT ipv6:\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" AND ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] AND NOT ipv6:[\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\" TO \\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"] ) AND ipv6:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\") AND NOT ( ipv6:(\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\",\\\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\\\"))\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1698054706846,\"lte\":1698055606846,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  `s1` like '%s1value1%' AND ( (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) OR ( (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) AND  `ipv6` in (IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT ( `ipv6` in (IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) )  AND  (  `s1` like '%s1value1%' AND ( (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) OR ( (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  (`ipv6` = IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT  ( `ipv6` >= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334') AND `ipv6` <= IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) AND  `ipv6` in (IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334')) AND NOT ( `ipv6` in (IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'),IPv6StringToNumOrDefault('2001:0db8:85a3:0000:0000:8a2e:0370:7334'))) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1698055600000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1698054700000  ))\"\n" +
                "            ]";
        doTest(TEST_IP_TYPE_IPV6, query, Boolean.FALSE, expectedSqls);
    }

    /**
     * queryString-testNumberField
     * number类型 eq notequal range not range le let ge get
     * i1:1 AND NOT i1:2 AND i1:[2 TO 3] AND NOT i1:[4 TO 5] AND i1:(6,7)  AND NOT i1:(8,9) AND (i2:>10  AND i2:>=11 OR (i2:<10  AND i2:<=11)) AND (NOT i3:>12  AND  NOT i3:>=13 AND NOT i3:<14  AND NOT i2:<=15)
     */
    @Test
    public void testNumberField() {
        String query = "{\"aggs\":{},\"size\":0,\"_source\":{\"excludes\":[]},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[{\"field\":\"@timestampDateTime\",\"format\":\"date_time\"}],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"i1:1 AND NOT i1:2 AND i1:[2 TO 3] AND NOT i1:[4 TO 5] AND i1:(6,7)  AND NOT i1:(8,9) AND (i2:>10  AND i2:>=11 OR (i2:<10  AND i2:<=11)) AND (NOT i3:>12  AND  NOT i3:>=13 AND NOT i3:<14  AND NOT i2:<=15)\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"query_string\":{\"query\":\"i1:1 AND NOT i1:2 AND i1:[2 TO 3] AND NOT i1:[4 TO 5] AND i1:(6,7)  AND NOT i1:(8,9) AND (i2:>10  AND i2:>=11 OR (i2:<10  AND i2:<=11)) AND (NOT i3:>12  AND  NOT i3:>=13 AND NOT i3:<14  AND NOT i2:<=15)\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestampDateTime\":{\"gte\":1697889668151,\"lte\":1697890568151,\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"timeout\":\"120000ms\"}";
        String expectedSqls = "[\n" +
                "                \"SELECT count(1) as _count FROM `table1_all` PREWHERE ( (  `i1` = 1 AND NOT  `i1` = 2 AND  ( `i1` >= 2 AND `i1` <= 3) AND NOT  ( `i1` >= 4 AND `i1` <= 5) AND  `i1` in (6,7) AND NOT  `i1` in (8,9) AND ( `i2`  >10 AND  `i2`  >=11 OR ( `i2`  <10 AND  `i2`  <=11)) AND ( NOT  `i3`  >12 AND NOT  `i3`  >=13 AND NOT  `i3`  <14 AND NOT  `i2`  <=15) )  AND  (  `i1` = 1 AND NOT  `i1` = 2 AND  ( `i1` >= 2 AND `i1` <= 3) AND NOT  ( `i1` >= 4 AND `i1` <= 5) AND  `i1` in (6,7) AND NOT  `i1` in (8,9) AND ( `i2`  >10 AND  `i2`  >=11 OR ( `i2`  <10 AND  `i2`  <=11)) AND ( NOT  `i3`  >12 AND NOT  `i3`  >=13 AND NOT  `i3`  <14 AND NOT  `i2`  <=15) ) ) AND ((  toUnixTimestamp64Milli(`@timestampDateTime`) <= 1697890560000  AND  toUnixTimestamp64Milli(`@timestampDateTime`) >= 1697889660000  ))\"\n" +
                "            ]";
        doTest(TEST_NUMBER, query, Boolean.FALSE, expectedSqls);
    }
}
