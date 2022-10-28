import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.file.exist
import io.kotest.matchers.should
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipInputStream

internal class FileServiceV1Test {

    @RegisterExtension
    private val tempDirectoryExtension = TempDirectoryExtension()

    @Test
    fun `createZipFile returns empty zip files for no content specified`() {
        // given
        val files = emptyList<File>()
        val filename = File(tempDirectoryExtension.tempDirectoryPath() + "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(files, filename.path)

        // then
        actual should exist()
    }

    @Test
    fun `createZipFile returns zip with single file`() {
        // given
        val file = File(tempDirectoryExtension.tempDirectoryPath() + "/file1.txt")
        file.writeText("Hello World!")
        val filename = File(tempDirectoryExtension.tempDirectoryPath() + "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(listOf(file), filename.path)

        // then
        actual should exist()
        listFilesInZipFile(actual.path) shouldContain "file1.txt"
    }

    @Test
    fun `createZipFile returns zip with multiple files`() {
        // given
        val file1 = File(tempDirectoryExtension.tempDirectoryPath() + "/file1.txt")
        file1.writeText("Hello World!")
        val file2 = File(tempDirectoryExtension.tempDirectoryPath() + "/file2.txt")
        file2.writeText("Hello New World!")
        val filename = File(tempDirectoryExtension.tempDirectoryPath() + "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(listOf(file1, file2), filename.path)

        // then
        actual should exist()
        listFilesInZipFile(actual.path) shouldContainOnly listOf("file1.txt", "file2.txt")
    }
}

fun listFilesInZipFile(filename: String): List<String> {
    val files = mutableListOf<String>()
    val zis = ZipInputStream(FileInputStream(File(filename)))
    var zipEntry = zis.nextEntry
    while (zipEntry != null) {
        files.add(zipEntry.name)
        zis.closeEntry()
        zipEntry = zis.nextEntry
    }
    zis.close()
    return files.toList()
}