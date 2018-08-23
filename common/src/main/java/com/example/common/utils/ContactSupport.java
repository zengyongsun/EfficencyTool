/**
 *
 */
package com.example.common.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.orhanobut.logger.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Lifeix
 *         <p/>
 *         读取联系人的工具类
 */
public class ContactSupport {

    /**
     * 获取全部联系人
     *
     * @param context
     * @return
     */
    public static List<Contact> getAllContacts(Context context) {
        List<Contact> contacts = new ArrayList<Contact>();

        ContentResolver cr = context.getContentResolver();
        String str[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
        Cursor cur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
                null, "sort_key");

        /*Cursor cur = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, "sort_key");*/
        processCursor2(context, cur, contacts);
        return contacts;
    }

    /**
     * 根据联系人名称搜索
     *
     * @param context
     * @param searchName
     * @return
     */
    public static List<Contact> searchByName(Context context, String searchName) {
        Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, searchName);
        String str[] = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID};
        Cursor cur = context.getContentResolver().query(uri, str, null, null, "sort_key");
        List<Contact> contacts = new ArrayList<>(cur.getCount());
        processCursor2(context, cur, contacts);
        return contacts;
    }

    private static void processCursor2(Context context, Cursor cursor, List<Contact> contacts) {
        if (cursor != null) {
            try {
                Contact contact = null;
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor.getString(cursor
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNumber = phoneNumber.replaceAll("-", "");
                    phoneNumber = phoneNumber.replace(" ", "");
                    if (Null.isNull(phoneNumber) || !isMobileNumber(phoneNumber)) {
                        // contactList.add(new Contact(name, phone));
                        continue;
                    }

                    contact = new Contact(name, phoneNumber);
                    if(contacts.contains(contact)){
                        contact = null;
                        continue;
                    }
                    // contactsInfo.setContactsPhotoId(cur.getLong(cur.getColumnIndex(Phone.PHOTO_ID)));
                    //long contactid = cur.getLong(cur
                    //        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    //long photoid = cur.getLong(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID));
                    // 如果photoid 大于0 表示联系人有头像 ，如果没有给此人设置头像则给他一个默认的
                /*if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts
                            .openContactPhotoInputStream(cr, uri);
                    contactsInfo.setBitmap(BitmapFactory.decodeStream(input));
                } else {
                    contactsInfo.setBitmap(BitmapFactory.decodeResource(
                            context.getResources(), R.drawable.ic_launcher));
                }*/
                    contacts.add(contact);
                }
            } catch (Exception e) {
                Logger.e("parse cursor error", e);
            } finally {
                cursor.close();
            }
        }
    }

    private static void processCursor(Context context, Cursor cur, List<Contact> contacts) {
        // 循环遍历
        if (cur != null && cur.moveToFirst()) {
            int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cur
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int phoneNumIndex = cur
                    .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            cur.getCount();
            do {
                // 获得联系人的ID号
                String contactId = cur.getString(idColumn);
                // 获得联系人姓名
                String name = cur.getString(displayNameColumn);
                // 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneNum = cur.getInt(phoneNumIndex);

                if (phoneNum > 0) {
                    // 获得联系人的手机号码
                    Cursor phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        do {
                            // 遍历所有的电话号码
                            String phone = phones
                                    .getString(
                                            phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (null == phone) {
                                continue;
                            }
                            phone = phone.replaceAll("-", "");
                            phone = phone.replace(" ", "");
                            if (null != phone /*&& 11 == phone.length() 已兼容（+86）*/
                                    && isMobileNumber(phone)) {
                                // contactList.add(new Contact(name, phone));
                                contacts
                                        .add(new Contact(name, phone, "", "CONTACT_MOBILE"));
                            }
                        } while (phones.moveToNext());
                    }
                    phones.close();
                }
            } while (cur.moveToNext());
            cur.close();
        }
    }

    public static Bitmap getContactPhotoByPhoneNum(Context context,
                                                   String phoneNum) {
        // 根据号码获得联系人头像
        // 获得Uri
        Uri uriNumber2Contacts = Uri.parse("content://com.android.contacts/"
                + "data/phones/filter/" + phoneNum);
        // 查询Uri，返回数据集
        Cursor cursorCantacts = context.getContentResolver().query(
                uriNumber2Contacts, null, null, null, null);
        // 如果该联系人存在
        if (cursorCantacts.getCount() > 0) {
            // 移动到第一条数据
            cursorCantacts.moveToFirst();
            // 获得该联系人的contact_id
            Long contactID = cursorCantacts.getLong(cursorCantacts
                    .getColumnIndex("contact_id"));
            // 获得contact_id的Uri
            Uri uri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI, contactID);
            // 打开头像图片的InputStream
            InputStream input = ContactsContract.Contacts
                    .openContactPhotoInputStream(context.getContentResolver(),
                            uri);
            // 从InputStream获得bitmap
            return BitmapFactory.decodeStream(input);
        }
        return null;
    }

    static Pattern p = Pattern
            .compile("^((\\+86)|(86))?((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

    public static boolean isMobileNumber(String phoneNo) {
        Matcher m = p.matcher(phoneNo);
        return m.matches();
    }

    public static class Contact {
        public String name;
        public String value;
        public String photo;
        public String type;

        public Contact(String name, String value) {
            super();
            this.name = name;
            this.value = value;
        }

        public Contact(String name, String value, String photo, String type) {
            super();
            this.name = name;
            this.value = value;
            this.photo = photo;
            this.type = type;
        }

        @Override
        public String toString() {
            return "Contact [name=" + name + ", value=" + value + ", photo="
                    + photo + ", type=" + type + "]";
        }

        @Override
        public boolean equals(Object o) {
            Contact other = (Contact) o;
            if(!Null.isNull(other) && other.isNotNull()){
                return other.name.equals(name) && other.value.equals(value);
            }
            return false;
        }

        public boolean isNotNull(){
            return !Null.isNull(name) && !Null.isNull(value);
        }
    }
}
