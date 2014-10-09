import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class Example_02_FutureComposableFlatMap {

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

        return fBasicProfile
                .thenComposeAsync(
                        basicProfile -> fRanking
                                .thenComposeAsync(
                                        level -> lastMedalInLevel.apply(level)
                                                .thenApplyAsync(
                                                        lm -> String.format("%s;%s;%s", basicProfile, level, lm)
                                                )
                                )
                );

    }
}