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

    @Override
    public String toString() {
        String asiat="";
        for (String s:
                components) {
            asiat+=s+"\n";
        }
        return
                //  "sortOrder=" + sortOrder +'\n' +  //mikä ihme on sortorder??
                "Aterialaji: " + name + '\n' +
                        "Hinta: " + price +'\n' +
                        "Asiakastyyppi: " + "opiskelija/henkilökunta/muut asiakkaat" + '\n'+'\n' +
                        "Ruoka: " +'\n'+ asiat  + "-----------------------------------------------------";
    }
}