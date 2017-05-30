package tea.demon.com.teaproject.data;

/**
 * Created by D&LL on 2017/5/26.
 */

public class TeaClass {
    private int class_id;
    private String name, description;

    public TeaClass(int class_id, String name, String description) {
        this.class_id = class_id;
        this.name = name;
        this.description = description;
    }

    public int getClass_id() {
        return class_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

