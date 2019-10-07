package com.tty.desh.storage

data class DBData<T>( var value:T, var isChanged:Boolean = false)