package ru.mareanexx.common.utils

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun uriToFile(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File.createTempFile("avatar_", ".jpg", context.cacheDir)
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return file
    }
}