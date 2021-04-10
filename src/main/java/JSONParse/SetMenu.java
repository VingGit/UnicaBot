package JSONParse;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "SortOrder",
        "Name",
        "Price",
        "Components"
})

/**
 * Rakenneluokka JSON-parseemiseen.
 * @author Valtteri Ingman
 */
public class SetMenu  {

    @JsonProperty("SortOrder")
    private Integer sortOrder;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Price")
    private String price;
    @JsonProperty("Components")
    private List<String> components = null;
    private   StringBuilder ruokalajit;

    public String getName() {//onko kyseessä, jälkkäri, lounas vaiko mikä
        return name;
    }

    public String getPrice() {//hinnasto formaatissa opiskelija/työntekijät/muut
        return price;
    }

    public StringBuilder getRuokalajit() {
        ruokalajit= new StringBuilder();
        for (String s:
                components) {
            ruokalajit.append(name).append("\n")
                      .append(price).append("\n")
                      .append(s).append("\n").append("\n");
        }
        return ruokalajit;
    }

    @Override
    public String toString() {

        return
                //  "sortOrder=" + sortOrder +'\n' +  //mikä ihme on sortorder??
                "Aterialaji: " + name + '\n' +
                        "Hinta: " + price +'\n' +
                        "Asiakastyyppi: " + "opiskelija/henkilökunta/muut asiakkaat" + '\n'+'\n' +
                        "Ruoka: " +'\n'+ ruokalajit  + "-----------------------------------------------------";
    }
}