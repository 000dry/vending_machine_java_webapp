import App.App;
import ClientHandlers.MessageHandler;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.embeddedserver.jetty.websocket.WsSession;


public class Server { //SERVER ADAPTED FROM https://javalin.io/tutorials/

    public static App app;
    public static MessageHandler mH;
    public static ObjectMapper mapper;

    public static void main(String[] args) throws JsonProcessingException {

        app = new App();

        mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String jsonInString = mapper.writeValueAsString(app);

        Javalin server = Javalin.create()
                .port(getHerokuAssignedPort())
                .enableStaticFiles("/public")
                .start();

        server.ws("/vendor", ws -> {
            ws.onConnect(session -> {
                session.getRemote().sendString(jsonInString);
                System.out.println("Connected");
            });

            ws.onMessage((WsSession session, String message) -> {
                if (message.equals("NEW SESSION")) {
                    app = new App();
                }
                mH = new MessageHandler(message, app);
                mH.commandFromClient();
                String updatedJsonInString = mapper.writeValueAsString(app);
                session.getRemote().sendString(updatedJsonInString);
            });
        });
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7070;
    }

}
