package config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author qyl
 * @program WebBaseCfg.java
 * @Description TODO
 * @createTime 2022-08-25 17:06
 */
public class WebBaseCfg {
    @Value("${system.server.name}")
    private String serverName;
    @Value("${system.init.passwd}")
    private String initPasswd;
    @Value("${system.spa.users}")
    private String spaUsers;
    @Value("${system.swagger.open}")
    private String swaggerIsOpen;

    public WebBaseCfg() {
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getInitPasswd() {
        return this.initPasswd;
    }

    public String getSpaUsers() {
        return this.spaUsers;
    }

    public String getSwaggerIsOpen() {
        return this.swaggerIsOpen;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setInitPasswd(String initPasswd) {
        this.initPasswd = initPasswd;
    }

    public void setSpaUsers(String spaUsers) {
        this.spaUsers = spaUsers;
    }

    public void setSwaggerIsOpen(String swaggerIsOpen) {
        this.swaggerIsOpen = swaggerIsOpen;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WebBaseCfg)) {
            return false;
        } else {
            WebBaseCfg other = (WebBaseCfg)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$serverName = this.getServerName();
                    Object other$serverName = other.getServerName();
                    if (this$serverName == null) {
                        if (other$serverName == null) {
                            break label59;
                        }
                    } else if (this$serverName.equals(other$serverName)) {
                        break label59;
                    }

                    return false;
                }

                Object this$initPasswd = this.getInitPasswd();
                Object other$initPasswd = other.getInitPasswd();
                if (this$initPasswd == null) {
                    if (other$initPasswd != null) {
                        return false;
                    }
                } else if (!this$initPasswd.equals(other$initPasswd)) {
                    return false;
                }

                Object this$spaUsers = this.getSpaUsers();
                Object other$spaUsers = other.getSpaUsers();
                if (this$spaUsers == null) {
                    if (other$spaUsers != null) {
                        return false;
                    }
                } else if (!this$spaUsers.equals(other$spaUsers)) {
                    return false;
                }

                Object this$swaggerIsOpen = this.getSwaggerIsOpen();
                Object other$swaggerIsOpen = other.getSwaggerIsOpen();
                if (this$swaggerIsOpen == null) {
                    if (other$swaggerIsOpen != null) {
                        return false;
                    }
                } else if (!this$swaggerIsOpen.equals(other$swaggerIsOpen)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WebBaseCfg;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $serverName = this.getServerName();
        result = result * 59 + ($serverName == null ? 43 : $serverName.hashCode());
        Object $initPasswd = this.getInitPasswd();
        result = result * 59 + ($initPasswd == null ? 43 : $initPasswd.hashCode());
        Object $spaUsers = this.getSpaUsers();
        result = result * 59 + ($spaUsers == null ? 43 : $spaUsers.hashCode());
        Object $swaggerIsOpen = this.getSwaggerIsOpen();
        result = result * 59 + ($swaggerIsOpen == null ? 43 : $swaggerIsOpen.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "WebBaseCfg(serverName=" + this.getServerName() + ", initPasswd=" + this.getInitPasswd() + ", spaUsers=" + this.getSpaUsers() + ", swaggerIsOpen=" + this.getSwaggerIsOpen() + ")";
    }
}
