import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

public class Server {

    public static void main(String[] args) throws JsonProcessingException {

        App app = new App();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(app);

        Javalin server = Javalin.create()
                .port(7070)
                .enableStaticFiles("/public")
                .start();
        server.ws("/vendor", ws -> {
            ws.onConnect(session -> System.out.println("Connected"));
            ws.onMessage((session, message) -> {
                System.out.println("Received: " + message);
                session.getRemote().sendString(jsonInString);
            });
        });

    }
}
