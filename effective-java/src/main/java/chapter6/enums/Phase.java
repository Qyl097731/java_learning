package chapter6.enums;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qyl
 * @program Phase
 * @createTime 2022-09-02 11:58
 */
public enum Phase {
    SOLID, LIQUID, GAS;

    public enum transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

        private final Phase from;
        private final Phase to;

        transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<Phase, Map<Phase, transition>> m =
                Stream.of(values()).collect(Collectors.groupingBy(t -> t.from, () -> new EnumMap<>(Phase.class),
                        Collectors.toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase.class))));
    }


}
