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
package com.ly.ckibana.strategy.clause;

import com.ly.ckibana.model.compute.QueryClause;
import com.ly.ckibana.model.enums.QueryClauseType;

/**
 * 查询策略.
 * @author zl
 */
public interface ClauseStrategy {

    /**
     * 转换为sql.
     *
     * @param queryClause queryClause
     * @return sql
     */
    String toSql(QueryClause queryClause);

    /**
     * 获取类型.
     *
     * @return QueryClauseType
     */
    QueryClauseType getType();
}
