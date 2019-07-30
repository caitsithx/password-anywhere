package home.lixl.paw.store.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Group")
@XmlAccessorType(XmlAccessType.FIELD)
public class Group {
    @XmlElement(name = "Id")
    private String Id;

    public List<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(List<Pair> pairs) {
        this.pairs = pairs;
    }

    @XmlElement(name = "Pair")
    private List<Pair> pairs;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
