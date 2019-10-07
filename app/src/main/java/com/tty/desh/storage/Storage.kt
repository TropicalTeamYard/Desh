package com.tty.desh.storage

import android.annotation.SuppressLint
import android.content.Context
import com.tty.desh.model.User


class Storage() {
    var userProvider: UserProvider = UserProvider(this)
    private var context:Context? = null
    private var isLoaded = false

    fun beginTransaction(context: Context){
        this.context = context
    }

    fun endTransaction(){
        this.context = null
    }

    fun load(){
        if (!isLoaded){
            isLoaded = true
            userProvider.load()
        }
    }

    /**
     * 用户数据提供器
     * 提供的功能: #1 和数据交换存储，临时存储。
     * from @author{cht}, developed by @author{cht}
     */
    inner class UserProvider(var storage: Storage) {
        var users = ArrayList<User>()

        fun load(){
            val dbHelper = DatabaseHelper(context!!)
            val dbForUser = dbHelper.dbForUser

            val ids = dbForUser.getIds()
            users.clear()
            for (id in ids){
                val user = dbForUser.getData(id)
                users.add(user)
            }
        }

        fun update(){
            val dbHelper = DatabaseHelper(context!!)
            val dbForUser = dbHelper.dbForUser

            for (user in users){
                dbForUser.updateData(user.id, user)
            }
        }

        fun fetch(){

        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: Storage? = null
        fun get(): Storage{
            return if (instance == null){
                Storage()
            } else {
                instance!!
            }
        }
    }
}