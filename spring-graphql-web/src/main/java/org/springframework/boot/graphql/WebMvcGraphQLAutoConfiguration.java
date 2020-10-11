/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.boot.graphql;

import graphql.GraphQL;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.WebMvcGraphQLHandler;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Collections;

import static org.springframework.web.servlet.function.RequestPredicates.accept;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(GraphQL.class)
@ConditionalOnBean(GraphQL.Builder.class)
@AutoConfigureAfter(GraphQLAutoConfiguration.class)
public class WebMvcGraphQLAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebMvcGraphQLHandler graphQLHandler(GraphQL.Builder graphQLBuilder) {
        return new WebMvcGraphQLHandler(graphQLBuilder.build(), Collections.emptyList());
    }

    @Bean
    public RouterFunction<ServerResponse> graphQLQueryEndpoint(WebMvcGraphQLHandler handler) {
        return RouterFunctions.route()
                .POST("/graphql", accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8), handler)
                .build();
    }

}
