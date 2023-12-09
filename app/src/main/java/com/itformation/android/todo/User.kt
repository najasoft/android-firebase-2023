package com.itformation.android.todo

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    @DocumentId val id: String = "",
    @PropertyName("nom") val nom: String? = null,
    @PropertyName("age") val age: Int? = null
)
