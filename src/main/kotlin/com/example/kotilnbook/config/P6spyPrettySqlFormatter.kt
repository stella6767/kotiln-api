package com.example.kotilnbook.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import java.text.SimpleDateFormat
import java.util.*

class P6spyPrettySqlFormatter(

) : MessageFormattingStrategy{

    /**
     *  'var' on function parameter is not allowed - Kotlin에서 함수 파라미터로 var을 허용하지 않는 이유 / https://thecommelier.tistory.com/10
     */

    override fun formatMessage(connectionId: Int, now: String?, elapsed: Long, category: String?, prepared: String?, sql: String?, url: String?): String {
        val sql = formatSql(category!!, sql!!)
        val currentDate = Date()
        val format1 = SimpleDateFormat("yy.MM.dd HH:mm:ss")
        //return now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + P6Util.singleLine(prepared) + sql;
        return format1.format(currentDate) + " | " + "OperationTime : " + elapsed + "ms" + sql
    }


    private fun formatSql(category: String, sql: String): String? {
        var sql: String = sql
        if (sql.trim { it <= ' ' } == "") return sql

        // Only format Statement, distinguish DDL And DML
        if (Category.STATEMENT.name == category) {
            val tmpsql = sql.trim { it <= ' ' }.lowercase()
            sql = if (tmpsql.startsWith("create") || tmpsql.startsWith("alter") || tmpsql.startsWith("comment")) {
                FormatStyle.DDL.formatter.format(sql)
            } else {
                FormatStyle.BASIC.formatter.format(sql)
            }
            sql = "|\nHeFormatSql(P6Spy sql,Hibernate format):$sql"
        }
        return sql
    }

}