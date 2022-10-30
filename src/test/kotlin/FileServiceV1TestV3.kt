import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.file.exist
import io.kotest.matchers.should
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

internal class FileServiceV1TestV3 {

    @TempDir
    private lateinit var tempDirectory: File

    @Test
    fun `createZipFile returns empty zip files for no content specified`() {
        // given
        val files = emptyList<File>()
        val filename = File(tempDirectory.path +  "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(files, filename.path)

        // then
        actual should exist()
    }

    @Test
    fun `createZipFile returns zip with single file`() {
        // given
        val file = File(tempDirectory.path + "/file1.txt")
        file.writeText("Hello World!")
        val filename = File(tempDirectory.path + "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(listOf(file), filename.path)

        // then
        actual should exist()
        listFilesInZipFile(actual.path) shouldContain "file1.txt"
    }

    @Test
    fun `createZipFile returns zip with multiple files`() {
        // given
        val file1 = File(tempDirectory.path + "/file1.txt")
        file1.writeText("Hello World!")
        val file2 = File(tempDirectory.path + "/file2.txt")
        file2.writeText("Hello New World!")
        val filename = File(tempDirectory.path + "/test.zip")

        // when
        val actual = FileServiceV1.createZipFile(listOf(file1, file2), filename.path)

        // then
        actual should exist()
        listFilesInZipFile(actual.path) shouldContainOnly listOf("file1.txt", "file2.txt")
    }
}