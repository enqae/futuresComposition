import scala.util.Random

trait Helpers {

  def waitFor(secs: Int) = waitForMillis(secs * 1000)

  private[this] def waitForMillis(millis: Int) = Thread.sleep(millis)

  def doAction(info: String) = {
    print(s"$info")

    for (i <- 1 to 5) {
      // simulate some work
      print(s"$info - Step[$i]")
      waitForMillis(Random.nextInt(100))
    }
  }

  def print(info: String) = printThread(s"$info")

  private[this] def printThread(info: String) = println(s"[TH]. ${Thread.currentThread().getId} ... $info")

}
