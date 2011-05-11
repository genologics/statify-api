package com.genologics.statsapi.data

import org.squeryl._
import annotations._
import PrimitiveTypeMode._
import java.sql.Timestamp

/**
 * Registration persistent entity.
 */
class Registration(val id: Long,
                   @Column(length = 36)
                   val guid: String,
                   @Column(length = 255)
                   val token: String)
      extends KeyedEntity[Long] {
    def this(guid: String, token: String) = this(0, guid, token)
}

/**
 * LogEntry persistent entity.
 */
class LogEntry(val id: Long,
               @Column(length = 36)
               val guid: String,
               @Column("created_at")
               val createdAt: Timestamp,
               @Column("updated_at")
               val updatedAt: Timestamp,
               val entry: String)
      extends KeyedEntity[Long] {
    def this(guid:String, entry: String, timestamp: Long = System.currentTimeMillis) = this(0, guid, new Timestamp(timestamp), new Timestamp(timestamp), entry)
}

/**
 * StatsSchema.
 */
object StatsSchema extends Schema {

    val registrations = table[Registration]

    val logEntries = table[LogEntry]("log_entry")

    on(registrations)(r => declare(
        r.id is(autoIncremented),
        r.guid is(unique, indexed("ix_registration_guid"), dbType("char(36)"))
    ))

    on(logEntries)(le => declare(
        le.id is(autoIncremented),
        le.guid is(indexed("ix_log_entry_guid"), dbType("char(36)")),
        le.entry is(dbType("longtext"))
    ))
}