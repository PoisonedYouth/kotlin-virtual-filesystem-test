import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path


object FileServiceV2 {

    fun createZipFile(files: List<Path>, zipFile: Path): Path {
        val path = zipFile.toUri()
        val uri = URI.create("jar:$path")

        val env = mutableMapOf<String, String>()
        env["create"] = "true"
        FileSystems.newFileSystem(uri, env).use { fs ->
            files.forEach {
                Files.copy(it, fs.getPath(it.fileName.toString()))
            }
        }
        return zipFile
    }
}
