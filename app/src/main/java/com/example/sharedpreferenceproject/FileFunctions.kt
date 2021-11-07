package com.example.sharedpreferenceproject

import android.content.Context
import java.io.*

object FileFunctions {
    private fun getFileValue(fileName: String?, context: Context?): String? {
        return try {
            val outStringBuf = StringBuffer()
            var inputLine: String? = ""
            /*
                 * We have to use the openFileInput()-method the ActivityContext
                 * provides. Again for security reasons with openFileInput(...)
                 */
            val fIn: FileInputStream? = context?.openFileInput(fileName)
            val isr = InputStreamReader(fIn)
            val inBuff = BufferedReader(isr)
            while (inBuff.readLine().also {value->
                    inputLine = value
                } != null) {
                outStringBuf.append(inputLine)
            }
            inBuff.close()
            outStringBuf.toString()
        } catch (e: IOException) {
            null
        }
    }

    private fun appendFileValue(
        fileName: String?, value: String?,
        context: Context?
    ): Boolean {
        return writeToFile(fileName, value, context, Context.MODE_APPEND)
    }

    private fun setFileValue(
        fileName: String?, value: String?,
        context: Context?
    ): Boolean {
        return writeToFile(
            fileName, value, context,
            Context.MODE_PRIVATE
        )
    }

    private fun writeToFile(
        fileName: String?, value: String?,
        context: Context?, writeOrAppendMode: Int
    ): Boolean { // just make sure it's one of the modes we support
//        if (writeOrAppendMode != Context.MODE_PRIVATE  && writeOrAppendMode != Context.MODE_APPEND
//        ) {
//            return false
//        }
        try { /*
             * We have to use the openFileOutput()-method the ActivityContext
             * provides, to protect your file from others and This is done for
             * security-reasons. We chose MODE_WORLD_READABLE, because we have
             * nothing to hide in our file
             */
            val fOut: FileOutputStream? = context?.openFileOutput(
                fileName,
                writeOrAppendMode
            )
            val osw = OutputStreamWriter(fOut)

            // Write the string to the file
            if (writeOrAppendMode == Context.MODE_APPEND)
                osw.append(",")
            osw.write(value)


            // save and close
            osw.flush()
            osw.close()
        } catch (e: IOException) {
            return false
        }
        return true
    }

    fun deleteFile(fileName: String?, context: Context?) {
        context?.deleteFile(fileName)
    }


    fun getFile(fileName: String?, context: Context?): File? {
        try {
            return context?.getFileStreamPath(fileName)
        } catch (e: IOException) {

        }
        return null
    }

    fun saveToLog(logJsonString: String, context: Context?) {
        if (getFileValue(FileConstant.LOG_FILE_NAME, context) == null) {
            setFileValue(FileConstant.LOG_FILE_NAME, logJsonString, context)
        } else {
            appendFileValue(FileConstant.LOG_FILE_NAME, logJsonString, context)
        }
    }
}