package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geekynehal.soulmusic.R;
import com.geekynehal.soulmusic.Songs;

import java.util.ArrayList;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>
{

    private final ArrayList<Songs> songDetail;
    private final Context context;
    public MainScreenAdapter(ArrayList<Songs> songDetails, Context context)
    {
        this.context=context;
        this.songDetail=songDetails;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.row_custom_mainscreen_adapter,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Songs songObj=songDetail.get(position);
        holder.trackTitle.setText(songObj.getSongName());
        holder.trackArtist.setText(songObj.getSong_artist());

    }

    @Override
    public int getItemCount()
    {
        if(songDetail==null)
        {
            return 0;
        }
        else
        {
            return songDetail.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trackTitle,trackArtist;
        RelativeLayout contentHolder;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            trackTitle=itemView.findViewById(R.id.trackTitle);
            trackArtist=itemView.findViewById(R.id.trackArtist);
            contentHolder=itemView.findViewById(R.id.contentRow);
        }
    }
}
