import java.*
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.net.UnknownHostException

class client// constructor to put ip address and port
@Throws(IOException::class)
constructor(address: String ="", port: Int) {
    // initialize socket and input output streams
    private var input: DataInputStream? = null
    private var socket: Socket? = null
    private var osw: DataOutputStream? = null
    private var portNum = port
    init {
        try {
            socket = Socket(address, port)
            osw = DataOutputStream(socket!!.getOutputStream())
            input = DataInputStream(socket!!.getInputStream())

        } catch (e: IOException) {
            //System.err.print(e)
        }

    }

    @Throws(IOException::class)
    fun sendMsg(msg: String) {
        osw!!.writeUTF(msg)
        println("Send msg " + msg)

    }

    @Throws(IOException::class)
    fun recieveMsg() : String {
       return input!!.readUTF()
    }

    fun recieve_msg_as_server() : String{
        val server = ServerSocket(this.portNum)
        val socket = server.accept()
        var inputdata: DataInputStream = DataInputStream(socket.getInputStream())
        //println(inputdata.readUTF())
        val message = inputdata.readUTF()
        socket.close()
        server.close()
        return message
    }
    @Throws(IOException::class)
    fun closeSocket() {
        socket!!.close()
    }
}
