package global.msnthrp.feeed.utils

import android.content.Context
import java.io.*

class FileStorage(context: Context, name: String) {

    private val file = File(context.cacheDir, name)

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun writeToFile(data: String) {
        val writer = BufferedWriter(FileWriter(file))
        writer.write(data)
        writer.close()
    }

    fun readFromFile(): String {
        val br = BufferedReader(FileReader(file))
        val sb = StringBuilder()
        var str: String?
        do {
            str = br.readLine()
            if (str != null) sb.append(str)
        } while (str != null)
        return sb.toString()
    }

}