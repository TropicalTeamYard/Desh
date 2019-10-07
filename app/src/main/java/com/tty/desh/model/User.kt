package com.tty.desh.model

import com.tty.desh.storage.DBData

class User(var id: Int,var name: String,var nickname: String,var token: String,var update_time: String,var isChanged:Boolean = false)