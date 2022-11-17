// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

suite("test_insert") {
    // todo: test insert, such as insert values, insert select, insert txn
    sql "show load"

    try {
    sql """
    CREATE TABLE test_insert1 (
    c1 varchar(10) NULL,
    v1 DECIMAL(18,6) NULL COMMENT "",
    v2 DECIMAL(18,6) NULL COMMENT ""
    ) ENGINE=OLAP
    DUPLICATE KEY(c1)
    COMMENT "OLAP"
    DISTRIBUTED BY HASH(c1) BUCKETS 3
      PROPERTIES (
      "replication_allocation" = "tag.location.default: 1",
      "in_memory" = "false",
      "storage_format" = "V2"
    )
    """

      sql """ insert into test_insert1(c1, v1, v2) values ('r1', 1.1, 1.2),('r2', 2.1, 2.2) """

      qt_select """select count(*) from test_insert1;"""

      sql """
    CREATE TABLE test_insert2 (
    c1 varchar(10) NULL,
    v1 DECIMAL(18,6) NULL COMMENT "",
    mv_v2 DECIMAL(18,6) NULL COMMENT ""
    ) ENGINE=OLAP
    DUPLICATE KEY(c1)
    COMMENT "OLAP"
    DISTRIBUTED BY HASH(c1) BUCKETS 3
      PROPERTIES (
      "replication_allocation" = "tag.location.default: 1",
      "in_memory" = "false",
      "storage_format" = "V2"
    )
    """

      sql """ insert into test_insert2(c1, v1, mv_v2) values ('r1', 1.1, 1.2),('r2', 2.1, 2.2) """

      qt_select """select count(*) from test_insert2;"""

    } finally {
        sql """ DROP TABLE IF EXISTS test_insert1 force"""

        sql """ DROP TABLE IF EXISTS test_insert2 force"""
    }
}
