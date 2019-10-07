package com.tty.desh.net

import android.accounts.NetworkErrorException
import android.app.Activity
import android.app.Application
import android.os.Handler
import androidx.fragment.app.Fragment
import com.tty.desh.model.User

import kotlinx.android.synthetic.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

object Net {

    fun postAsync(url:String, content:String, callback: (String?) -> Unit){
        val handler = Handler()

        GlobalScope.launch {
            val response:String? = post(url, content)
            handler.post { callback(response) }
        }
    }

    fun getAsync(url:String, callback: (String?) -> Unit){
        val handler = Handler()

        GlobalScope.launch {
            val response:String? = get(url)
            handler.post { callback(response) }
        }
    }

    fun post(url:String, content:String):String?{
        var conn:HttpURLConnection? = null
        try {
            val mUrl = URL(url)
            conn = mUrl.openConnection() as HttpURLConnection

            conn.requestMethod = "POST"
            conn.readTimeout = this.readTimeout
            conn.connectTimeout = this.connectTimeout
            conn.doOutput = true

            val out:OutputStream = conn.outputStream
            out.write(content.toByteArray())
            out.flush()
            out.close()

            val responseCode:Int = conn.responseCode
            if (responseCode == OK){
                val inputStream:InputStream = conn.inputStream
                return inputStream.dataString
            } else {
                throw NetworkErrorException("response status is $responseCode")
            }
        } catch (e:NetworkErrorException){
            e.printStackTrace()
        } finally {
            conn?.disconnect()
        }
        return null
    }

    fun get(url:String):String?{
        var conn:HttpURLConnection? = null
        try {
            val mUrl = URL(url)
            conn = mUrl.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.readTimeout = this.readTimeout
            conn.connectTimeout = this.connectTimeout

            val responseCode = conn.responseCode
            if (responseCode == OK){
                val inputStream = conn.inputStream
                return inputStream.dataString
            } else{
                throw NetworkErrorException("response status is $responseCode")
            }
        } catch (e:NetworkErrorException){
            e.printStackTrace()
        } finally {
            conn?.disconnect()
        }
        return null
    }

    private const val bufferSize = 1024
    private const val readTimeout = 5000
    private const val connectTimeout = 10000
    private const val OK = 200

    /**
     * 获取数据字符串
     */
    private val InputStream.dataString:String
    get() {
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(bufferSize)
        var len:Int
        do {
            len = this.read(buffer)
            if (len == -1)
                break
            outputStream.write(buffer, 0, len)
        } while (true)
        this.close()
        val data:String = outputStream.toString()
        outputStream.close()
        return data
    }

    var comUser = ComUser()

    class ComUser: UserNet {
        override fun login(name: String, password: String): NetResult<User> {
            if (name == "test" && password == "123456"){
                return NetResult(OK, "ok", "登录成功", User(10042, "test", "测试账号", "token-test", "2019-09-18 16:00", true))
            }
        }

    }
}