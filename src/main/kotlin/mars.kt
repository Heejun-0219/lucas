import java.time.LocalDate

fun main() {
    // 사용자로부터 지구 날짜를 입력받습니다.
    print("지구날짜는? ")
    // val earthDate = read().trim()
    val earthDate = "2024-01-01"
    val earthDateParsed = LocalDate.parse(earthDate)
    val baseDate = LocalDate.of(1, 1, 1)

    // 기준일로부터의 경과 일수를 계산합니다.
    val daysFromBase = calculateDaysFromBase(baseDate, earthDateParsed) + 1

    // 진행 바를 표시합니다.
    showProgressBar()

    // 화성 날짜로 변환합니다.
    val marsDate = convertEarthToMars(daysFromBase)

    // 결과를 출력합니다.
    println("지구날은 ${printEarthDays(daysFromBase)} => ${marsDate.first} 화성년 ${marsDate.second}월 ${marsDate.third}일\n")

    // 화성 달력을 출력합니다.
    printMarsCalendar(marsDate.first, marsDate.second)
}

fun calculateDaysFromBase(
    baseDate: LocalDate,
    targetDate: LocalDate,
): Long {
    var daysCount = 0L
    var currentYear = baseDate.year
    val targetYear = targetDate.year

    // 연도별 일수 계산
    while (currentYear < targetYear) {
        daysCount += if (currentYear % 4 == 0) 366 else 365
        currentYear++
    }

    // 해당 연도에서 남은 일수 계산
    daysCount += targetDate.dayOfYear - baseDate.dayOfYear

    return daysCount
}

fun showProgressBar() {
    for (i in 1..9) {
        print("\r${"█".repeat(i)}${"▁".repeat(10 - i)} ${i * 10}%")
        // Thread.sleep(500)
    }
    println("\r${"█".repeat(10)} 화성까지 여행 100%")
}

fun convertEarthToMars(daysFromBase: Long): Triple<Int, Int, Int> {
    var remainingDays = daysFromBase
    var marsYear = 0

    // 화성년 계산
    while (remainingDays >= (if (marsYear % 2 == 0) 668 else 669)) {
        remainingDays -= if (marsYear % 2 == 0) 668 else 669
        marsYear++
    }
    // 화성월 계산
    val monthDays =
        listOf(
            28, // 1
            28, // 2
            28, // 3
            28, // 4
            28, // 5
            27, // 6
            28, // 7
            28, // 8
            28, // 9
            28, // 10
            28, // 11
            27, // 12
            28, // 13
            28, // 14
            28, // 15
            28, // 16
            28, // 17
            27, // 18
            28, // 19
            28, // 20
            28, // 21
            28, // 22
            28, // 23
            if (marsYear % 2 == 0) 28 else 27, // 24
        )
    var marsMonth = 1
    while (remainingDays >= monthDays[marsMonth]) {
        remainingDays -= monthDays[marsMonth]
        marsMonth++
    }

    val marsDay = remainingDays

    return Triple(marsYear, marsMonth, marsDay.toInt())
}

fun printMarsCalendar(
    year: Int,
    month: Int,
) {
    println("     ${year}년 ${month}월")
    println("Su Lu Ma Me Jo Ve Sa")

    val daysInMonth =
        when {
            month % 6 == 0 && month != 24 -> 27
            month == 24 && (year % 2 == 0) -> 28
            month == 24 && (year % 2 != 0) -> 27
            else -> 28
        }

    for (day in 1..daysInMonth) {
        print("%2d ".format(day))
        if (day % 7 == 0 || day == daysInMonth) println()
    }
}

fun printEarthDays(day: Long): String = "%,d".format(day)
