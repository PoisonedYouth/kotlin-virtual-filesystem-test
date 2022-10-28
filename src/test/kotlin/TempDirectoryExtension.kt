import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File

class TempDirectoryExtension: AfterEachCallback {

    val tempDirectory = File("test")

    init {
        tempDirectory.mkdir()
    }

    override fun afterEach(context: ExtensionContext?) {
        tempDirectory.listFiles()?.forEach { it.delete() }
    }

    fun tempDirectoryPath(): String = tempDirectory.path
}