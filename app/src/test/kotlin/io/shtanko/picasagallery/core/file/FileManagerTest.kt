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

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters.JVM
import java.io.File
import java.io.File.createTempFile

@FixMethodOrder(JVM)
class FileManagerTest {

	private lateinit var manager: FileManager
	private val FILE_PREFIX = "file"
	private val FILE_SUFFIX = ".fuck"
	private val FILE_CONTENT = "Test\nTest\nTest\n"

	@Before
	fun setUp() {
		manager = FileManagerImpl()
	}

	@Test
	fun is_file_existsTest() {
		assertEquals(manager.exists(getTmpFile()), true)
	}

	@Test
	fun write_to_file_Test() {
		val file = getTmpFile()
		manager.writeToFile(file, FILE_CONTENT)
		assertEquals(manager.readFileContent(file), FILE_CONTENT)
	}

	private fun getTmpFile(): File = createTempFile(FILE_PREFIX, FILE_SUFFIX)

}