package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by simon on 9/16/2016.
 */
public class CustomEarthquakeAdapter extends ArrayAdapter {
    private static final String LOCATION_SEPERATOR = "of";
    /*This is our custom constructor
    * This context is used to inflate the layout file in list is the data we populate to the list
    *
    * @param context        The current context used to inflate the layoutt
    * @param Earthquake     A list of Earthquake object to display the list*/

    public CustomEarthquakeAdapter(Context context, ArrayList<Earthquake> earthquake) {
        //We initiliaze the ArrayAdapters intenal storage for context and the list
        //the second argument is used when we are populationg the single textview
        //As this is custom arraydapter with three view. so we pass 0 here
        //object to represent in the listview
        super(context, 0, earthquake);
    }


    /*Provide view for an AdapterView (listview or GridView)
    *
    * @param position       postition of list of data displayedin list view
    * @param convertView    The recycle view to populate
    * @param parent         The parent view group that is used for inflation
    * return the view for the position in adapterview*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //check if the existing view is resued . otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_row, parent, false);
        }

        /*get earthquake object curremt posititon*/
        Earthquake currentEarthquake = (Earthquake) getItem(position);

        //find the textview in earthquake-row.xml ..initilize their id
        //ane settext the getter from earthquake
        TextView TextMagnitude = (TextView) listItemView.findViewById(R.id.textView);
        String formatedMagnitude = formatMagnitude(currentEarthquake.getmMagnitude());//formatMagnitude()method is defined below
        TextMagnitude.setText(formatedMagnitude);

        /*get the background color dynamically based on magnitude value*/
        //set the proper background color on magnitudeCircle Variable

        GradientDrawable magnitudeCircle = (GradientDrawable) TextMagnitude.getBackground();

        //get the current background color based on current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        //set the color on magnitude color
        magnitudeCircle.setColor(magnitudeColor);

        // If the original location string (i.e. "5km N of Cairo, Egypt") contains
        // a primary location (Cairo, Egypt) and a location offset (5km N of that city)
        // then store the primary location separately from the location offset in 2 Strings,
        // so they can be displayed in 2 TextViews.

        //for splitting string into two sepreate textview of location
        String originalLocation = currentEarthquake.getmLocation();
        String primaryLocation, locationoffset;

        if (originalLocation.contains(LOCATION_SEPERATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPERATOR);
            locationoffset = parts[0] + LOCATION_SEPERATOR;
            primaryLocation = parts[1];
        } else {
            locationoffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }


        //putting the string value in twoo seperate textfield
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetview = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetview.setText(locationoffset);



        //splitting the date in two seperate textfieold
        Date dateobject = new Date(currentEarthquake.getmDate());

        //setting the date in
        TextView TextDate = (TextView) listItemView.findViewById(R.id.textView3);
        //formate datet string
        String formatedDate = formatDate(dateobject);
        TextDate.setText(formatedDate);

        //setting the time
        String formatedTime = formatTime(dateobject);
        TextView TextTime = (TextView) listItemView.findViewById(R.id.textView4);
        TextTime.setText(formatedTime);
        return listItemView;


    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId = 0;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatTime(Date dateobject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateobject);
    }

    private String formatDate(Date dateobject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateobject);
    }
}
