import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

data class LogEntry(
    val level: String,
    val timeStamp: String,
    val process: String,
    val message: String,
) {
    override fun toString(): String = "[$level] $timeStamp / $process : $message"
}

fun parseLogFile(filePath: String): List<LogEntry> {
    val logEntries = mutableListOf<LogEntry>()

    val lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)

    lines.forEach {
        val logData = it.split("\t")

        if (logData.size < LogEntry::class.java.declaredFields.size) {
            return@forEach
        }
        val logEntry =
            LogEntry(
                level = logData[0],
                timeStamp = logData[1],
                process = logData[2],
                // Join the rest of the log data as message
                message = logData.subList(3, logData.size).joinToString(separator = " | "),
            )
        logEntries.add(logEntry)
    }
    return logEntries
}

fun filterByLogLevel(logEntries: List<LogEntry>): List<LogEntry> {
    println("Enter the log level to filter:")
    val logLevel = readln()

    return logEntries.filter { it.level.equals(logLevel, ignoreCase = true) }
}

fun filterByProcessName(logEntries: List<LogEntry>): List<LogEntry> {
    println("Enter the process name to filter:")
    val processName = readln()

    return logEntries.filter { it.process.equals(processName, ignoreCase = true) }
}

fun sortByTimeStamp(logEntries: List<LogEntry>): List<LogEntry> = logEntries.sortedBy { it.timeStamp }

fun sortByProcessName(logEntries: List<LogEntry>): List<LogEntry> = logEntries.sortedBy { it.process }

fun countByLogLevel(logEntries: List<LogEntry>): Map<String, Int> = logEntries.groupingBy { it.level }.eachCount().toSortedMap()

fun countByProcessName(logEntries: List<LogEntry>): Map<String, Int> = logEntries.groupingBy { it.process }.eachCount().toSortedMap()

fun printSelectPrompt() {
    println("Select an option:")
    println("1. Filter by log level")
    println("2. Filter by process name")
    println("3. Sort by timestamp")
    println("4. Sort by process name")
    println("5. Count by log level")
    println("6. Count by process name")
    println("7. Exit")
    print("Enter the choice: ")
}

fun main() {
    val filePath = "src/main/resources/1701410305471system.log"
    val logEntries = parseLogFile(filePath)

    val analyzer =
        mapOf(
            1 to ::filterByLogLevel,
            2 to ::filterByProcessName,
            3 to ::sortByTimeStamp,
            4 to ::sortByProcessName,
            5 to ::countByLogLevel,
            6 to ::countByProcessName,
        )

    while (true) {
        printSelectPrompt()

        val choice = readlnOrNull()?.toIntOrNull()

        if (choice == 7) {
            break
        } else if (choice == null || choice !in 1..6) {
            println("Invalid choice. Please enter a valid choice.")
            continue
        }

        val result = analyzer[choice]?.invoke(logEntries)

        if (result is List<*>) {
            result.forEach(::println)
        } else if (result is Map<*, *>) {
            result.forEach { (key, value) -> println("$key: $value") }
        }
    }
}
