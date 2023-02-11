package com.example.recycler.entidad;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.recycler.entidad.Contacto;

import java.util.ArrayList;
import java.util.List;

public class BuscadorContactos {

    public static List<Contacto> getContactos(ContentResolver contentResolver) {

        List<Contacto> contactos = new ArrayList<>();

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                if (phoneCursor != null && phoneCursor.moveToFirst()) {
                    @SuppressLint("Range") String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactos.add(new Contacto(name, phone));
                }
                phoneCursor.close();
            }
        }
        cursor.close();
        return contactos;
    }

}
