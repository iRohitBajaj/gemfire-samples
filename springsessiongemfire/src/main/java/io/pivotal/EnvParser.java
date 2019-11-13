package io.pivotal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvParser {
    private static EnvParser instance;
    private static final Pattern p = Pattern.compile("(.*)\\[(\\d*)\\]");

    private EnvParser() {
    }

    // host[port1],host2[port2]
    public static String getGemfireLocators() throws IOException, URISyntaxException {
        StringBuilder locatorList = new StringBuilder();
        Map credentials = getGemfireCredentials();
        List<String> locators = (List<String>) credentials.get("locators");
        for (int i=0; i<locators.size();  i++) {
            Matcher m = p.matcher(locators.get(i));
            if (!m.matches()) {
                throw new IllegalStateException("Unexpected locator format. expected host[port], got"+locators.get(i));
            }
            locatorList.append(m.group(1)).append("[").append(m.group(2)).append("]");
            if(i<locators.size()-1){
                locatorList.append(",");
            }
        }
        return locatorList.toString();
    }

    public static String getGemfireUsername() throws IOException {
        String username = null;
        Map credentials = getGemfireCredentials();
        List<Map> users = (List<Map>) credentials.get("users");
        if(users.size()> 0){
            username = (String) users.get(0).get("username");
        }
        return username;
    }

    public static String getGemfirePasssword() throws IOException {
        String password = null;
        Map credentials = getGemfireCredentials();
        List<Map> users = (List<Map>) credentials.get("users");
        if(users.size()> 0){
            password = (String) users.get(0).get("password");
        }
        return password;
    }

    private static Map getGemfireCredentials() throws IOException {
        Map credentials = null;
        String envContent = System.getenv().get("VCAP_SERVICES");
        ObjectMapper objectMapper = new ObjectMapper();
        Map services = objectMapper.readValue(envContent, Map.class);
        List gemfireService = getGemFireService(services);
        if (gemfireService != null) {
            Map serviceInstance = (Map) gemfireService.get(0);
            credentials = (Map) serviceInstance.get("credentials");
        }
        return credentials;

    }

    private static List getGemFireService(Map services) {
        List l = (List) services.get("p-cloudcache");
        if (l == null) {
            throw new IllegalStateException("GemFire service is not bound to this application");
        }
        return l;
    }

    private static List getMySqlService(Map services) {
        List l = (List) services.get("p.mysql");
        if (l == null) {
            throw new IllegalStateException("Mysql service is not bound to this application");
        }
        return l;
    }

    private static Map getMySqlCredentials() throws IOException {
        Map credentials = null;
        String envContent = System.getenv().get("VCAP_SERVICES");
        ObjectMapper objectMapper = new ObjectMapper();
        Map services = objectMapper.readValue(envContent, Map.class);
        List mySqlService = getMySqlService(services);
        if (mySqlService != null) {
            Map serviceInstance = (Map) mySqlService.get(0);
            credentials = (Map) serviceInstance.get("credentials");
        }
        return credentials;

    }

    public static String getMySqlUsername() throws IOException {
        String username = null;
        Map credentials = getMySqlCredentials();
        if(credentials.size()> 0 && !StringUtils.isEmpty(credentials.get("username"))){
            username = (String) credentials.get("username");
        }
        return username;
    }

    public static String getMySqlPassword() throws IOException {
        String username = null;
        Map credentials = getMySqlCredentials();
        if(credentials.size()> 0 && !StringUtils.isEmpty(credentials.get("password"))){
            username = (String) credentials.get("password");
        }
        return username;
    }

    public static String getMySqlJdbcUrl() throws IOException {
        String username = null;
        Map credentials = getMySqlCredentials();
        if(credentials.size()> 0 && !StringUtils.isEmpty(credentials.get("jdbcUrl"))){
            username = (String) credentials.get("jdbcUrl");
        }
        return username;
    }

}
