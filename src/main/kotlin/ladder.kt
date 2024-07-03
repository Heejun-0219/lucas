import kotlin.random.Random

const val WIDTH = 4
const val HEIGHT = 5

// 사다리 발판의 종류를 나타내는 열거형
enum class FootStep {
    EMPTY,       // 빈 발판
    HORIZONTAL,  // 수평 발판
    LEFT,        // 좌하향 발판
    RIGHT;       // 우하향 발판

    // 발판의 출력 형식을 반환하는 함수
    fun symbol() = when (this) {
        EMPTY -> "   "
        HORIZONTAL -> "---"
        LEFT -> "/-/"
        RIGHT -> "\\-\\"
    }
}

class LadderGame {
    private val ladder = Array(HEIGHT) { Array(WIDTH) { FootStep.EMPTY } } // 사다리 데이터 구조

    // 사다리 데이터를 초기화하는 함수
    fun reset() {
        ladder.forEach { it.fill(FootStep.EMPTY) }
    }

    // 사다리 데이터에 랜덤하게 발판을 채우는 함수
    fun randomFill() {
        val footsteps = FootStep.entries.filter { it != FootStep.EMPTY }
        val totalSteps = Random.nextInt(HEIGHT * (WIDTH - 1)) // 총 발판 수를 랜덤하게 결정

        repeat(totalSteps) {
            val y = Random.nextInt(HEIGHT)
            val x = Random.nextInt(WIDTH - 1) // 발판이 사다리 범위를 벗어나지 않도록 설정
            val step = footsteps.random()

            ladder[y][x] = step
        }
    }

    // 사다리 데이터를 출력하는 함수
    fun display() {
        ladder.forEach { row ->
            row.forEach { print("|${it.symbol()}") }
            println("|")
        }
    }

    // 사다리 데이터의 유효성을 검사하는 함수
    fun analyze(): Boolean {
        for (y in 0 until HEIGHT) {
            for (x in 0 until WIDTH - 1) {
                val current = ladder[y][x]
                val next = ladder[y][x + 1]

                // 연속된 수평 발판이나 상충되는 좌우 발판 조합을 검사
                if (current == FootStep.HORIZONTAL && next == FootStep.HORIZONTAL) return false
                if (current == FootStep.RIGHT && next == FootStep.LEFT) return false
                if (current == FootStep.LEFT && next == FootStep.RIGHT) return false
            }
        }
        return true
    }
}

fun main() {
    val ladderGame = LadderGame()

    ladderGame.reset() // 사다리 데이터 초기화
    ladderGame.randomFill() // 랜덤하게 사다리 발판 생성

    val isValid = ladderGame.analyze() // 사다리 데이터 유효성 검사
    println("Ladder game is ${if (isValid) "valid" else "invalid"}") // 검사 결과 출력

    ladderGame.display() // 사다리 데이터 출력
}
