import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


object FileServiceV1 {

    fun createZipFile(files: List<File>, filename: String): File {
        val zipFile = File(filename)
        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { out ->
            for(file in files) {
                FileInputStream(file).use { fi ->
                    BufferedInputStream(fi).use { origin ->
                        val entry = ZipEntry(file.name)
                        out.putNextEntry(entry)
                        origin.copyTo(out, 1024)
                    }
                }
            }
        }
        return zipFile
    }
}