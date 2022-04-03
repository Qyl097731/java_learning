package java8.day02.stream06;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName:  java_learing
 * packageName: java8.day02.stream06
 * date: 2022-04-03 22:50
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Trader trader;
    private Integer year;
    private Integer cost;
}
