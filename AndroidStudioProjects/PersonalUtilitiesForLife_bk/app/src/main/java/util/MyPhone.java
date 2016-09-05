package util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nhut on 6/22/2016.
 */
public class MyPhone {
    public static Map<String, String> getContactBook(ContentResolver cr, boolean keyIsName) {
        Uri uriContact = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = cr.query(uriContact, null, null, null, null);
        Map<String, String> listContact = new HashMap<String, String>();
        if (keyIsName)
            while (cursor.moveToNext()) {
                listContact.put(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            }
        else
            while (cursor.moveToNext()) {
                listContact.put(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
            }
        return listContact;
    }

    private static final boolean KEYISNAME = true;
    private static final boolean KEYISNUMBER = false;
    public static String replacePhoneNumberWithContactName(String phoneNumber, ContentResolver cr) {
        Map <String, String> contactBook = getContactBook(cr, KEYISNUMBER);
        String name = contactBook.get(phoneNumber);
        return (name != null) ? name : phoneNumber;
    }

    public static String replaceContactNameWithPhoneNumber(String contactName, ContentResolver cr) {
        Map<String, String> contactBook = getContactBook(cr, KEYISNAME);
        String phoneNumber = contactBook.get(contactName);
        return (phoneNumber != null) ? phoneNumber : contactName;
    }

}
