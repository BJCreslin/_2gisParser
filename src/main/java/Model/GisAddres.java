package Model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * класс из серии "где и, что искать"
 */

@Data
public class GisAddres {
    private String serverAdress = "https://2gis.ru/tomsk";

    private List<String> adress = Arrays.asList(
            serverAdress + "/search/сантехнические%20товары",
            serverAdress + "/search/Сантехника%20%2F%20Санфаянс");

    public String getPage(int page) {
        return "/page/" + page;

    }


}
