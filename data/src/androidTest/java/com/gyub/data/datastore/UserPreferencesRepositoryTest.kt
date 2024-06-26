package com.gyub.data.datastore

import android.content.Context
import android.content.ContextWrapper
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import com.gyub.common.const.UserPrefKey.TOKEN
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

/**
 *
 *
 * @author   Gyub
 * @created  2024/06/25
 */
class UserPreferencesRepositoryTest {
    @get:Rule
    val tempFolder = TemporaryFolder()

    private lateinit var context: Context
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        val testContext = object : ContextWrapper(context) {
            override fun getFilesDir(): File {
                return tempFolder.root
            }
        }
        userPreferencesRepository = UserPreferencesRepository(testContext)
    }

    @Test
    fun test_save_and_get_string() = runTest {
        val key = "test_key"
        val value = "test_value"

        userPreferencesRepository.saveString(key, value)

        val dataStoreKey = stringPreferencesKey(key)
        val storedValue = context.dataStore.data.first()[dataStoreKey]

        assertEquals(value, storedValue)
    }

    @Test
    fun test_token_save_and_get() = runTest {

        userPreferencesRepository.saveString(TOKEN, "token")
        val token = userPreferencesRepository.token.first()

        assertEquals("token", token)
    }
}