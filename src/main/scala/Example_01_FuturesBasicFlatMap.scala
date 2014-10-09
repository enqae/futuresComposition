import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

object Example_01_FuturesBasicFlatMap extends Example_00_Futures {

  def findFullProfile(user: String): Future[String] = {

    val fRankingForUser = fRanking(user)
    val fbasicProfileForUser = fBasicProfile(user)

    fRankingForUser
      .flatMap(
        ranking =>
          fbasicProfileForUser
            .flatMap(
              basicProfile =>
                fLastMedalInLevel(basicProfile)
                  .map(
                    lastMedal => s"$basicProfile;$ranking;$lastMedal"
                  )
            )
      )

  }

}
