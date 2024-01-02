import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object SimpleServer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("simple-ag-server")
    implicit val executionContext = system.dispatcher

    val route =
      pathEndOrSingleSlash {
        complete("hello!")
      } ~
        path("version") {
          complete("Version is: 0.1.2")
        }

    val serverBinding: Future[Http.ServerBinding] = Http().newServerAt("0.0.0.0", 8080).bind(route)

    serverBinding.onComplete {
      case Success(bound) =>
        println(s"Server online at http://0.0.0.0:8080/")
      case Failure(e) =>
        Console.err.println(s"Server could not start!")
        e.printStackTrace()
        system.terminate()
    }

    scala.io.StdIn.readLine() // let it run until user presses return

    // Add shutdown hook to gracefully shutdown the server
    sys.addShutdownHook {
      serverBinding
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete { _ =>
          system.terminate() // and shutdown when done
          println("Server stopped")
        }
    }
  }
}
