package chapter08.demo05;

import java.io.*;
import java.net.*;

/**
 * @date:2022/10/27 18:27
 * @author: qyl
 */
public class Whois {
    public final static int DEFAULT_PORT = 43;
    public final static String DEFAULT_HOST = "whois.internic.net";

    private int port = DEFAULT_PORT;
    private InetAddress host;

    public Whois(InetAddress host, int port) {
        this.port = port;
        this.host = host;
    }

    public Whois(InetAddress host) {
        this(host, DEFAULT_PORT);
    }

    public Whois(String hostname, int port) throws UnknownHostException {
        this(InetAddress.getByName(hostname), port);
    }

    public Whois(String hostname) throws UnknownHostException {
        this(InetAddress.getByName(hostname), DEFAULT_PORT);
    }

    public Whois() throws UnknownHostException {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    // 搜索条目
    public enum SearchFor {
        ANY("Any"), NETWORK("Network"), PERSON("Person"), HOST("Host"),
        DOMAIN("Domain"), ORGANIZATION("Organization"), GROUP("Group"),
        GATEWAY("Gateway"), ASN("ASN");
        private String label;

        SearchFor(String label) {
            this.label = label;
        }

        public static SearchFor findSearchForByName(String name) {
            SearchFor[] categories = SearchFor.values();
            for (SearchFor category : categories) {
                if (category.label.equals(name)){
                    return category;
                }
            }
            return ANY;
        }
    }

    // 搜索的类别
    public enum SearchIn {
        ALL(""), NAME("Name"), MAILBOX("Mainbox"), HANDLE("!");
        private String label;

        SearchIn(String label) {
            this.label = label;
        }

        public static SearchIn findSearchInByName(String name) {
            Whois.SearchIn[] groups = Whois.SearchIn.values();
            for (Whois.SearchIn g : groups) {
                if (g.label.equals(name)) {
                    return g;
                }
            }
            return ALL;
        }
    }

    public String lookUpNames(String target, SearchFor category, SearchIn group, boolean exactMatch) throws IOException {
        String suffix = "";
        // 模糊匹配
        if (!exactMatch) suffix = ".";
        String prefix = category.label + " " + group.label;
        String query = prefix + target + suffix;
        try (Socket socket = new Socket()) {
            SocketAddress address = new InetSocketAddress(host, port);
            socket.connect(address);
            Writer out = new OutputStreamWriter(socket.getOutputStream(), "ASCII");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ASCII"));
            out.write(query + "\r\n");
            out.flush();

            StringBuilder response = new StringBuilder();
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                response.append(line);
                response.append("\r\n");
            }
            return response.toString();
        }
    }

    public InetAddress getHost() {
        return host;
    }

    public void setHost(String host) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
    }
}
