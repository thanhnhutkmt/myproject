package software.nhut.personalutilitiesforlife.data;

import android.util.Log;

/** small class which represents a contact on any view */
public class SimContact {
    public static final String TAG = SimContact.class.getSimpleName();
    private String name;
    private String number;
    private String id;
    
    public SimContact(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    /**  
     * @return the string for the Listview */
    @Override
    public String toString() {        
         return "["+ id + "]'" + name +"': " + number;
    }

    // null-safe string compare
    public boolean compareStrings(final String one, final String two) {
        if (one == null ^ two == null) {
            return false;
        }
        if (one == null && two == null) {
            return true;
        }
        return one.compareTo(two) == 0;
    }

    @Override
    public boolean equals(Object o) {
        // if not Contact, can't be true
        if(!(o instanceof SimContact))
            return false;
        SimContact c = (SimContact)o;
        
        // only if id's present, compare them
        if((id != null) && (id.length()) > 0 && (c.id.length() > 0))
            return c.id.compareTo(id) == 0;
        
        // if SimNames not equal...
        if(compareStrings(name, c.name) == false) {
            return false;
        }

        // finally if numbers not equal...
        return compareStrings(number, c.number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}