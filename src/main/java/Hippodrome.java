import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

public class Hippodrome {
    private static final Logger logger = LogManager.getLogger();

    private final List<Horse> horses;

    public Hippodrome(List<Horse> horses) {
        if (isNull(horses)) {
            /*Если в конструктор был передан null, то перед пробросом исключения,
            добавить в лог запись вида: 2022-05-31 17:29:30,029 ERROR Hippodrome: Horses list is null*/
            logger.error("Horses list is null");
            throw new IllegalArgumentException("Horses cannot be null.");
        } else if (horses.isEmpty()) {
            /*Если в конструктор был передан пустой список, то перед пробросом исключения,
            добавить в лог запись вида: 2022-05-31 17:30:41,074 ERROR Hippodrome: Horses list is empty*/
            logger.error("Horses list is empty");
            throw new IllegalArgumentException("Horses cannot be empty.");
        }

        this.horses = horses;
        /*В конце конструктора добавить в лог запись вида: 2022-05-31 17:05:26,152
        DEBUG Hippodrome: Создание Hippodrome, лошадей [7]*/
        logger.info("Создание Hippodrome, лошадей {}", horses.size());
    }

    public List<Horse> getHorses() {
        return Collections.unmodifiableList(horses);
    }

    public void move() {
        horses.forEach(Horse::move);
    }

    public Horse getWinner() {
        return horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();
    }
}
