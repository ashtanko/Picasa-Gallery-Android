/*
 * Copyright 2017 Alexey Shtanko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.shtanko.picasagallery.core.log

import android.util.Log.d
import android.util.Log.e
import io.shtanko.picasagallery.Config
import io.shtanko.picasagallery.Config.APPLICATION_LOG_TAG
import io.shtanko.picasagallery.Config.LOGS_PATH
import io.shtanko.picasagallery.Config.LOG_FILE_FORMAT_NAME
import io.shtanko.picasagallery.Config.LOG_QUEUE_NAME
import io.shtanko.picasagallery.Config.NET_LOG_FILENAME
import io.shtanko.picasagallery.PicasaApplication
import io.shtanko.picasagallery.core.DispatchQueue
import io.shtanko.picasagallery.core.log.LogType.DEBUG
import io.shtanko.picasagallery.core.log.LogType.ERROR
import io.shtanko.picasagallery.vendors.utils.time.FastDateFormat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.Locale

/**
 * {@link io.shtanko.picasagallery.core.log.Loggable} implementation.
 */
object FileLog : Loggable {


  private var streamWriter: OutputStreamWriter? = null
  private val dateFormat: FastDateFormat = FastDateFormat.getInstance(Config.LOGS_DATE_FORMAT,
      Locale.US)

  private var logQueue: DispatchQueue? = null
  private var currentFile: File? = null
  private var networkFile: File? = null
  @Volatile private var Instance: FileLog? = null

  private fun getInstance(): FileLog? {
    var localInstance: FileLog? = Instance
    if (localInstance == null) {
      synchronized(FileLog::class.java) {
        localInstance = Instance
        if (localInstance == null) {
          localInstance = FileLog
          Instance = localInstance
        }
      }
    }
    return localInstance
  }

  init {
    try {
      val sdCard = PicasaApplication.app.applicationContext.getExternalFilesDir(null)
      val dir = File(sdCard.absolutePath + LOGS_PATH)
      dir.mkdirs()
      currentFile = File(dir, dateFormat.format(System.currentTimeMillis()) + LOG_FILE_FORMAT_NAME)
    } catch (e: Exception) {
      e.printStackTrace()
    }

    try {
      logQueue = DispatchQueue(LOG_QUEUE_NAME)
      currentFile?.createNewFile()
      val stream = FileOutputStream(currentFile)
      streamWriter = OutputStreamWriter(stream)
      streamWriter?.write(
          "-----start log " + dateFormat.format(System.currentTimeMillis()) + "-----\n")
      streamWriter?.flush()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  override fun getLogFile(): String {
    try {
      val sdCard = PicasaApplication.app.applicationContext.getExternalFilesDir(null) ?: return ""
      val dir = File(sdCard.absolutePath + LOGS_PATH)
      dir.mkdirs()
      getInstance()?.networkFile = File(dir,
          getInstance()?.dateFormat?.format(System.currentTimeMillis()) + NET_LOG_FILENAME)
      return getInstance()?.networkFile?.absolutePath.toString()
    } catch (e: Exception) {
      e(e)
    }
    return ""
  }

  override fun e(message: String, t: Throwable) {
    e(APPLICATION_LOG_TAG, message, t)
    getInstance()?.logQueue?.postRunnable(Runnable {
      try {
        writeToError(message)
        getInstance()?.streamWriter?.write(t.toString())
        getInstance()?.streamWriter?.flush()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    })
  }

  override fun e(message: String) {
    processLog(ERROR, message)
  }

  override fun e(message: Throwable) {
    message.printStackTrace()
    if (getInstance()?.streamWriter != null) getInstance()?.logQueue?.postRunnable(Runnable {
      try {
        writeToError(message)
        val stack = message.stackTrace
        stack.indices.forEach { a -> writeToError(stack[a]) }
        getInstance()?.streamWriter?.flush()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }) else message.printStackTrace()
  }

  override fun d(message: String) {
    processLog(DEBUG, message)
  }

  override fun cleanupLogs() {
    val sdCard = PicasaApplication.app.applicationContext.getExternalFilesDir(null)
    val dir = File(sdCard.absolutePath + LOGS_PATH)
    val files = dir.listFiles()
    files
        ?.indices
        ?.asSequence()
        ?.map { files[it] }
        ?.filter {
          !(getInstance()
              ?.currentFile != null && it.absolutePath == getInstance()
              ?.currentFile?.absolutePath) && !(getInstance()
              ?.networkFile != null && it.absolutePath == getInstance()
              ?.networkFile?.absolutePath)
        }
        ?.forEach { it.delete() }
  }

  private fun processLog(type: LogType, message: String) {
    when (type) {
      DEBUG -> d(APPLICATION_LOG_TAG, message)
      ERROR -> e(APPLICATION_LOG_TAG, message)
    }
    getInstance()?.logQueue?.postRunnable(Runnable {
      try {
        write(LogType.ERROR, message)
        getInstance()?.streamWriter?.flush()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    })
  }


  private fun writeToError(message: Any?) {
    write(LogType.ERROR, message)
  }

  private fun write(type: LogType, message: Any?) {
    getInstance()?.streamWriter?.write(getInstance()?.dateFormat?.format(
        System.currentTimeMillis()) + formatFileName(type, message))
  }

  private fun formatFileName(type: LogType, message: Any?): String =
      " ${type.name}/${APPLICATION_LOG_TAG}: " + message + "\n"
}