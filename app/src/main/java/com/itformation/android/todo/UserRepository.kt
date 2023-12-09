package com.itformation.android.todo

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
class UserRepository {
    private val db = Firebase.firestore
    private val usersCollection = db.collection("users")

    fun getUsers(): Flow<List<User>> =
        callbackFlow {
            val subscription = usersCollection.addSnapshotListener { value, _ ->

                value?.let {
                    val userList = it.documents.map { document ->
                        document.toObject(User::class.java)
                    }.filterNotNull()
                    trySend(userList).isSuccess
                }
            }
            awaitClose { subscription.remove() }
        }

    suspend fun addUser(user: User) {
        usersCollection.add(user)
    }

    suspend fun deleteUser(user: User) {
        usersCollection.document(user.id).delete()
    }
    suspend fun updateUser(user: User) {
        user.nom?.let { usersCollection.document(it).set(user) }
    }
}
