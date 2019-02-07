import Model.Firm;
import Model.GisAddres;
import lombok.SneakyThrows;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.*;

/**
 * ПРограма для парсинга сайта 2gis города ТОмска
 * каталогов "/search/сантехнические%20товары"  и "/search/Сантехника%20%2F%20Санфаянс")
 * в excell файл outmaga.xls  .
 * Парсим  уникальный номер в 2 GIs, название, адрес, номер телефона
 *
 * Если надо увеличить каталоги для выбора,то добавь их в adress класса GisAddres
 */

public class Application {
    @SneakyThrows
    public static void main(String[] args) {
        GisAddres gisaddres = new GisAddres();

        Map<Long, Firm> firmMap = new HashMap<>();

//перебираем
        for (String findAdress : gisaddres.getAdress()) {
            int page = 1;

            while (true) {
                Document document;
                try {
                    out.println(findAdress + gisaddres.getPage(page));
                    document = Jsoup.connect(findAdress + gisaddres.getPage(page)).get();
                } catch (HttpStatusException Ex) {
                    out.println("break");
                    break;
                }



                Elements elements = document.getElementsByClass("miniCard__content");

                for (Element element1 : elements) {
                    Firm firm = new Firm();
                    firm.setName(element1.getElementsByClass("link miniCard__headerTitleLink").text());
                    firm.setAdress(element1.getElementsByClass("miniCard__address").text());
                    firm.setAa(element1.getElementsByClass("miniCard__headerTitle").
                            first().select("a").attr("href"));
                    firm.setAa(gisaddres.getServerAdress() + firm.getAa());


                    Document document1 = Jsoup.connect(firm.getAa()).get();
                    firm.setPhoneNumber(document1.getElementsByClass("contact__phonesItemLinkNumber").text());


                    firmMap.put(Long.parseLong(firm.getAa().replace("https://2gis.ru/tomsk/tomsk/firm/", "")), firm);

                    if (document.select("link").attr("rel", "next").isEmpty()) {
                        break;
                    }


                }
                page++;
            }
        }
        out.println(firmMap.size());
        firmMap.forEach((k, v) -> out.println(k + "   :" + v));
        controller.action(firmMap);

        //      }
    }
}
