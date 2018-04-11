import io.javalin.Javalin;

public class Server {

    public static void main(String[] args) {
        Javalin.create()
                .port(7070)
                .enableStaticFiles("/public")
                .start();
    }
}
