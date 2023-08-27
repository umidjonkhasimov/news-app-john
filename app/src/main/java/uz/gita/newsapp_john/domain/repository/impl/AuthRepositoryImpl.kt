package uz.gita.newsapp_john.domain.repository.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.data.MySharedPreferencesImpl
import uz.gita.newsapp_john.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val myPrefs: MySharedPreferences,
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun signIn(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                myPrefs.isSignedIn = true
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun signUp(userName: String, email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                myPrefs.isSignedIn = true
//                myPrefs.currentUserName = userName
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }

    override fun sigOut() {
        auth.signOut()
        myPrefs.isSignedIn = false
        myPrefs.currentUserLastName = null
        myPrefs.currentUserFirstName = null
    }
}