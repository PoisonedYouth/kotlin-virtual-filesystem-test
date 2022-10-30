import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.should
import kotlin.io.path.name
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

internal class FileServiceV2TestV1 {

    @RegisterExtension
    private val tempDirectoryExtension = TempDirectoryExtensionV3()

    @Test
    fun `createZipFile returns empty zip files for no content specified`() {
        // given
        val files = emptyList<Path>()
        val zipFile = tempDirectoryExtension.tempDirectoryPath().resolve("test.zip")

        // when
        val actual = FileServiceV2.createZipFile(files, zipFile)

        // then
        actual should Files::exists
    }

    @Test
    fun `createZipFile returns zip with single file`() {
        // given
        val file = tempDirectoryExtension.tempDirectoryPath().resolve("file1.txt")
        Files.writeString(file, "Hello World!")
        val zipFile = tempDirectoryExtension.tempDirectoryPath().resolve("test.zip")

        // when
        val actual = FileServiceV2.createZipFile(listOf(file), zipFile)

        // then
        actual should Files::exists
        listFilesInZipFile(actual) shouldContain "file1.txt"
    }

    @Test
    fun `createZipFile returns zip with multiple files`() {
        // given
        val file1 = tempDirectoryExtension.tempDirectoryPath().resolve("file1.txt")
        Files.writeString(file1, "Hello World!")
        val file2 = tempDirectoryExtension.tempDirectoryPath().resolve("file2.txt")
        Files.writeString(file2, "Hello New World!")
        val zipFile = tempDirectoryExtension.tempDirectoryPath().resolve("test.zip")

        // when
        val actual = FileServiceV2.createZipFile(listOf(file1, file2), zipFile)

        // then
        actual should Files::exists
        listFilesInZipFile(actual) shouldContainOnly listOf("file1.txt", "file2.txt")
    }
}

fun listFilesInZipFile(zipFile: Path): List<String> {
    val uri = URI.create("jar:${zipFile.toUri()}")
    val env = mutableMapOf<String, String>()
    env["create"] = "false"
    val files = mutableListOf<String>()
    FileSystems.newFileSystem(uri, env).use { fs ->
        Files.newDirectoryStream(fs.getPath("/")).forEach {
            files.add(it.name)
        }
    }
    return files
}
