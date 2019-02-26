package global.msnthrp.feeed.utils

import okhttp3.ResponseBody
import java.io.*


fun writeResponseBody(file: File, body: ResponseBody): Boolean {
    try {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)
            inputStream = body.byteStream()
            outputStream = FileOutputStream(file)

            while (true) {
                val read = inputStream.read(fileReader)
                if (read == -1) break

                outputStream.write(fileReader, 0, read)
            }
            outputStream.flush()
            return true
        } catch (e: IOException) {
            return false
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    } catch (e: IOException) {
        return false
    }

}