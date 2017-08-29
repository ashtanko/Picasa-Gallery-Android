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

import android.util.Log
import io.shtanko.picasagallery.Config
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
      val dir = File(sdCard.absolutePath + Config.LOGS_PATH)
      dir.mkdirs()
      currentFile = File(dir, dateFormat.format(System.currentTimeMillis()) + ".txt")
    } catch (e: Exception) {
      e.printStackTrace()
    }

    try {
      logQueue = DispatchQueue("logQueue")
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
      val dir = File(sdCard.absolutePath + Config.LOGS_PATH)
      dir.mkdirs()
      getInstance()?.networkFile = File(dir,
          getInstance()?.dateFormat?.format(System.currentTimeMillis()) + "_net.txt")
      return getInstance()?.networkFile?.absolutePath.toString()
    } catch (e: Exception) {

    }
    return ""
  }

  override fun e(message: String, t: Throwable) {
    Log.e(Config.APPLICATION_LOG_TAG, message, t)
    if (getInstance()?.streamWriter != null) {
      getInstance()?.logQueue?.postRunnable(Runnable {
        try {
          getInstance()?.streamWriter?.write(getInstance()?.dateFormat?.format(
              System.currentTimeMillis()) + " ${LogType.ERROR.name}/${Config.APPLICATION_LOG_TAG}: " + message + "\n")
          getInstance()?.streamWriter?.write(t.toString())
          getInstance()?.streamWriter?.flush()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      })
    }
  }

  override fun e(message: String) {
    processLog(ERROR, message)
  }

  override fun e(message: Throwable) {
    message.printStackTrace()
    if (getInstance()?.streamWriter != null) {
      getInstance()?.logQueue?.postRunnable(Runnable {
        try {
          getInstance()?.streamWriter?.write(getInstance()?.dateFormat?.format(
              System.currentTimeMillis()) + " ${LogType.ERROR.name}/${Config.APPLICATION_LOG_TAG}: " + message + "\n")
          val stack = message.stackTrace
          for (a in stack.indices) {
            getInstance()?.streamWriter?.write(getInstance()?.dateFormat?.format(
                System.currentTimeMillis()) + " ${LogType.ERROR.name}/${Config.APPLICATION_LOG_TAG}: " + stack[a] + "\n")
          }
          getInstance()?.streamWriter?.flush()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      })
    } else {
      message.printStackTrace()
    }
  }

  override fun d(message: String) {
    processLog(DEBUG, message)
  }

  private fun processLog(type: LogType, message: String) {
    when (type) {
      DEBUG -> {
        Log.d(Config.APPLICATION_LOG_TAG, message)
      }
      ERROR -> {
        Log.e(Config.APPLICATION_LOG_TAG, message)
      }
    }

    if (getInstance()?.streamWriter != null) {
      getInstance()?.logQueue?.postRunnable(Runnable {
        try {
          getInstance()?.streamWriter?.write(getInstance()?.dateFormat?.format(
              System.currentTimeMillis()) + " ${type.name}/${Config.APPLICATION_LOG_TAG}: " + message + "\n")
          getInstance()?.streamWriter?.flush()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      })
    }
  }

  override fun cleanupLogs() {
    val sdCard = PicasaApplication.app.applicationContext.getExternalFilesDir(null)
    val dir = File(sdCard.absolutePath + Config.LOGS_PATH)
    val files = dir.listFiles()
    if (files != null) {
      for (a in files.indices) {
        val file = files[a]
        if (getInstance()?.currentFile != null && file.absolutePath == getInstance()?.currentFile?.absolutePath) {
          continue
        }
        if (getInstance()?.networkFile != null && file.absolutePath == getInstance()?.networkFile?.absolutePath) {
          continue
        }
        file.delete()
      }
    }
  }
}