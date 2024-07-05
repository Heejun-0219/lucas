import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class City(
    val seq: Int,
    val city: String,
    val year: Int,
    val lat: Double,
    val lng: Double,
    val pop: Long,
)

val cities =
    listOf(
        City(0, "서울", 1946, 37.5665, 126.9780, 9720846),
        City(1, "부산", 1963, 35.1796, 129.0756, 3413841),
        City(2, "인천", 1981, 37.4563, 126.7052, 2938420),
        City(3, "대구", 1981, 35.8714, 128.6014, 2414220),
        City(4, "대전", 1995, 36.3504, 127.3845, 1475221),
        City(5, "광주", 1986, 35.1595, 126.8526, 1454677),
        City(6, "울산", 1997, 35.5384, 129.3114, 1159673),
        City(7, "세종", 2012, 36.4875, 127.2816, 362259),
        City(8, "수원", 1949, 37.2636, 127.0286, 1240374),
        City(9, "창원", 2010, 35.2286, 128.6811, 1046188),
        City(10, "포항", 1949, 36.0190, 129.3435, 511807),
        City(11, "전주", 1949, 35.8242, 127.1480, 658346),
        City(12, "청주", 1949, 36.6424, 127.4890, 847110),
        City(13, "제주", 1955, 33.4996, 126.5312, 486306),
        City(14, "고양", 1992, 37.6564, 126.8350, 1075500),
        City(15, "용인", 1996, 37.2411, 127.1776, 1081914),
        City(16, "천안", 1995, 36.8151, 127.1139, 666417),
        City(17, "김해", 1995, 35.2342, 128.8811, 559648),
        City(18, "평택", 1986, 36.9921, 127.1122, 519075),
        City(19, "마산", 1949, 35.2138, 128.5833, 424192),
        City(20, "군산", 1949, 35.9672, 126.7364, 266569),
        City(21, "원주", 1955, 37.3422, 127.9202, 364738),
        City(22, "의정부", 1963, 37.7389, 127.0455, 442782),
        City(23, "김포", 1998, 37.6236, 126.7145, 442453),
        City(24, "광명", 1981, 37.4772, 126.8664, 345262),
        City(25, "춘천", 1995, 37.8813, 127.7298, 285584),
        City(26, "안산", 1995, 36.7898, 127.0049, 321355),
        City(27, "성남", 1973, 37.4200, 127.1265, 944626),
        City(28, "구미", 1978, 36.1195, 128.3446, 402607),
        City(29, "시흥", 1989, 37.3803, 126.8031, 446420),
        City(30, "목포", 1949, 34.8118, 126.3922, 238718),
        City(31, "익산", 1947, 35.9483, 126.9577, 292524),
        City(32, "경주", 1955, 35.8562, 129.2247, 257041),
        City(33, "의왕", 1986, 37.3446, 126.9688, 157346),
        City(34, "부천", 1973, 37.4989, 126.7831, 843794),
        City(35, "남양주", 1995, 37.6367, 127.2143, 736287),
        City(36, "파주", 1997, 37.7598, 126.7805, 453589),
        City(37, "거제", 1989, 34.8806, 128.6216, 241253),
        City(38, "화성", 2001, 37.1997, 126.8310, 791057),
        City(39, "강릉", 1995, 37.7519, 128.8761, 213658),
    )

fun distance(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double,
): Double = sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))

fun kMeans(
    k: Int,
    xSelector: (City) -> Double,
    ySelector: (City) -> Double,
): Map<Pair<Double, Double>, List<City>> {
    val random = Random(1)
    var centers = (1..k).map { Pair(xSelector(cities[random.nextInt(cities.size)]), ySelector(cities[random.nextInt(cities.size)])) }

    var prevCenters: List<Pair<Double, Double>>? = null

    while (prevCenters != centers) {
        val clusters =
            cities.groupBy { city ->
                centers.minByOrNull { center -> distance(center.first, center.second, xSelector(city), ySelector(city)) }!!
            }

        prevCenters = centers
        centers =
            clusters.map { (_, group) ->
                Pair(
                    group.map(xSelector).average(),
                    group.map(ySelector).average(),
                )
            }
    }

    return cities.groupBy { city ->
        centers.minByOrNull { center -> distance(center.first, center.second, xSelector(city), ySelector(city)) }!!
    }
}

fun kmeans_long(k: Int): Map<Pair<Double, Double>, List<City>> = kMeans(k, { it.year.toDouble() }, { it.lng })

fun main() {
    print("Enter the number of clusters: ")
    val k = readln().toInt()

    val longClusters = kmeans_long(k)
    println("kmeans_long results:")
    var i = 0
    longClusters.forEach { (center, clusterCities) ->
        println("${i++}------------------------------------")
        println("Center: $center")
        println("Cities: ${clusterCities.joinToString(", ") { it.city }}")
    }
}
