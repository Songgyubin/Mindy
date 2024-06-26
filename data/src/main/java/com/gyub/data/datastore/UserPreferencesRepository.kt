package com.gyub.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gyub.common.const.UserPrefKey.TOKEN
import com.gyub.data.datastore.UserPreferencesRepository.Companion.PREF_USER_INFO
import kotlinx.coroutines.flow.map

/**
 * DataStore로 사용자 데이터 관리
 *
 * @author   Gyub
 * @created  2024/06/25
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_USER_INFO)

class UserPreferencesRepository(private val context: Context) {
    val token = context.dataStore.data.map {
        it[stringPreferencesKey(TOKEN)].orEmpty()
    }

    /**
     * [String] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveString(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [Int] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveInt(key: String, value: Int) {
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [Long] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveLong(key: String, value: Long) {
        val preferenceKey = longPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [Double] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveDouble(key: String, value: Double) {
        val preferenceKey = doublePreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [Float] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveFloat(key: String, value: Float) {
        val preferenceKey = floatPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [Boolean] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveBoolean(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    /**
     * [ByteArray] 저장
     *
     * @param key
     * @param value
     */
    suspend fun saveByteArray(key: String, value: ByteArray) {
        val preferenceKey = byteArrayPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    companion object {
        const val PREF_USER_INFO = "PREF_USER_INFO"
    }
}