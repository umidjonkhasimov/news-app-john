package uz.gita.newsapp_john.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.newsapp_john.util.CURRENT_USER_EMAIL
import uz.gita.newsapp_john.util.CURRENT_USER_FIRST_NAME
import uz.gita.newsapp_john.util.CURRENT_USER_LAST_NAME
import uz.gita.newsapp_john.util.CURRENT_USER_PASSWORD
import uz.gita.newsapp_john.util.IS_SIGNED_IN
import uz.gita.newsapp_john.util.MY_PREFS
import javax.inject.Inject

class MySharedPreferencesImpl @Inject constructor(
    @ApplicationContext
    context: Context
) : MySharedPreferences {
    private val myPrefs: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        myPrefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        editor = myPrefs.edit()
    }

    override var isSignedIn: Boolean
        get() = myPrefs.getBoolean(IS_SIGNED_IN, false)
        set(value) = myPrefs.edit().putBoolean(IS_SIGNED_IN, value).apply()

    override var currentUserEmail: String?
        get() = myPrefs.getString(CURRENT_USER_EMAIL, null)
        set(value) = myPrefs.edit().putString(CURRENT_USER_EMAIL, value).apply()

    override var currentUserFirstName: String?
        get() = myPrefs.getString(CURRENT_USER_FIRST_NAME, null)
        set(value) = myPrefs.edit().putString(CURRENT_USER_FIRST_NAME, value).apply()

    override var currentUserLastName: String?
        get() = myPrefs.getString(CURRENT_USER_LAST_NAME, null)
        set(value) = myPrefs.edit().putString(CURRENT_USER_LAST_NAME, value).apply()

    override var currentUserPassword: String?
        get() = myPrefs.getString(CURRENT_USER_PASSWORD, null)
        set(value) = myPrefs.edit().putString(CURRENT_USER_PASSWORD, value).apply()

}