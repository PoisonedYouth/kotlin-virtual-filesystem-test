import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.io.File


object TempDirectoryExtension : AfterEachCallback, AfterAllCallback {


    private val tempDirectory = File("test")

    init {
        tempDirectory.mkdir()
    }

    override fun afterEach(context: ExtensionContext?) {
        tempDirectory.listFiles()?.forEach { it.delete() }
    }

    fun tempDirectoryPath(): String = tempDirectory.path

    override fun afterAll(context: ExtensionContext?) {
        tempDirectory.delete()
    }
}
