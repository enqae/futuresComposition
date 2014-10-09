import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Example_02_FuturesAsync extends Example_00_Futures {

  def findFullProfile(user: String): Future[String] = {

    import scala.async.Async.{async, await}

    val fRankingForUser = fRanking(user)
    val fbasicProfileForUser = fBasicProfile(user)

    async {
      val ranking = await(fRankingForUser)
      val basicProfile = await(fbasicProfileForUser)
      val lastMedal = await(fLastMedalInLevel(basicProfile))

      s"$basicProfile;$ranking;$lastMedal"
    }

  }
}
