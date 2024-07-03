class Package(val name: String) {
    val files = mutableListOf<File>()
}

class File(val name: String) {
    var type: Type? = null
}

class Type(val name: String) {
    val attrs = mutableListOf<Attr>()
}

class Attr(val name: String, val type: String = "nothing")

fun parseGraphviz(inputPackages: List<Package>): String {
    var result = ""
    result += "digraph G {\n"
    var relation = ""
    var clusterIndex = 0

    for (pack in inputPackages) {
        result += "subgraph cluster_$clusterIndex {\n" + // 패키지 추가
                "style=filled;\n" + "color=blue;\n" +
                "label=" + """ "${pack.name} Package" """ + ";\n"

        clusterIndex++
        for (file in pack.files) { // 파일 추가
            result += "subgraph cluster_${clusterIndex} {\n"
            result += "label=" + """ "${file.name}.file" """ + ";\n" + "color=lightgrey;\n"
            result += "${file.type?.name} [shape=box style=filled color=cyan]\n"

            for (attr in file.type?.attrs ?: mutableListOf()) { // 속성 추가
                result += "${attr.name};\n"
                if (attr.type != "nothing") {
                    relation += "${attr.name} -> ${attr.type};\n"
                }
            }
            result += "}\n"
            clusterIndex++
        }
        result += "}\n"
    }

    result += relation
    result += "}\n"
    return result
}

fun main() {
    // 예시 데이터 생성
    val productType = Type("Product").apply {
        attrs.add(Attr("sku", "String"))
        attrs.add(Attr("price", "Int"))
        attrs.add(Attr("title", "String"))
    }

    val orderType = Type("Order").apply {
        attrs.add(Attr("orderId", "String"))
        attrs.add(Attr("product", "Product"))
    }

    val sourceType = Type("Source").apply {
        attrs.add(Attr("Int"))
        attrs.add(Attr("String"))
    }

    val productFile = File("Product").apply {
        type = productType
    }

    val orderFile = File("Order").apply {
        type = orderType
    }

    val sourceFile = File("Source").apply {
        type = sourceType
    }

    val servicePackage = Package("Service").apply {
        files.add(productFile)
        files.add(orderFile)
    }

    val systemPackage = Package("System").apply {
        files.add(sourceFile)
    }

    val packages = listOf(servicePackage, systemPackage)

    // Graphviz DOT 문자열 생성
    val dotString = parseGraphviz(packages)
    println(dotString)
}
