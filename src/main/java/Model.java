import java.util.Date;
public class Model implements Comparable<Model>{
    public Model(Date date, String name) {
        this.date = date;
        this.name = name;
    }
    Date date;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @Override
    public String toString() {
        return "Model{" +
                "date=" + date +
                ", name='" + name + '\'' +
                '}';
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    String name;
    @Override
    public int compareTo(Model o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return getDate().compareTo(o.getDate());
    }
}