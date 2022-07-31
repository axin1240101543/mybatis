package com.darren.es;

/**
 * <h3>mybatis</h3>
 * <p></p>
 *
 * @author : Darren
 * @date : 2022年07月31日 10:40:21
 **/
public class EsConfiguration {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

