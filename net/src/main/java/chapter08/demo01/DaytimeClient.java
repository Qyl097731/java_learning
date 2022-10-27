package chapter08.demo01;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date:2022/10/27 14:58
 * @author: qyl
 */
public class DaytimeClient {
    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "time.nist.gov";
        try(Socket socket = new Socket(hostname,13)){
            socket.setSoTimeout(15000);
            StringBuilder time = new StringBuilder();
            try(Reader r = new InputStreamReader(socket.getInputStream())){
                for (int c = r.read() ; c !=-1;c = r.read()){
                    time.append((char)c);
                }
                System.out.println(time);
                System.out.println(parseDate(time.toString()));
            }
        } catch (IOException | ParseException e) {
            System.err.println(e);
        }
    }

    private static Date parseDate(String s) throws ParseException {
        String[] pieces = s.split(" ");
        String dateTime = pieces[1] + " " + pieces[2] + " UTC";
        DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss z");
        return format.parse(dateTime);
    }
}
