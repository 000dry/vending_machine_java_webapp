import io.javalin.Javalin;

public class Server {

    public static void main(String[] args) {

        App app = new App();

        Javalin server = Javalin.create()
                .port(7070)
                .enableStaticFiles("/public")
                .start();
        server.ws("/vendor", ws -> {
            ws.onConnect(session -> System.out.println("Connected"));
            ws.onMessage((session, message) -> {
                System.out.println("Received: " + message);
                session.getRemote().sendString("Echo server: " + message);
            });
        });

    }
}
