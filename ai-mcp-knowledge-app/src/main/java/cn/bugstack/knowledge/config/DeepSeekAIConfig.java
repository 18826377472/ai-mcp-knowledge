package cn.bugstack.knowledge.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.observation.ChatClientObservationConvention;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DeepSeekAIConfig {

    @Bean
    public OpenAiApi deepSeekApi(@Value("${spring.ai.deepseek.base-url}") String baseUrl, 
                                 @Value("${spring.ai.deepseek.api-key}") String apikey) {
        return OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apikey)
                .build();
    }

    @Bean("deepSeekChatModel")
    public OpenAiChatModel deepSeekChatModel(OpenAiApi deepSeekApi) {
        return new OpenAiChatModel(deepSeekApi);
    }

    @Bean("deepSeekEmbeddingModel")
    public OpenAiEmbeddingModel deepSeekEmbeddingModel(OpenAiApi deepSeekApi) {
        return new OpenAiEmbeddingModel(deepSeekApi);
    }

    @Bean("deepSeekSimpleVectorStore")
    public SimpleVectorStore deepSeekVectorStore(OpenAiEmbeddingModel deepSeekEmbeddingModel) {
        return SimpleVectorStore.builder(deepSeekEmbeddingModel).build();
    }

    /**
     * -- 删除旧的表（如果存在）
     * DROP TABLE IF EXISTS public.vector_store_deepseek;
     *
     * -- 创建新的表，使用UUID作为主键
     * CREATE TABLE public.vector_store_deepseek (
     *     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     *     content TEXT NOT NULL,
     *     metadata JSONB,
     *     embedding VECTOR(1536)
     * );
     *
     * SELECT * FROM vector_store_deepseek
     */
    @Bean("deepSeekPgVectorStore")
    public PgVectorStore deepSeekPgVectorStore(OpenAiEmbeddingModel deepSeekEmbeddingModel, JdbcTemplate jdbcTemplate) {
        return PgVectorStore.builder(jdbcTemplate, deepSeekEmbeddingModel)
                .vectorTableName("vector_store_deepseek")
                .build();
    }

    @Bean("deepSeekChatClientBuilder")
    public ChatClient.Builder deepSeekChatClientBuilder(OpenAiChatModel deepSeekChatModel) {
        return new DefaultChatClientBuilder(deepSeekChatModel, ObservationRegistry.NOOP, (ChatClientObservationConvention) null);
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

} 