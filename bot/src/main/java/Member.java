import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Member {
    private String name = "";
    private Date startDate;
    private Date endDate;
    private Integer id;

    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy", Locale.GERMAN);

    public Member(Integer id, String name, String startDate, String endDate) throws ParseException {
        this.name = name;
        this.startDate = formatter.parse(startDate);
        this.endDate = formatter.parse(endDate);
        this.id = id;
    }
    @Deprecated
    public String toJSON(){
        return String.format("{\"endDate\":\"%s\",\"name\":\"%s\",\"startDate\":\"%s\",\"hash\":%s}", formatter.format(this.endDate), this.name, formatter.format(this.startDate), this.id);
    }
    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
