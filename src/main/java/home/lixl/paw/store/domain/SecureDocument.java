package home.lixl.paw.store.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SecureDocument")
@XmlAccessorType(XmlAccessType.FIELD)
public class SecureDocument {
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @XmlElement(name = "Group")
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }
}
