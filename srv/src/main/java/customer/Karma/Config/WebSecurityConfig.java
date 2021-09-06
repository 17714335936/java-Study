package customer.Karma.Config;

import com.esotericsoftware.yamlbeans.YamlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Map<String, Map<String, Object>> properties = new HashMap<>();

    static {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlReader.class.getClassLoader().getResourceAsStream("application.yaml");) {
            properties = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable();
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .and()
//                .logout()
//                .and()
//                .httpBasic();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//
//        Map<String, Object> cds = properties.get("cds");
//        List<String> securityMockUsers = (List<String>)cds.get("security.mock.users");
//        List<Object> data = Collections.singletonList(securityMockUsers.get(0));
//        String name = ((Map<String, String>) data.get(0)).get("name");
//        String password = ((Map<String, String>) data.get(0)).get("password");
//        String roles = ((Map<String, String>) data.get(0)).get("roles");
//
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username(name)
//                        .password(password)
//                        .roles(roles)
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

}
