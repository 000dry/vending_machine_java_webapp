import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.embeddedserver.jetty.websocket.WsSession;

public class Server {

    public static App app;
    public static ObjectMapper mapper;

    public static void main(String[] args) throws JsonProcessingException {

        app = new App("NONE");
        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(app);

        Javalin server = Javalin.create()
                .port(7070)
                .enableStaticFiles("/public")
                .start();

        server.ws("/vendor", ws -> {
            ws.onConnect(session -> {
                session.getRemote().sendString(jsonInString);
                System.out.println("Connected");});
            ws.onMessage((WsSession session, String message) -> {
                System.out.println(message);
                App updatedApp = commandFromClient(message);
                String updatedJsonInString = mapper.writeValueAsString(updatedApp);
                session.getRemote().sendString(updatedJsonInString);
            });
        });
    }

    public static App commandFromClient(String message){
        if(message.equals("A") || message.equals("B") || message.equals("C")) {
            App updatedApp = app;
            updatedApp.vendingMachine.vendItem(message);
            return updatedApp;
        } else {
            return app;
        }
    }
}
