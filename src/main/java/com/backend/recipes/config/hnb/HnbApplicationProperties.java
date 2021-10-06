package com.backend.recipes.config.hnb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.hnb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HnbApplicationProperties {

    private String url = "https://api.hnb.hr/tecajn/v1?valuta=";

}

