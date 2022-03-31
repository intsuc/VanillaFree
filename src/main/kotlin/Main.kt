import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

val PACK_0 = """{"pack":{"description":"","pack_format":10}}""".toByteArray()
val PACK_1 = """{"pack":{"description":"","pack_format":10},"filter":{"block":[{"namespace":"^minecraft$","path":"^(?:advancements|loot_tables|recipes)/"}]}}""".toByteArray()
val ADVANCEMENT = """{"criteria":{"":{"trigger":"impossible"}}}""".toByteArray()
val LOOT_TABLE = """{}""".toByteArray()
val RECIPE = """{"type":"crafting_special_armordye"}""".toByteArray()
val TAG = """{"replace":true,"values":[]}""".toByteArray()

fun main() {
    val license = File("LICENSE").readBytes()
    val generated = Paths.get("generated")

    ZipOutputStream(File("VanillaFree0.zip").outputStream().buffered()).use { output ->
        output.writeFile("LICENSE", license)
        output.writeFile("pack.mcmeta", PACK_0)

        Files.walk(generated.resolve("data"))
            .filter { !Files.isDirectory(it) }
            .map { generated.relativize(it) }
            .map { path -> path.map { it.toString() } }
            .forEach { components ->
                val name = components.joinToString("/")
                val content = when (val type = components[2]) {
                    "advancements" -> ADVANCEMENT
                    "loot_tables" -> LOOT_TABLE
                    "recipes" -> RECIPE
                    "tags" -> TAG
                    else -> throw IllegalStateException("unknown type: '$type'")
                }
                output.writeFile(name, content)
            }
    }

    ZipOutputStream(File("VanillaFree1.zip").outputStream().buffered()).use { output ->
        output.writeFile("LICENSE", license)
        output.writeFile("pack.mcmeta", PACK_1)
    }
}

fun ZipOutputStream.writeFile(name: String, content: ByteArray) {
    putNextEntry(ZipEntry(name))
    write(content)
    closeEntry()
}
