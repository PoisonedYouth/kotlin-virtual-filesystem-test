import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File
import java.util.*

object TempDirectoryExtensionV2 : BeforeEachCallback, AfterAllCallback {

    private lateinit var tempDirectory: File

    override fun beforeEach(context: ExtensionContext?) {
        tempDirectory = File(System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID().toString())
        tempDirectory.mkdir()
    }

    override fun afterAll(context: ExtensionContext?) {
        tempDirectory.delete()
    }

    fun tempDirectoryPath(): String = tempDirectory.absolutePath
}