package co.yap.yapcore.helpers

import android.content.Context
import com.liveperson.infra.Infra
import java.io.File


object FileUtils {

    fun deleteFile(context: Context, file: File) {
        file.delete()
        if (file.exists()) {
            file.canonicalFile.delete()
            if (file.exists()) {
                context.deleteFile(file.name)
            }
        }

    }

     fun deleteRecursive(fileOrDirectory: File) {
        if (fileOrDirectory.isDirectory) {
            val var1 = fileOrDirectory.listFiles()
            val var2 = var1.size
            for (var3 in 0 until var2) {
                val child = var1[var3]
                deleteRecursive(child)
            }
        }
        fileOrDirectory.delete()
    }

}