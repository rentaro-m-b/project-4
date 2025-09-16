package com.example.common

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

object DSLContextProvider {
    fun provideDSLContext(dataSource: DataSource): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)
}
