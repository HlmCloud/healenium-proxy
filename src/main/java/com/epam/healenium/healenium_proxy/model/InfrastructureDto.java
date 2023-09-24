package com.epam.healenium.healenium_proxy.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InfrastructureDto {

    private String schema;
    private String introductionAIMessage;
    private String finalResultAIMessage;
}
