# ssm-scaffold
����һ��SpringMVC+Mybatis ���ּܣ�����ƽʱ�Ŀ���ʹ�ã�Ҳ����Ϊ���ֵ�ѧϰ��Ŀ��

##����
 - Spring :4.2.1.RELEASE
 - mybatis:3.3.0
 - mybatis-spring:1.2.3
 - druid:1.0.15
 - fastjson:1.2.7
 - mybatis-generator:1.3.2
 - pagehelper:4.0.1
 - slf4j:1.7.12
 - log4j:1.2.17

���ݿ�Ĭ��ʹ����mysql������
 - mysql-connection-java:5.1.6

��Щ����pom.xml�Ķ��������ҵ���ֱ���޸ģ�

	<properties>
        <junit-version>3.8.1</junit-version>
        <spring-version>4.2.1.RELEASE</spring-version>
        <mybatis-version>3.3.0</mybatis-version>
        <mybatis-spring-version>1.2.3</mybatis-spring-version>
        <druid-version>1.0.15</druid-version>
        <fastjson-version>1.2.7</fastjson-version>
        <mysql-connection-version>5.1.6</mysql-connection-version>
        <mybatis-generator-version>1.3.2</mybatis-generator-version>
        <pagehelper-version>4.0.1</pagehelper-version>
        <slf4j-version>1.7.12</slf4j-version>
        <log4j-version>1.2.17</log4j-version>
    </properties>

## ����˵��
 - applicationContext.xml:Spring �����ļ�
 - generator.properties:Mybatis-generator �����ļ�
 - generatorConfig.xml:Mybatis-generator �����ļ�
 - jdbc.properties:jdbc�����ļ�
 - log4j.properties:log4j �����ļ�
 - mvc-dispatcher-servlet.xml:SpringMVC�����ļ�

#ssm-scaffold
