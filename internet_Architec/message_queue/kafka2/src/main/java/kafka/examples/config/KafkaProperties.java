/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kafka.examples.config;

public class KafkaProperties {
	public static String ZOOKEEPER_CLUSTER = 
			"192.168.1.201:2181," + 
			"192.168.1.202:2181," + 
			"192.168.1.203:2181" ;
	
	public static String KAFKA_CLUSTER = 
			"192.168.1.201:9092," + 
			"192.168.1.202:9092," + 
			"192.168.1.203:9092" ;
	
    public static final String KAFKA_SERVER_URL = "192.168.1.201";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100_000;
    
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";
    
    public static final String TOPIC1 = "topic1";
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    

    private KafkaProperties() {}
}