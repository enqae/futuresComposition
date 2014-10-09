import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Example_01_RxJavaNonBlockingComposition {

    public static void main(String[] args) throws Exception {

        Misc.print("StartingMainProcessing Action");

        findFullProfile("JaneNone")
                .subscribe(fp -> {
                    Misc.print("EndingFullProfileProcessing Action");
                    Misc.print(String.format("FullProfile: %s", fp));
                });

        Misc.print("End of Main Processing");

        // Just waiting all work to be done
        Misc.waitFor(5);
    }

    private static Observable<String> findFullProfile(final String user) throws Exception {

        // Java 8 Lambdas are used to simplify this example
        Future<String> fRanking = ForkJoinPool.commonPool().submit(() -> {
            Misc.doAction("Select Ranking " + user);
            return "125";
        });

        Future<String> fBasicProfile = ForkJoinPool.commonPool().submit(() -> {
            Misc.doAction("Select BasicProfile " + user);
            return "BasicProfile:MoonWalker";
        });

        Function<String, Future<String>> lastMedalInLevel =
                (String basicProfile) ->
                        ForkJoinPool.commonPool().submit(() -> {
                            Misc.doAction("Select LastMedalInLevel");
                            return basicProfile.endsWith("MoonWalker") ? "MoonConquest" : "NoMedalYet";
                        });


        Observable<String> oBasicProfile = Observable.from(fBasicProfile);
        Observable<String> oRanking = Observable.from(fRanking);
        Observable<String> oLastMedal = oBasicProfile.flatMap(bp -> Observable.from(lastMedalInLevel.apply(bp)));

        return Observable
                .zip(
                        oBasicProfile,
                        oRanking,
                        oLastMedal,
                        (basicProfile, ranking, lastMedal)
                                -> String.format("%s;%s;%s", basicProfile, ranking, lastMedal))
                .subscribeOn(Schedulers.computation());
    }

}
