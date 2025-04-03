package com.alamkanak.weekview

import java.util.Calendar

internal data class FetchRange(
    val previous: Period,
    val current: Period,
    val next: Period
) {
    val periods: List<Period> = listOf(previous, current, next)

    internal companion object {
        fun create(firstVisibleDay: Calendar): FetchRange {
            val current = Period.fromDate(firstVisibleDay)
            return FetchRange(current.previous, current, current.next)
        }
    }
}

internal data class Period(
    val date: Calendar
) : Comparable<Period> {

    val previous: Period
        get() {
            val previousDate = date.clone() as Calendar
            previousDate.add(Calendar.WEEK_OF_YEAR, -2)
            return Period(previousDate)
        }

    val next: Period
        get() {
            val nextDate = date.clone() as Calendar
            nextDate.add(Calendar.WEEK_OF_YEAR, 4)
            return Period(nextDate)
        }

    val startDate: Calendar
        get() {
            val start = date.clone() as Calendar
            start.set(Calendar.DAY_OF_WEEK, start.firstDayOfWeek)
            start.set(Calendar.HOUR_OF_DAY, 0)
            start.set(Calendar.MINUTE, 0)
            start.set(Calendar.SECOND, 0)
            start.set(Calendar.MILLISECOND, 0)
            return start
        }

    val endDate: Calendar
        get() {
            val end = date.clone() as Calendar
            end.set(Calendar.DAY_OF_WEEK, end.firstDayOfWeek)
            end.add(Calendar.WEEK_OF_YEAR, 1)
            end.add(Calendar.MILLISECOND, -1)
            return end
        }

    override fun compareTo(other: Period): Int {
        return date.compareTo(other.date)
    }

    internal companion object {
        fun fromDate(date: Calendar): Period = Period(date)
    }
}
