package com.valartech.commons.network.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * For Moshi.
 */
class DateTimeAdapter {

    companion object {
        private val SYSTEM_ZONE_ID = ZoneId.systemDefault()
    }

    @ToJson
    fun toJson(zonedDateTime: ZonedDateTime): String {
        return DateTimeFormatter.ISO_DATE_TIME.format(zonedDateTime)
    }

    @FromJson
    fun fromJson(isoDateTimeString: String): ZonedDateTime {
        val utcTime = LocalDateTime.parse(isoDateTimeString)
        //todo update if we're getting time in UTC
//        return utcTime.toInstant(ZoneOffset.from())
        return utcTime.toInstant(ZoneOffset.UTC).atZone(SYSTEM_ZONE_ID)
    }
}
