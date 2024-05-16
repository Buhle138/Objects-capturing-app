package com.example.assignmentparttwo.loginPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {

    val state = mutableStateOf(User())

    fun updateUserName(username: String){
        state.value = state.value.copy(username = username)
    }

    fun updateEmail(email: String){
        state.value = state.value.copy(email = email)
    }

    fun updatePassword(password: String){
        state.value = state.value.copy(password = password)
    }

    val usersState = mutableStateOf(Users())

    fun updateUsersList(user: User){

        var currentList = usersState.value.users.toMutableList()
        currentList.add(user)
        usersState.value = usersState.value.copy(users = currentList)

    }

}

data class  Users(
    val users: List<User> = mutableListOf()
)

data class User (

    val username: String = "",
    val email: String = "",
    val password: String = "",

)