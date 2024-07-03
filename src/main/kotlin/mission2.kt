fun play(turns: Int, movesList: List<String>): Map<String, Int> {
    val board = Array<String?>(15) { null }
    val players = listOf("A", "B", "C", "D")
    val positions = mutableMapOf<String, Int>()
    val ownership = mutableMapOf<String, Int>()

    players.forEach {
        positions[it] = -1
        ownership[it] = 0
    }

    for (i in 0 until turns) {
        val moves = movesList[i].split(",").map { it.trim().toInt() }

        for (j in moves.indices) {
            val move = moves[j]
            if (move !in 1..4) {
                println("Invalid move: $move. Moves should be between 1 and 4.")
                continue
            }

            val player = players[j]
            positions[player]?.let {
                val newPosition = (it + move) % 15
                positions[player] = newPosition

                if (board[newPosition] == null) {
                    board[newPosition] = player
                    ownership[player] = (ownership[player] ?: 0) + 1
                }

                println("Player $player moved to position $newPosition.")
                println("Board: ${board.joinToString(", ") { it ?: "-" }}")
                println("Ownership: $ownership")

                if (board.all { it != null }) return ownership
            }
        }
    }
    return ownership
}

fun main() {
    val example1Turns = 8
    val example1Moves = listOf(
        "1,2,3,4",
        "3,1,2,4",
        "2,3,4,1",
        "1,3,2,4",
        "3,2,1,1",
        "3,2,1,1",
        "1,3,2,4",
        "4,4,4,4"
    )
    val example1Result = play(example1Turns, example1Moves)
    println("예제 1 정답: $example1Result") // 예상 출력: {A=4, B=1, C=5, D=5}

    val example2Turns = 4
    val example2Moves = listOf(
        "1,2,3,4",
        "1,1,1,2",
        "1,1,2,1",
        "1,1,1,2"
    )
    val example2Result = play(example2Turns, example2Moves)
    println("예제 2 정답: $example2Result") // 예상 출력: {A=1, B=2, C=1, D=4}

    val example3Turns = 4
    val example3Moves = listOf(
        "1,2,3,4",
        "1,1,1,1",
        "2,2,2,2",
        "3,3,3,3",
        "1,1,1,1"
    )
    val example3Result = play(example3Turns, example3Moves)
    println("예제 3 정답: $example3Result") // 예상 출력: {A=1, B=2, C=3, D=4}

    val example4Turns = 6
    val example4Moves = listOf(
        "1,2,3,4",
        "4,4,4,4",
        "4,4,4,4",
        "4,4,4,4",
        "2,3,1,3",
        "1,3,2,4"
    )
    val example4Result = play(example4Turns, example4Moves)
    println("예제 4 정답: $example4Result") // 예상 출력: {A=4, B=4, C=4, D=3}

    val example5Turns = 8
    val example5Moves = listOf(
        "1,1,1,3",
        "1,1,1,1",
        "1,1,1,4",
        "1,1,1,1",
        "2,2,1,3",
        "4,4,4,2",
        "1,1,1,2",
        "1,1,1,1"
    )
    val example5Result = play(example5Turns, example5Moves)
    println("예제 5 정답: $example5Result") // 예상 출력: {A=5, B=0, C=1, D=6}
}
