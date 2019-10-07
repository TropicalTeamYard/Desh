package com.tty.desh.net

import com.tty.desh.model.User

interface UserNet{
    fun login(name:String, password:String): NetResult<User>

}