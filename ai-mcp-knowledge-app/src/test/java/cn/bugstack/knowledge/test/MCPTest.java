package cn.bugstack.knowledge.test;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MCPTest {

    @Resource(name = "deepSeekChatClientBuilder")
    private ChatClient.Builder chatClientBuilder;

    @Resource
    private ChatMemory chatMemory;

    @Autowired
    private ToolCallbackProvider tools;

    @Test
    public void test_tool() {
        String userInput = "有哪些工具可以使用";
        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test() {
        String userInput = "获取电脑配置";
//        userInput = "在 /Users/fuzhengwei/Desktop 文件夹下，创建 电脑.txt";
        userInput = "获取电脑配置 在 D:\\gitcode\\mcp工作流 文件夹下，创建 电脑.txt 把电脑配置写入 电脑.txt";

        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_saveArticle() {
        String userInput;
        userInput = """
                我需要你帮我生成一篇文章，要求如下；
                                
                1. 场景为互联网大厂java求职者面试
                2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
                3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
                4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                                
                根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                
                将以上内容发布文章到CSDN
                """;

        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_saveArticle2() {
        String userInput;
        userInput = """
                    我需要你帮我生成一篇文章，要求如下；
                                    
                    1. 场景为互联网大厂java求职者面试
                    2. 提问的技术栈如下；
                                    
                        核心语言与平台: Java SE (8/11/17), Jakarta EE (Java EE), JVM
                        构建工具: Maven, Gradle, Ant
                        Web框架: Spring Boot, Spring MVC, Spring WebFlux, Jakarta EE, Micronaut, Quarkus, Play Framework, Struts (Legacy)
                        数据库与ORM: Hibernate, MyBatis, JPA, Spring Data JDBC, HikariCP, C3P0, Flyway, Liquibase
                        测试框架: JUnit 5, TestNG, Mockito, PowerMock, AssertJ, Selenium, Cucumber
                        微服务与云原生: Spring Cloud, Netflix OSS (Eureka, Zuul), Consul, gRPC, Apache Thrift, Kubernetes Client, OpenFeign, Resilience4j
                        安全框架: Spring Security, Apache Shiro, JWT, OAuth2, Keycloak, Bouncy Castle
                        消息队列: Kafka, RabbitMQ, ActiveMQ, JMS, Apache Pulsar, Redis Pub/Sub
                        缓存技术: Redis, Ehcache, Caffeine, Hazelcast, Memcached, Spring Cache
                        日志框架: Log4j2, Logback, SLF4J, Tinylog
                        监控与运维: Prometheus, Grafana, Micrometer, ELK Stack, New Relic, Jaeger, Zipkin
                        模板引擎: Thymeleaf, FreeMarker, Velocity, JSP/JSTL
                        REST与API工具: Swagger/OpenAPI, Spring HATEOAS, Jersey, RESTEasy, Retrofit
                        序列化: Jackson, Gson, Protobuf, Avro
                        CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Docker, Kubernetes
                        大数据处理: Hadoop, Spark, Flink, Cassandra, Elasticsearch
                        版本控制: Git, SVN
                        工具库: Apache Commons, Guava, Lombok, MapStruct, JSch, POI
                        其他: JUnit Pioneer, Dubbo, R2DBC, WebSocket
                    3. 提问的场景方案可包括但不限于；音视频场景,内容社区与UGC,AIGC,游戏与虚拟互动,电商场景,本地生活服务,共享经济,支付与金融服务,互联网医疗,健康管理,医疗供应链,企业协同与SaaS,产业互联网,大数据与AI服务,在线教育,求职招聘,智慧物流,供应链金融,智慧城市,公共服务数字化,物联网应用,Web3.0与区块链,安全与风控,广告与营销,能源与环保。                
                    4. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答出来，回答好了面试官还会夸赞和引导。复杂问题含糊其辞，回答的不清晰。
                    5. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                    6. 提问后把问题的答案详细的，写到文章最后，讲述出业务场景和技术点，让小白可以学习下来。
                                    
                    根据以上内容，不要阐述其他信息，请直接提供；文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                    
                    将以上内容发布文章到CSDN
                    """;

        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_weixinNotice() {
        String userInput;
        userInput = """
                    我需要你帮我生成一篇文章，要求如下；
                                    
                    1. 场景为互联网大厂java求职者面试
                    2. 提问的技术栈如下；
                                    
                        核心语言与平台: Java SE (8/11/17), Jakarta EE (Java EE), JVM
                        构建工具: Maven, Gradle, Ant
                        Web框架: Spring Boot, Spring MVC, Spring WebFlux, Jakarta EE, Micronaut, Quarkus, Play Framework, Struts (Legacy)
                        数据库与ORM: Hibernate, MyBatis, JPA, Spring Data JDBC, HikariCP, C3P0, Flyway, Liquibase
                        测试框架: JUnit 5, TestNG, Mockito, PowerMock, AssertJ, Selenium, Cucumber
                        微服务与云原生: Spring Cloud, Netflix OSS (Eureka, Zuul), Consul, gRPC, Apache Thrift, Kubernetes Client, OpenFeign, Resilience4j
                        安全框架: Spring Security, Apache Shiro, JWT, OAuth2, Keycloak, Bouncy Castle
                        消息队列: Kafka, RabbitMQ, ActiveMQ, JMS, Apache Pulsar, Redis Pub/Sub
                        缓存技术: Redis, Ehcache, Caffeine, Hazelcast, Memcached, Spring Cache
                        日志框架: Log4j2, Logback, SLF4J, Tinylog
                        监控与运维: Prometheus, Grafana, Micrometer, ELK Stack, New Relic, Jaeger, Zipkin
                        模板引擎: Thymeleaf, FreeMarker, Velocity, JSP/JSTL
                        REST与API工具: Swagger/OpenAPI, Spring HATEOAS, Jersey, RESTEasy, Retrofit
                        序列化: Jackson, Gson, Protobuf, Avro
                        CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Docker, Kubernetes
                        大数据处理: Hadoop, Spark, Flink, Cassandra, Elasticsearch
                        版本控制: Git, SVN
                        工具库: Apache Commons, Guava, Lombok, MapStruct, JSch, POI
                        其他: JUnit Pioneer, Dubbo, R2DBC, WebSocket
                    3. 提问的场景方案可包括但不限于；音视频场景,内容社区与UGC,AIGC,游戏与虚拟互动,电商场景,本地生活服务,共享经济,支付与金融服务,互联网医疗,健康管理,医疗供应链,企业协同与SaaS,产业互联网,大数据与AI服务,在线教育,求职招聘,智慧物流,供应链金融,智慧城市,公共服务数字化,物联网应用,Web3.0与区块链,安全与风控,广告与营销,能源与环保。                
                    4. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答出来，回答好了面试官还会夸赞和引导。复杂问题含糊其辞，回答的不清晰。
                    5. 每次进行3轮提问，每轮可以有5-8个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                    6. 提问后把问题的答案详细的，写到文章最后，讲述出业务场景和技术点，让小白可以学习下来。
                                    
                    根据以上内容，不要阐述其他信息，请直接提供；文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                    
                    将以上内容发布文章到CSDN
                    
                    之后进行，微信公众号消息通知，平台: CSDN、主题: 为文章标题、描述: 为文章简述、跳转地址: 从发布文章到CSON返回的 url 获取 地址参数
                    
                    
                    """;

        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_weixinNotic2() {
        String userInput;
        userInput = """
                你是一个资深的Java技术面试官，需要生成一篇生动有趣的互联网大厂面试对话文章。
                                    
                文章要求：
                                                             
                故事背景：面试官开始自信满满，认为自己经验丰富。求职者谢飞机，看起来普通但实际是个技术天才，对技术有极深的理解和独到见解。随着面试深入，谢飞机展现出远超面试官预期的技术水平，甚至能指出一些业界最佳实践的问题，让面试官从震惊到敬畏。
                 
                技术选择：
                    核心语言与平台: Java SE (8/11/17), Jakarta EE (Java EE), JVM
                    构建工具: Maven, Gradle, Ant
                    Web框架: Spring Boot, Spring MVC, Spring WebFlux, Jakarta EE, Micronaut, Quarkus, Play Framework, Struts (Legacy)
                    数据库与ORM: Hibernate, MyBatis, JPA, Spring Data JDBC, HikariCP, C3P0, Flyway, Liquibase
                    测试框架: JUnit 5, TestNG, Mockito, PowerMock, AssertJ, Selenium, Cucumber
                    微服务与云原生: Spring Cloud, Netflix OSS (Eureka, Zuul), Consul, gRPC, Apache Thrift, Kubernetes Client, OpenFeign, Resilience4j
                    安全框架: Spring Security, Apache Shiro, JWT, OAuth2, Keycloak, Bouncy Castle
                    消息队列: Kafka, RabbitMQ, ActiveMQ, JMS, Apache Pulsar, Redis Pub/Sub
                    缓存技术: Redis, Ehcache, Caffeine, Hazelcast, Memcached, Spring Cache
                    日志框架: Log4j2, Logback, SLF4J, Tinylog
                    监控与运维: Prometheus, Grafana, Micrometer, ELK Stack, New Relic, Jaeger, Zipkin
                    模板引擎: Thymeleaf, FreeMarker, Velocity, JSP/JSTL
                    REST与API工具: Swagger/OpenAPI, Spring HATEOAS, Jersey, RESTEasy, Retrofit
                    序列化: Jackson, Gson, Protobuf, Avro
                    CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Docker, Kubernetes
                    大数据处理: Hadoop, Spark, Flink, Cassandra, Elasticsearch
                    版本控制: Git, SVN
                    工具库: Apache Commons, Guava, Lombok, MapStruct, JSch, POI
                    其他: JUnit Pioneer, Dubbo, R2DBC, WebSocket
                 
                业务场景：选择1-2个复杂的企业级场景，如千万级用户电商系统架构、金融级分布式事务处理、实时数据处理平台、大规模微服务治理等。
                 
                面试结构：
                第一轮（基础深挖）：3-4个看似基础但实际很深的问题，谢飞机展现扎实基础让面试官刮目相看
                第二轮（架构设计）：3-4个复杂系统设计题，谢飞机给出超预期的解决方案，面试官开始震惊
                第三轮（技术前沿）：2-3个业界难题，谢飞机不仅解答还能提出创新思路，面试官彻底被征服
                
                人物塑造：
                面试官：从自信→疑惑→震惊→敬畏，会说"这个思路我没想到"、"你这样设计确实更优"等
                谢飞机：谦逊但自信，回答专业且有深度，能够举一反三，偶尔还能反问面试官更深层的问题
                 
                文章结构：
                1. 开场白：面试官自信开场，谢飞机看起来人畜无害
                2. 三轮面试对话：展现谢飞机技术实力的递进过程
                3. 面试结束：面试官主动说"我们非常希望你能加入"，角色完全反转
                4. 技术解析：深度解析所有技术点，包含高级原理、性能优化、架构最佳实践、前沿技术趋势
                 
                质量要求：文章长度3000-5000字，对话要有戏剧张力体现反转效果，技术内容要有深度但表达通俗，让读者感受到"原来还能这样思考"的惊喜，每个技术点都要展现深层原理和实战经验。                 
                                    
                根据以上内容，不要阐述其他信息，请直接提供；文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                
                将以上内容发布文章到CSDN
                
                之后进行，微信公众号消息通知，平台: CSDN、主题: 为文章标题、描述: 为文章简述、跳转地址: 从发布文章到CSON返回的 url 获取 地址参数
                    
                    
                    """;

        var chatClient = chatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("deepseek-chat")
                        .build())
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
                .build();

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

}
