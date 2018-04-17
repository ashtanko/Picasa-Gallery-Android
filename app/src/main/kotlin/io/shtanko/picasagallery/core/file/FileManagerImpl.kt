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

package io.shtanko.picasagallery.core.file

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import javax.inject.Singleton

/**
 * {@link io.shtanko.picasagallery.core.file.FileManager} implementation.
 */

@Singleton
class FileManagerImpl : FileManager {

  override fun writeToFile(
    file: File,
    fileContent: String
  ) {
    if (file.exists()) {
      try {
        val writer = FileWriter(file)
        writer.write(fileContent)
        writer.close()
      } catch (e: IOException) {

      }
    }
  }

  override fun readFileContent(file: File): String {
    val fileContentBuilder = StringBuilder()
    if (file.exists()) {
      try {
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        var stringLine = bufferedReader.readLine()

        while (stringLine != null) {
          fileContentBuilder.append(stringLine)
              .append("\n")
          stringLine = bufferedReader.readLine()
        }
        bufferedReader.close()
        fileReader.close()
      } catch (e: IOException) {

      }
    }

    return fileContentBuilder.toString()
  }

  override fun exists(file: File) = file.exists()

  override fun clearDirectory(dir: File): Boolean {
    var result = false
    if (dir.exists()) {
      for (file in dir.listFiles()) {
        result = file.delete()
      }
    }
    return result
  }

  private fun isEOF(br: BufferedReader): Boolean {
    var result = false
    try {
      result = br.ready()
    } catch (e: IOException) {
      System.err.println(e)
    }
    return result
  }

}