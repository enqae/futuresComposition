import Misc._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

trait TheFutures {

  def fBasicProfile =
    (user: String) =>
      Future {
        doAction(s"Select BasicProfile ($user)")
        "basicProfile:MoonWalker"
      }

  def fRanking =
    (user: String) =>
      Future {
        doAction(s"Select Ranking Position ($user)")
        "125"
      }

  def fLastMedalInLevel =
    (basicProfile: String) =>
      Future {
        doAction(s"Select LastMedalInLevel ($basicProfile)")
        if (basicProfile.endsWith("MoonWalker")) "MoonConquest" else "NoMedalYet"
      }

}
