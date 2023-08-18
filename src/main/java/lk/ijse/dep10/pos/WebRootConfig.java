package lk.ijse.dep10.pos;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Import(JpaConfig.class)
public class WebRootConfig {

}
