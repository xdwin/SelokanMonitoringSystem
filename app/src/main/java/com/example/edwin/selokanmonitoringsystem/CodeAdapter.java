package com.example.edwin.selokanmonitoringsystem;

/**
 * Created by Edwin on 8/9/2015.
 */
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edwin on 7/31/2015.
 */
public class CodeAdapter extends BaseAdapter {

    Chapter chapter = new Chapter();

    List<Chapter> chapterList = chapter.getDataForListView();

    @Override
    public int getCount() {
        return chapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_codeadapter, parent,false);
        }

        TextView chapterName = (TextView)convertView.findViewById(R.id.textView1);
        TextView chapterDescription = (TextView)convertView.findViewById(R.id.textView2);

        Chapter chapter = chapterList.get(position);

        chapterName.setText(chapter.chapterName);
        chapterDescription.setText(chapter.chapterLocation);

        return convertView;
    }

}


class Chapter{
    String chapterName;
    String chapterLocation;
    String chapterKetinggian;
    String chapterArus;
    String chapterStatus;
    List<Chapter> chapterList;
    public List<Chapter> getDataForListView(){
        chapterList = new ArrayList<>();
//        for (int i = 0; i < 10; i++){
//            Chapter chapter = new Chapter();
//            chapter.chapterName = "Pos"+i;
//            chapter.chapterDescription = "Jalan ke "+i;
//            chapterList.add(chapter);
//        }
        SelokanXML xml = new SelokanXML();
//        int posTag = xml.countTag(xml.getXML(), "pos");
//        Log.i("COUNT", posTag+"");
        ArrayList<String> arr = xml.getAttributeContent(xml.getXML(), "pos", "nomor");
        ArrayList<String> arr2 = xml.populateDataFromPos(xml.getXML());
        for (int i = 0; i < arr.size(); i++) {
            String[] split = arr2.get(i).split(",");
            Chapter chapter = new Chapter();
            chapter.chapterName = "Pos " + split[0];
            chapter.chapterLocation = split[1];
            chapter.chapterKetinggian = split[2];
            chapter.chapterArus = split[3];
            //Baru handle ketinggian air
            if (Integer.parseInt(chapter.chapterKetinggian) < 3){
                chapter.chapterStatus = "Air mendekati permukaan Selokan";
                //Handle Notification
            }
            else
                chapter.chapterStatus = "OK";
            chapterList.add(chapter);
        }
        return chapterList;
    }

    public Chapter getCodeAdapter(int position){
        return chapterList.get(position);
    }



}


class SelokanXML{

    protected XmlPullParserFactory factory;
    protected XmlPullParser xml;
    protected InputStream stream;

    protected XmlPullParser getXML(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            InputStream input = new URL("http://codalight.in/tset/selokan.xml").openStream();
            factory = XmlPullParserFactory.newInstance();
            xml = factory.newPullParser();
            xml.setInput(new InputStreamReader(input));

            return xml;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return xml;
    }

    /**
     * Return isi xml dari tag yang ditentukan
     * @param xml xml yang dirujuk
     * @param tag tag yang dirujuk
     * @return isi content
     */
    protected String getContent(XmlPullParser xml, String tag){
        String result = "";
        try {
            int evenType = xml.getEventType();
            while(evenType != XmlPullParser.END_DOCUMENT){
                if (evenType == XmlPullParser.START_TAG && xml.getName().equals(tag)){
                    evenType = xml.next();
                    if (evenType == XmlPullParser.TEXT){
                        result = xml.getText();
                    }
                    else
                        continue;
                }
                evenType = xml.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected boolean checkTag(XmlPullParser xml, String tag){
        try {
            int eventType = xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG && xml.getName().equals(tag)){
                    return true;
                }
                else
                    eventType = xml.nextTag();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected ArrayList<String> getAttributeContent(XmlPullParser xml, String tag, String attr){
        ArrayList<String> result = new ArrayList<>();
        try {
            int eventType = xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG && xml.getName().equals(tag)){
                    if (xml.getAttributeCount() <= 0)
                        continue;
                    else
                        result.add(xml.getAttributeValue(null, attr));
                }
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected int countTag(XmlPullParser xml, String tag){
        int result = 0;
        try {
            int eventType = xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                if (eventType == XmlPullParser.START_TAG && xml.getName().equals(tag))
                    result++;
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Ambil semua data dari sebuah pos dan simpan dalam Array of String
     * @param xml xml yang dirujuk
     * @return Array of String yang berisi data-data
     */
    protected  ArrayList<String> populateDataFromPos(XmlPullParser xml){
        /**
         * String[] data
         * data[0] = Informasi nama Pos
         * data[1] = Lokasi pos
         * data[2] = Ketinggian air pada pos
         * data[3] = Arus air pada pos
         * data[4] = Status pos
         */
        ArrayList<String> arr = new ArrayList<String>();

        try {
            int eventType = xml.getEventType();
            String data = "";
            while (eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG && xml.getName().equals("pos")){
                    data += xml.getAttributeValue(null, "nomor");
                    Log.i("eve",xml.getName());
                }
                else if (eventType == XmlPullParser.START_TAG && !xml.getName().equals("pos")
                        && !xml.getName().equals("selokan")){
                    Log.i("ISI1",xml.getName());
                    xml.next();
                    data += ","+ xml.getText();
                    Log.i("ISI",xml.getName()+" "+xml.getText());
                }
                else if (eventType == XmlPullParser.END_TAG && xml.getName().equals("pos")){
                    Log.i("ANNNON",xml.getName());
                    arr.add(data);
                    data = "";
                }
                eventType = xml.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;

    }

}