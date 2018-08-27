package com.base.muslim.camera.utils

import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间管理类
 *
 * @author Super
 * @Date 2014-5-21 下午2:24:49
 */
object DATE {
    /**
     * 时间日期格式化到年月日时分秒.
     */
    val dateFormatMDY = "MM/dd/yyyy"
    /**
     * 时间日期格式化到年月日时分秒.
     */
    val dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss"
    /**
     * 时间日期格式化到年月日.
     */
    val dateFormatYMD = "yyyy-MM-dd"
    /**
     * 时间日期格式化到年月.
     */
    val dateFormatYM = "yyyy-MM"
    /**
     * 时间日期格式化到年月日时分.
     */
    val dateFormatYMDHM = "yyyy-MM-dd HH:mm"
    /**
     * 时间日期格式化到月日.
     */
    val dateFormatMD = "MM/dd"
    /**
     * 时分秒.
     */
    val dateFormatHMS = "HH:mm:ss"
    /**
     * 时分.
     */
    val dateFormatHM = "HH:mm"

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日时分秒
     *
     * @return
     */
    val dateYMDHMS: String
        get() {
            val format = SimpleDateFormat(dateFormatYMDHMS)
            return format.format(java.util.Date())
        }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日
     *
     * @return
     */
    val dateYMD: String
        get() {
            val format = SimpleDateFormat(dateFormatYMD)
            return format.format(java.util.Date())
        }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到年月日时分
     *
     * @return
     */
    val dateYMDHM: String
        get() {
            val format = SimpleDateFormat(dateFormatYMDHM)
            return format.format(java.util.Date())
        }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到时分秒
     *
     * @return
     */
    val dateHMS: String
        get() {
            val format = SimpleDateFormat(dateFormatHMS)
            return format.format(java.util.Date())
        }

    /**
     * 系统当前时间
     * <br></br>时间日期格式化到时分
     *
     * @return
     */
    val dateHM: String
        get() {
            val format = SimpleDateFormat(dateFormatHM)
            return format.format(java.util.Date())
        }

