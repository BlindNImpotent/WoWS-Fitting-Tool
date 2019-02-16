package WoWSFT.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wowsft")
public class CustomProperties
{
    private String language;

    private String server;

    private String globalLanguage;

    private String protocol;

    private String env;

    public String getLanguage() {
        return language;
    }

    public String getServer() {
        return server;
    }

    public String getGlobalLanguage() {
        return globalLanguage;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getEnv()
    {
        return env;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setGlobalLanguage(String globalLanguage) {
        this.globalLanguage = globalLanguage;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public void setEnv(String env)
    {
        this.env = env;
    }
}
