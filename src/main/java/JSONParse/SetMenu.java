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
 * määrittelee yksittäisen ruokalajin muotoilun, näitä on päivän menussa useampia.
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



    public StringBuilder getRuokalajit() {
        ruokalajit= new StringBuilder();
        ruokalajit.append(name).append("\n")
                .append(price).append("\n");
        for (String s:
                components) {

                      ruokalajit.append(s).append("\n");
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