    /**
     * 系统当前时间
     *
     * @return
     */
    fun currDateLong(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前日期，将字符串转换为整数yyyyMMdd
     *
     * @return
     */
    fun currDate(): Int {
        val date = Date() //当前时间，转为整数存储
        return Integer.parseInt(DateFormat.format("yyyyMMdd", date).toString())
    }

    /**
     * 根据时间获取到Long的时间
     *
     * @param date
     * @return
     */
    fun getDateLong(date: java.util.Date): Long {
        return date.time
    }

    /**
     * 根据时间获取到Long的时间
     *
     * @param date
     * @return
     */
    fun getDateLong(date: String): Long {
        val format = SimpleDateFormat(dateFormatYMDHMS)
        try {
            val tmp = format.parse(date)
            return getDateLong(tmp)
        } catch (e: ParseException) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分秒
     *
     * @return
     */
    fun getDateYMDHMS(date: Long): String {
        val format = SimpleDateFormat(dateFormatYMDHMS)
        val date2 = java.util.Date(date)
        return format.format(date2)
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日
     *
     * @return
     */
    fun getDateYMD(date: Long): String {
        val format = SimpleDateFormat(dateFormatYMD)
        val date2 = java.util.Date(date)
        return format.format(date2)
    }

    fun getDateMDY(date: Long): String {
        val format = SimpleDateFormat(dateFormatMDY)
        val date2 = java.util.Date(date)
        return format.format(date2)
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分
     *
     * @return
     */
    fun getDateYMDHM(date: Long): String {
        val format = SimpleDateFormat(dateFormatYMDHM)
        val date2 = java.util.Date(date)
        return format.format(date2)
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分秒
     *
     * @return
     */
    fun getDateYMDHMS(date: java.util.Date): String {
        val format = SimpleDateFormat(dateFormatYMDHMS)
        return format.format(date)
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日
     *
     * @return
     */
    fun getDateYMD(date: java.util.Date): String {
        val format = SimpleDateFormat(dateFormatYMD)
        return format.format(date)
    }

    /**
     * 根据Date 转为为响应格式的时间
     * <br></br>时间日期格式化到年月日时分
     *
     * @return
     */
    fun getDateYMDHM(date: java.util.Date): String {
        val format = SimpleDateFormat(dateFormatYMDHM)
        return format.format(date)
    }

    /**
     * 根据long 转为为响应格式的时间
     * <br></br>时间日期格式化到时分秒
     *
     * @return
     */
    fun getDateHMS(date: Long): String {
        val format = SimpleDateFormat(dateFormatHMS)
        val date2 = java.util.Date(date)
        return format.format(date2)
    }

    /**
     * 两个时间比较
     *
     * @param src
     * @param dst
     * @return 1：src 在 dst 后、 -1 dst 在 src 后 、0 相同
     */
    fun compare(src: Long, dst: Long): Int {
        var compare = 0
        if (src > dst) {
            compare = 1
        } else if (src < dst) {
            compare = -1
        }
        return compare
    }

    /**
     * 两个时间比较
     *
     * @param src
     * @param dst
     * @return 1：src 在 dst 后、 -1 dst 在 src 后 、0 相同
     */
    fun compare(src: java.util.Date?, dst: java.util.Date?): Int {
        try {
            return compare(src!!.time, dst!!.time)
        } catch (e: Exception) {
            return -2
        }

    }

    /**
     * 两个时间比较
     *
     * @param src
     * @param dst
     * @return 1：src 在 dst 后、 -1 dst 在 src 后 、0 相同
     */
    fun compare(src: String, dst: String): Int {
        var srcDate: java.util.Date? = null
        var dstDate: java.util.Date? = null
        try {
            val srcF = SimpleDateFormat(dateFormatYMDHMS)
            val dstF = SimpleDateFormat(dateFormatYMDHMS)
            srcDate = srcF.parse(src)
            dstDate = dstF.parse(dst)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return compare(srcDate, dstDate)
    }

    /**
     * 描述：判断是否是闰年()
     *
     * (year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    fun isLeapYear(year: Int): Boolean {
        return if (year % 4 == 0 && year % 400 != 0 || year % 400 == 0) {
            true
        } else {
            false
        }
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date   日期时间
     * @param offset 偏移天(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    fun getDateOffsetDay(date: java.util.Date, day: Int): java.util.Date {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = date
            calendar.add(Calendar.DATE, day)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar.time
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date   日期时间
     * @param offset 偏移小时(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    fun getDateOffsetHOUR(date: java.util.Date, hour: Int): java.util.Date {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = date
            calendar.add(Calendar.HOUR, hour)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar.time
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date   日期时间
     * @param offset 偏移小时(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    fun getDateOffsetMinute(date: java.util.Date, minute: Int): java.util.Date {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = date
            calendar.add(Calendar.MINUTE, minute)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar.time
    }

    /**
     * 一个星期后
     * 描述：获取偏移之后的Date.
     *
     * @param date 日期时间
     * @return Date 偏移之后的日期时间
     */
    fun getDateOffsetWeek(date: java.util.Date): java.util.Date {
        val calendar = Calendar.getInstance()
        try {
            calendar.time = date
            calendar.add(Calendar.DATE, 7)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return calendar.time
    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    fun getOffsetDay(date1: Long, date2: Long = Date().time): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = date1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = date2
        //先判断是否同年
        val y1 = calendar1.get(Calendar.YEAR)
        val y2 = calendar2.get(Calendar.YEAR)
        val d1 = calendar1.get(Calendar.DAY_OF_YEAR)
        val d2 = calendar2.get(Calendar.DAY_OF_YEAR)
        var maxDays = 0
        var day = 0
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR)
            day = d1 - d2 + maxDays
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR)
            day = d1 - d2 - maxDays
        } else {
            day = d1 - d2
        }
        return day
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    fun getOffsetHour(date1: Long, date2: Long): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = date1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = date2
        val h1 = calendar1.get(Calendar.HOUR_OF_DAY)
        val h2 = calendar2.get(Calendar.HOUR_OF_DAY)
        var h = 0
        val day = getOffsetDay(date1, date2)
        h = h1 - h2 + day * 24
        return h
    }

    fun getDiffTimeSeconds(date: Long): Long {
         return (date - Calendar.getInstance().timeInMillis) / 1000
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    fun getOffsetMinutes(date1: Long, date2: Long): Int {
        val calendar1 = Calendar.getInstance()
        calendar1.timeInMillis = date1
        val calendar2 = Calendar.getInstance()
        calendar2.timeInMillis = date2
        val m1 = calendar1.get(Calendar.MINUTE)
        val m2 = calendar2.get(Calendar.MINUTE)
        val h = getOffsetHour(date1, date2)
        var m = 0
        m = m1 - m2 + h * 60
        return m
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(compare("2012-12-01 12:12:12", "2012-12-01 12:12:11"))
        println(getDateYMD(currDateLong()))
        println(getDateYMDHMS(currDateLong()))
        println(getDateYMDHM(currDateLong()))
        println(getDateLong(Date()))
        println(getDateLong("2012-12-01 12:12:12"))
    }

    /**
     * 获得GMT时间字符串
     *
     * @param timemills 1970以来的毫秒数
     * @return
     */
    fun getGMTTimeString(timemills: Long): String {
        val date = Date(timemills)
        return date.toGMTString()
    }
}

