package chapter07.demo06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.util.Date;
import java.util.Locale;

/**
 * @description 检查Cache-control
 * @date:2022/10/26 13:17
 * @author: qyl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CacheControl {
    private Date maxAge = null;
    private Date sMaxAge = null;
    private boolean mustRevalidate = false;
    private boolean noCache = false;
    private boolean noStore = false;
    private boolean proxyRevalidate = false;
    private boolean publicCache = false;
    private boolean privateCache = false;

    public CacheControl(String s) {
        if (s == null || !s.contains(":")) {
            return;
        }

        String value = s.split(":")[1].trim();
        String[] components = value.split(",");

        Date now = new Date();
        for (String component : components) {
            try {
                component = component.trim().toLowerCase(Locale.US);
                if (component.startsWith("max-age=")) {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    maxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
                } else if (component.startsWith("s-maxage=")) {
                    int secondsInTheFuture = Integer.parseInt(component.substring(8));
                    sMaxAge = new Date(now.getTime() + 1000 * secondsInTheFuture);
                } else if (component.equalsIgnoreCase("must-revalidate")) {
                    mustRevalidate = true;
                } else if (component.equals("proxy-revalidate")) {
                    proxyRevalidate = true;
                } else if (component.equalsIgnoreCase("no-cache")) {
                    noCache = true;
                } else if (component.equalsIgnoreCase("public")) {
                    publicCache = true;
                } else if (component.equalsIgnoreCase("private")) {
                    privateCache = true;
                }
            } catch (RuntimeException e) {
                continue;
            }
        }
    }
}
