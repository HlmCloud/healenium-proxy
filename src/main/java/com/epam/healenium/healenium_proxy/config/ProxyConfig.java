package com.epam.healenium.healenium_proxy.config;

import com.epam.healenium.healenium_proxy.model.InfrastructureDto;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class ProxyConfig {

    @Autowired
    private final Environment env;

    private Config config;

    public ProxyConfig(Environment env) {
        this.env = env;
    }

    public Config getConfig(String currentSessionId) {
        return config.withValue("sessionKey", ConfigValueFactory.fromAnyRef(currentSessionId));
    }

    public Config getProjectConfig(String currentSessionId, InfrastructureDto infraDto, Map<String, Object> hlmOptions) {
        Config fialConfig = config.withValue("sessionKey", ConfigValueFactory.fromAnyRef(currentSessionId))
                .withValue("schema", ConfigValueFactory.fromAnyRef(infraDto.getSchema()))
                .withValue("introduction", ConfigValueFactory.fromAnyRef(infraDto.getIntroductionAIMessage()))
                .withValue("finalmessage", ConfigValueFactory.fromAnyRef(infraDto.getFinalResultAIMessage()));
        hlmOptions.forEach((key, value) -> fialConfig.withValue(key, ConfigValueFactory.fromAnyRef(value)));
        return fialConfig;
    }

    @PostConstruct
    private void initConfig() {
        config = ConfigFactory.empty()
                .withValue("hlm.server.url", ConfigValueFactory.fromAnyRef(env.getProperty("proxy.healenium.container.url")))
                .withValue("hlm.imitator.url", ConfigValueFactory.fromAnyRef(env.getProperty("proxy.imitate.container.url")))
                .withValue("hlm.ai.url", ConfigValueFactory.fromAnyRef(env.getProperty("proxy.ai.container.url")))
                .withValue("heal-enabled", ConfigValueFactory.fromAnyRef(env.getProperty("healing.healenabled")))
                .withValue("recovery-tries", ConfigValueFactory.fromAnyRef(env.getProperty("healing.recoverytries")))
                .withValue("score-cap", ConfigValueFactory.fromAnyRef(env.getProperty("healing.scorecap")))
                .withValue("backlight-healing", ConfigValueFactory.fromAnyRef(true))
                .withValue("proxy", ConfigValueFactory.fromAnyRef(true));
    }
}
