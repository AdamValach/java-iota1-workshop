/*
Listen to the ZeroMQ data stream provided by IRI to monitor the tangle
in real time. We use the JeroMQ library in this example to listen to
new and confirmed transactions.
*/
import org.zeromq.ZMQ;

public class e09_zmq_listen {

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.SUB);

        socket.connect("tcp://zmq.devnet.iota.org:5556");
        socket.subscribe("tx"); //Subscribe to all new transactions, including trytes.
        socket.subscribe("sn"); //Subscribe to all confirmed transactions.

        while(true) {
            byte[] reply = socket.recv(0);
            String[] data = (new String(reply).split(" "));

            if(data[0].equals("tx")) System.out.println("NEW TRANSACTION" + "\n" + "Transaction hash: " + data[1] + "\n" + "Address: " + data[2] + "\n" + "Value: " + data[3] + "\n" + "Tag: " + data[4] + "\n");
            if(data[0].equals("sn")) System.out.println("CONFIRMED" + "\n" + "Transaction hash: " + data[2] + "\n" + "Address: " + data[3] + "\n");
        }
    }
}