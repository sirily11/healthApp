import java.*
import java.io.*
import java.net.Socket
import java.net.UnknownHostException

class client// constructor to put ip address and port
@Throws(IOException::class)
constructor(address: String, port: Int) {
    // initialize socket and input output streams
    val input: DataInputStream? = null
    var socket: Socket? = null
    var osw: DataOutputStream? = null

    init {

        try {
            socket = Socket(address, port)
            osw = DataOutputStream(socket?.getOutputStream())

        } catch (e: IOException) {
            println(e)
        }

    }

    fun sendMsg(msg: String) {

        try {
            println(msg)
            osw?.writeUTF(msg)
            println("Send msg " + msg)
        }catch (e : IOException){
            println(e)
        }
    }

    fun closeSocket() {
        try {
            socket?.close()
        }catch (e : IOException){
            print(e)
        }
    }
}
