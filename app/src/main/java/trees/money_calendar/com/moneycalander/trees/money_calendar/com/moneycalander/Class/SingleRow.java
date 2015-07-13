package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class;


public class SingleRow
{
     int image;
     String amount;
     String description;
     String flow;
     String date;
     String cateogry;
     long id;


    public SingleRow(int image, String amount, String date, String description, String flow, String cateogry, long id)
    {
        this.image=image;
        this.amount=amount;
        this.date=date;
        this.description=description;
        this.flow=flow;
        this.cateogry=cateogry;
        this.id=id;
    }



    ////alll gettters heree


    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public String getAmount() {
        return amount;
    }

    public String getFlow() {
        return flow;
    }

    public String getDate()
    {
        return date;
    }

    public String getCateogry()
    {
        return cateogry;
    }

    public long getId()
    {
        return id;
    }



}

