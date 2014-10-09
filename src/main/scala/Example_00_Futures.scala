import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

trait Example_00_Futures extends TheFutures with Helpers {

  def main(args: Array[String]) = {

    print("StartingMainProcessing Action")

    findFullProfile("JaneNone").map {
      fp => {
        print("EndingFullProfileProcessing Action")
        println(s"FullProfile: $fp")
      }
    }

    print("EndingMainProcessing Action")

    // Just waiting all work to be done
    waitFor(5)
  }

  def findFullProfile(user: String): Future[String]

}
