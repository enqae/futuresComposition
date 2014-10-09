import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Function;

public class Example_00_FutureBlockingGet {


    public static void main(String[] args) throws Exception {

        Misc.print("StartingMainProcessing Action");

        String fullProfile = findFullProfile("user");

        Misc.print(fullProfile);

        Misc.print("End of Main Processing");
    }

    private static String findFullProfile(final String user) throws Exception {

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

        // BLOCKING Operation
        String ranking = fRanking.get();

        // BLOCKING Operation
        String basicProfile = fBasicProfile.get();

        Future<String> fLastMedalInLevel = lastMedalInLevel.apply(basicProfile);
        // BLOCKING Operation
        String lastMedal = fLastMedalInLevel.get();

        return String.format("FullProfile: %s;%s;%s", basicProfile, ranking, lastMedal);
    }

}
