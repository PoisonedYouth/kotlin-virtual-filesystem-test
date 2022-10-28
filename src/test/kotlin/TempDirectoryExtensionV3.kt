import com.google.common.jimfs.Jimfs
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path
import java.util.*


object TempDirectoryExtensionV3 : BeforeEachCallback {

    private val fileSystem: FileSystem = Jimfs.newFileSystem()
    private lateinit var tempDirectory: Path

    override fun beforeEach(context: ExtensionContext?) {
        tempDirectory = fileSystem.getPath(UUID.randomUUID().toString())
        Files.createDirectories(tempDirectory)
    }

    fun tempDirectoryPath(): Path = tempDirectory
}
