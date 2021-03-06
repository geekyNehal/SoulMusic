package com.geekynehal.soulmusic;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Adapters.MainScreenAdapter;

public class MainScreenFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RelativeLayout visibleLayout;
    RecyclerView recyclerViewMainScreen;
    RelativeLayout noSongs;
    ArrayList<Songs> songList;
    MainScreenAdapter mainScreenAdapter;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating the layout for the fragment
        View view=inflater.inflate(R.layout.fragment_main_screen, container, false);
        visibleLayout=view.findViewById(R.id.visibleLayout);
        noSongs=view.findViewById(R.id.noSongs);
        recyclerViewMainScreen=view.findViewById(R.id.recyclerViewMainScreen);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        try
        {
            songList=getSongFromPhone();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(songList==null)
        {
            visibleLayout.setVisibility(View.INVISIBLE);
            noSongs.setVisibility(View.INVISIBLE);
        }
        else
        {
            mainScreenAdapter= new MainScreenAdapter(songList,getContext());
            RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getContext());
            recyclerViewMainScreen.setLayoutManager(mlayoutManager);
            recyclerViewMainScreen.setItemAnimator(new DefaultItemAnimator());
            recyclerViewMainScreen.setAdapter(mainScreenAdapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public ArrayList<Songs> getSongFromPhone()
    {
        ArrayList<Songs> songArrayList = null;
        ContentResolver contentResolver=getActivity().getContentResolver();
        Uri allSongsListUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String select=MediaStore.Audio.Media.IS_MUSIC+" !=0";
        Cursor cursor=contentResolver.query(allSongsListUri,null,select,null,null);
        if(cursor!=null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    Songs songs=new Songs();
                    String data=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String[] res=data.split("\\.");
                    songs.setSongName(res[0]);
                    songs.setSongId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    songs.setSongArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    songs.setSongAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    songs.setSongPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    songs.setSongUri(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))));
                    songs.setDateIndex(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)));
                    String duration=getDuration(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))));
                    songs.setDuration(duration);
                    songArrayList.add(songs);
                } while (cursor.moveToNext());
            }
        }
        else{
            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        return songArrayList;
    }
    //Converting from milliseconds to second
    private String getDuration(long millis)
    {
         if(millis<0)
         {
             throw new IllegalArgumentException("Duration cannot be negative");
         }
         long minute=TimeUnit.MILLISECONDS.toMinutes(millis);
         millis-=TimeUnit.MINUTES.toMillis(minute);
         long second=TimeUnit.MILLISECONDS.toSeconds(millis);
         //1 minute=60000 milliseconds
         StringBuilder stringBuilder=new StringBuilder(6);
         stringBuilder.append(minute<10? "0"+minute:minute);
         stringBuilder.append(":");
         stringBuilder.append(second<10? "0"+second:second);
         return stringBuilder.toString();
    }

    /**
     * This interface is implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
