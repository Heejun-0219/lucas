import java.util.HashMap

// 전화번호에서 하이픈을 제거하는 함수
fun remove(from: String): String {
    val result = from.replace("-", "")
    println("remove: $from -> $result") // 디버깅 로그 추가
    return result
}

// 전화번호의 길이가 유효한지 확인하는 함수
fun isInvalidLength(tel: String): Boolean {
    return tel.length > 12 || tel.length < 8
}

// 전화번호가 '0'으로 시작하는지 확인하는 함수
fun isInvalidStart(tel: String): Boolean {
    return tel[0] != '0'
}

// 전화번호의 마지막 네 자리가 모두 같은 숫자인지 확인하는 함수
fun isRepeatedDigits(ext: String): Boolean {
    return ext.all { it == ext[0] }
}

// 서울 지역번호를 처리하는 함수
fun handleSeoulNumber(tel: String, ext: String): List<String> {
    println("handleSeoulNumber: tel=$tel, ext=$ext") // 디버깅 로그 추가
    return if (tel.length != 10 || isRepeatedDigits(ext)) {
        listOf("서울", "X")
    } else {
        listOf("서울", "O")
    }
}

// 휴대폰 번호를 처리하는 함수
fun handleMobileNumber(tel: String, top: String): List<String> {
    println("handleMobileNumber: tel=$tel, top=$top") // 디버깅 로그 추가
    val map = hashMapOf(
        "010" to "휴대폰", "011" to "휴대폰", "016" to "휴대폰", "017" to "휴대폰",
        "018" to "휴대폰", "019" to "휴대폰"
    )
    if (!map.containsKey(top)) return listOf("전국", "X")
    return if (tel[2] == '0' && tel.length == 11 && (tel[3].toString().toIntOrNull() ?: 1) % 2 == 0) {
        listOf("휴대폰", "O")
    } else {
        listOf("휴대폰", "X")
    }
}

// 국제 전화를 처리하는 함수
fun handleInternationalNumber(tel: String): List<String> {
    println("handleInternationalNumber: tel=$tel") // 디버깅 로그 추가
    return if (tel.length in 8..12) listOf("국제", "O") else listOf("전국", "X")
}

// 지역 번호를 처리하는 함수
fun handleRegionalNumber(tel: String, top: String): List<String> {
    println("handleRegionalNumber: tel=$tel, top=$top") // 디버깅 로그 추가
    val map = hashMapOf(
        "031" to "경기", "032" to "인천", "033" to "강원", "041" to "충청", "042" to "대전",
        "044" to "세종", "051" to "부산", "052" to "울산", "053" to "대구", "054" to "경북",
        "055" to "경남", "061" to "전남", "062" to "광주", "063" to "전북", "064" to "제주"
    )
    val region = map[top] ?: return listOf("전국", "X")
    return if (tel.length == 10 && tel[3] == '0') {
        listOf(region, "X")
    } else {
        listOf(region, "O")
    }
}

// 전화번호를 처리하는 메인 함수
fun solution(telno: String): List<String> {
    val tel = remove(telno)
    println("solution: tel=$tel") // 디버깅 로그 추가

    if (isInvalidLength(tel) || isInvalidStart(tel)) return listOf("전국", "X")

    val top = tel.substring(0, 3)
    val ext = tel.takeLast(4)

    return when (tel[1]) {
        '2' -> handleSeoulNumber(tel, ext)  // 서울 번호 처리
        '1' -> handleMobileNumber(tel, top) // 휴대폰 번호 처리
        '0' -> if (tel[2] == '1' || tel[2] == '2') handleInternationalNumber(tel) else listOf("전국", "X") // 국제 번호 처리
        else -> handleRegionalNumber(tel, top) // 지역 번호 처리
    }
}

fun main(args: Array<String>) {
    // size failure
    println(solution("010123456789"))
    println(solution("01012345678"))
    println(solution("0101234567"))
    println(solution("010123456"))
    // first digit failure
    println(solution("11012345678"))
    println(solution("21012345678"))
    println(solution("31012345678"))

    // normal test
    println(solution("010-123-1234"))
    println(solution("010-2234-1234"))
    println(solution("02-1234-1234"))
    println(solution("0212341111"))
    println(solution("0311237890"))
    println(solution("061-012-7890"))
    println(solution("015-0157899"))
    println(solution("042-2123-7890"))

    // extension failure like add character
    println(solution("010-123-123a"))
    println(solution("010-123-123"))
    println(solution("010-123-12"))
    println(solution("010-123-1"))
    println(solution("010-123-"))

    // 국제번호 001, 002 + 8 ~ 12자리
    println(solution("001-1234-1234"))
    println(solution("002-1234-1234"))
    println(solution("00112341234"))
    println(solution("00212341234"))
    println(solution("0011234123"))
    println(solution("0021234123"))
    println(solution("001123412"))
    println(solution("002123412"))
    println(solution("00112341"))
}
