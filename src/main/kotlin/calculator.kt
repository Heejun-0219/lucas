import kotlin.reflect.KMutableProperty0

class StackCalculator {
    private val stack = mutableListOf<Int>()
    private var registerA: Int? = null
    private var registerB: Int? = null
    private val output = mutableListOf<String>()

    // 명령어 처리 함수
    fun execute(commands: Array<String>): List<String> {
        reset()
        for (command in commands) {
            when (command) {
                "POPA" -> popRegister(::registerA)
                "POPB" -> popRegister(::registerB)
                "ADD" -> performOperation(Int::plus)
                "SUB" -> performOperation(Int::minus)
                "PUSH0" -> push(0)
                "PUSH1" -> push(1)
                "PUSH2" -> push(2)
                "SWAP" -> swap()
                "PRINT" -> print()
                else -> output.add("UNKNOWN")
            }
        }
        return output
    }

    // 초기화 함수
    private fun reset() {
        stack.clear()
        registerA = null
        registerB = null
        output.clear()
    }

    // 스택에서 값을 꺼내 레지스터에 할당하는 함수
    private fun popRegister(register: KMutableProperty0<Int?>) {
        if (isStackEmpty()) return
        register.set(stack.removeAt(stack.size - 1))
    }

    // 연산을 수행하고 스택에 결과를 저장하는 함수
    private fun performOperation(operation: (Int, Int) -> Int) {
        if (areRegistersEmpty()) return
        push(operation(registerA!!, registerB!!))
    }

    // 스택에 값을 추가하는 함수
    private fun push(value: Int) {
        if (isStackFull()) return
        stack.add(value)
    }

    // 레지스터 A와 B의 값을 교환하는 함수
    private fun swap() {
        if (areRegistersEmpty()) return
        val temp = registerA
        registerA = registerB
        registerB = temp
    }

    // 스택의 마지막 값을 출력하는 함수
    private fun print() {
        if (isStackEmpty()) return
        output.add(stack.removeAt(stack.size - 1).toString())
    }

    // 스택이 비어있는지 확인하는 함수
    private fun isStackEmpty(): Boolean =
        if (stack.isEmpty()) {
            output.add("EMPTY")
            true
        } else {
            false
        }

    // 스택이 가득 찼는지 확인하는 함수
    private fun isStackFull(): Boolean =
        if (stack.size >= 8) {
            output.add("OVERFLOW")
            true
        } else {
            false
        }

    // 레지스터가 비어있는지 확인하는 함수
    private fun areRegistersEmpty(): Boolean =
        if (registerA == null || registerB == null) {
            output.add("ERROR")
            true
        } else {
            false
        }
}

// 테스트 예시
fun main() {
    val calculator = StackCalculator()
    println(calculator.execute(arrayOf("PRINT", "PUSH0", "PRINT", "POPA"))) // ["EMPTY", "0", "EMPTY"]
    println(calculator.execute(arrayOf("PUSH1", "PUSH1", "PUSH2", "POPA", "POPB", "SWAP", "ADD", "PRINT", "PRINT"))) // ["3", "1"]
    println(calculator.execute(arrayOf("PUSH2", "PUSH2", "PUSH1", "POPA", "POPB", "SWAP", "SUB", "POPA", "POPB", "ADD", "PRINT"))) // ["3"]
    println(calculator.execute(arrayOf("ADD", "PUSH2", "PUSH1", "PUSH0", "PUSH2", "PUSH1", "PUSH2", "PUSH2", "PUSH0", "PUSH2", "PUSH3"))) // ["ERROR", "OVERFLOW", "UNKNOWN"]
}
