package com.tty.desh.storage

interface IDBForModel<T>{
    fun getIds(): ArrayList<Int>
    fun getData(id: Int): T
    fun updateData(id: Int, data: T)
}