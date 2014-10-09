import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class Example_03_FutureComposableFlatMap {

    public static void main(String[] args) throws Exception {

        Misc.print("StartingMainProcessing Action");

        findFullProfile("JaneNone")
                .thenAcceptAsync(fp -> {
                    Misc.print("EndingFullProfileProcessing Action");
                    Misc.print(String.format("FullProfile: %s", fp));
                });

        Misc.print("End of Main Processing");

        // Just waiting all work to be done
        Misc.waitFor(5);
    }

    private static CompletableFuture<String> findFullProfile(final String user) throws Exception {

        CompletableFuture<String> fRanking = CompletableFuture.supplyAsync(() -> {
            Misc.doAction("Select Ranking " + user);
            return "125";
        });

        CompletableFuture<String> fBasicProfile = CompletableFuture.supplyAsync(() -> {
            Misc.doAction("Select BasicProfile " + user);
            return "BasicProfile:MoonWalker";
        });

        Function<String, CompletableFuture<String>> lastMedalInLevel =
                (String basicProfile) ->
                        CompletableFuture.supplyAsync(() -> {
                            Misc.doAction("Select LastMedalInLevel");
                            return basicProfile.endsWith("MoonWalker") ? "MoonConquest" : "NoMedalYet";
                        });

        return CompletableFuture.allOf(fBasicProfile, fRanking)
                .thenComposeAsync(
                        na -> {
                            // All futures ARE completed at this time
                            String basicProfile = fBasicProfile.join();
                            String ranking = fRanking.join();

                            return lastMedalInLevel.apply(basicProfile)
                                    .thenApplyAsync(
                                            lm -> String.format("%s;%s;%s", basicProfile, ranking, lm)
                                    );
                        });

    }
}