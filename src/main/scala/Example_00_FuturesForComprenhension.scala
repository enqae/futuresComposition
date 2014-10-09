import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Example_00_FuturesForComprenhension extends Example_00_Futures {

  def findFullProfile(user: String): Future[String] = {

    val fRankingForUser = fRanking(user)
    val fbasicProfileForUser = fBasicProfile(user)

    for {
      ranking <- fRankingForUser
      basicProfile <- fbasicProfileForUser
      lastMedal <- fLastMedalInLevel(basicProfile)
    } yield s"$basicProfile;$ranking;$lastMedal"

  }

}
