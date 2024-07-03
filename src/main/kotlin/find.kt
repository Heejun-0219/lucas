data class Game(
    val name: String,
    val discontinued: Boolean,
    val genre: String,
    val rating: Double,
    val maxParticipants: Int,
    val startDate: String,
    val endDate: String
)

val games = listOf(
    Game("Kong", true, "Adventure", 4.1, 1, "197001", "198104"),
    Game("Ace", false, "Board", 3.8, 4, "198707", "202407"),
    Game("Mario", true, "RPG", 3.3, 2, "200109", "200711"),
    Game("Prince", true, "RPG", 4.8, 1, "198303", "200205"),
    Game("Dragons", true, "Fight", 3.4, 4, "199005", "199512"),
    Game("Civil", false, "Simulation", 4.2, 1, "200206", "202407"),
    Game("Teken", true, "Fight", 4.0, 2, "199807", "200912"),
    Game("GoCart", false, "Sports", 4.6, 8, "200612", "202407"),
    Game("Football", false, "Sports", 2.9, 8, "199406", "202407"),
    Game("Brave", true, "RPG", 4.2, 1, "198006", "198501")
)

fun find(param0: String, param1: Int): String {
    val filteredGames = games.filter { game ->
        param0 >= game.startDate && param0 <= game.endDate && param1 <= game.maxParticipants
    }.sortedByDescending { it.rating }

    val result = filteredGames.joinToString(", ") { game ->
        val name = if (game.discontinued) "${game.name}*" else game.name
        "$name(${game.genre}) ${game.rating}"
    }

    return result
}

fun main() {
    // 기존 테스트 케이스
    println(find("198402", 1)) // Prince*(RPG) 4.8, Brave*(RPG) 4.2
    println(find("200008", 8)) // Football(Sports)
    println(find("199004", 5)) // ""

    // 추가 테스트 케이스
    println(find("202001", 1)) // GoCart(Sports) 4.6, Civil(Simulation) 4.2, Ace(Board) 3.8, Football(Sports) 2.9
    println(find("199201", 4)) // Ace(Board) 3.8, Dragons*(Fight) 3.4
    println(find("199809", 2)) // Teken*(Fight) 4.0, Ace(Board) 3.8, Football(Sports) 2.9
    println(find("200503", 1)) // Civil(Simulation) 4.2, Teken*(Fight) 4.0, Ace(Board) 3.8, Mario*(RPG) 3.3, Football(Sports) 2.9
    println(find("201001", 8)) // GoCart(Sports) 4.6, Football(Sports) 2.9
    println(find("198501", 1)) // Prince*(RPG) 4.8, Brave*(RPG) 4.2
    println(find("199806", 4)) // Ace(Board) 3.8, Football(Sports) 2.9
}
