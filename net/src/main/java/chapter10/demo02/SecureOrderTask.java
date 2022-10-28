package chapter10.demo02;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * @description
 * @date:2022/10/28 21:25
 * @author: qyl
 */
public class SecureOrderTask {
    public final static int PORT = 7000;
    public final static String ALGORITHM = "SSL";

    public static void main(String[] args) {
        try {
            SSLContext context = SSLContext.getInstance(ALGORITHM);
            // 参考实现支支持X.509密钥
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            // Oracle的默认密钥库类型
            KeyStore ks = KeyStore.getInstance("JKS");

            // 安全考虑，每个密钥库都必须用口令短语加密
            // 在从磁盘加载前必须提供这个口令，口令短语以char[]数组形式存储，可以很快从内存中擦除，而不是等待垃圾回收
            char[] password = System.console().readPassword();
            // 通过password来解锁keystore
            ks.load(new FileInputStream("jnp4e.keys"), password);
            // 初始化KeyManagerFactory
            kmf.init(ks, password);
            // 初始化上下文
            context.init(kmf.getKeyManagers(), null, null);

            // 擦除口令
            Arrays.fill(password, '0');

            SSLServerSocketFactory factory = context.getServerSocketFactory();
            SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(PORT);

            // 增加匿名（未认证）密码组
            String[] supported = server.getSupportedCipherSuites();
            String[] annoCipherSuitesSupported = new String[supported.length];
            int numAnonCipherSuitesSupported = 0;
            for (int i = 0; i < supported.length; i++) {
                if (supported[i].indexOf("_anno_") > 0) {
                    annoCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
                }
            }
            String[] oldEnabled = server.getEnabledCipherSuites();
            String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuitesSupported];
            System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
            System.arraycopy(annoCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);

            server.setEnabledCipherSuites(newEnabled);

            // 完成所有设置工作，可以进行实际通信了
            while (true) {
                // socket是安全的 但是代码中看不出迹象
                try (Socket conn = server.accept()) {
                    Reader r = new InputStreamReader(conn.getInputStream());
                    int c;
                    while ((c = r.read()) != -1) {
                        System.out.print((char) c);
                    }
                }
            }

        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | KeyManagementException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }
}
