package com.tty.desh.net

data class NetResult<T>(var status:Int, var shortcut:String, var msg:String, var data:T)