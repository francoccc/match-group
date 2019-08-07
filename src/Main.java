import com.franco.domain.MatchPlayer;
import com.franco.domain.Matcher;
import com.franco.group.TianTiMatchGroup;
import com.franco.listener.AddMatchLIstener;
import com.franco.strategy.PairMatchStrategy;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {

        final TianTiMatchGroup tianTiMatchGroup = (TianTiMatchGroup) new TianTiMatchGroup(new ArrayList(), new Matcher<MatchPlayer>() {

            @Override
            public boolean match(MatchPlayer o1, MatchPlayer o2) {
                return Math.abs(o1.getWin() - o2.getWin()) <= 3;
            }
        }, new PairMatchStrategy()).addListener(new AddMatchLIstener());

        final Random random = new Random();

        Thread[] threads = new Thread[5];
        for(int i = 0 ; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {

                @Override
                public void run() {
                    for(int i = 1; i <= 1000; i++) {
                        MatchPlayer matchPlayer = new MatchPlayer(count.getAndIncrement(), random.nextInt(8));
                        tianTiMatchGroup.add(matchPlayer);
                    }
                }
            });
            threads[i].start();
        }
    }
}
