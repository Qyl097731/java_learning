package chapter06.demo01;

import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;

/**
 * @description 阻塞所有.goc域的cookie，放行其他cookie
 * @date:2022/10/25 20:56
 * @author: qyl
 */
public class NoGovernmentCookies implements CookiePolicy {
    @Override
    public boolean shouldAccept(URI uri, HttpCookie cookie) {
        if (uri.getAuthority().toLowerCase().endsWith(".gov")
        || cookie.getDomain().toLowerCase().endsWith(".gov")){
            return false;
        }
        return true;
    }
}
