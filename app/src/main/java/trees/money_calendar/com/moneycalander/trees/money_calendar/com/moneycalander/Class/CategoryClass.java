package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class;

/**
 * Created by trees on 6/26/15.
 */
public class CategoryClass {

    //CATEGORIES WE HAVE///
    public static final String MISCAL = "Miscellaneous";
    public static final String GROC = "Grocery";
    public static final String FOOD = "Fooding";
    public static final String SHOP = "Shopping";
    public static final String EDU = "Education";
    public static final String PERSO = "Personal";
    public static final String MAINT = "Maintainance";
    public static final String ENTERT = "Entertaintment";
    public static final String HEALTH = "Health";
    public static final String TRAVEL = "Travel";
    public static final String SAVE = "Saving";
    public static final String HOME = "Home";


    //MONTHS

    public static final String MONTHS[] = {"jan", "feb", "mar", "apr", "may", "jun","jul", "aug", "sep", "oct", "nov", "dec"};

    public static byte getCategoryPosition(String category)
    {
        if(category.equals(MISCAL))  return  0;
        if(category.equals(GROC))  return  1;
        if(category.equals(FOOD))  return  2;
        if(category.equals(SHOP))  return  3;
        if(category.equals(EDU))  return  4;
        if(category.equals(PERSO))  return  5;
        if(category.equals(MAINT))  return  6;
        if(category.equals(ENTERT))  return  7;
        if(category.equals(HEALTH))  return  8;
        if(category.equals(TRAVEL))  return  9;
        if(category.equals(SAVE))  return  10;
        if(category.equals(HOME))  return  11;


        return 0;
    }


}
