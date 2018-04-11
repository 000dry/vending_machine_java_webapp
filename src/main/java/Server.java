import io.javalin.Javalin;

public class Server {

    public static void main(String[] args) {

        App app = new App();

        Javalin server = Javalin.create()
                .port(7070)
                .enableStaticFiles("/public")
                .start();


    }
}
