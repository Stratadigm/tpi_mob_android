package com.biz.stratadigm.tpi.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.biz.stratadigm.tpi.entity.dto.VenueDTO;
import com.biz.stratadigm.tpi.R;
import com.biz.stratadigm.tpi.ui.adapter.VenueAdapter;
import com.biz.stratadigm.tpi.tools.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tamara on 12/15/16.
 * Class respresent list of venue
 */

public class VenueListFragment extends Fragment {

    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutMAnager;
    private ArrayList<VenueDTO> mListVenue = new ArrayList<>();
    private VenueAdapter mVenueAdapter;
    private SharedPreferences sharedPreferences;
    private int offset = 0;
    private TextView more, less;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_list_fragment, container, false);

        sharedPreferences = getActivity().getSharedPreferences(Constant.TAG, Context.MODE_PRIVATE);
        mList = (RecyclerView) view.findViewById(R.id.venueList);
        mVenueAdapter = new VenueAdapter(mListVenue, getActivity());
        mLayoutMAnager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(mLayoutMAnager);
        mList.setAdapter(mVenueAdapter);
        getVenueList();
        more = (TextView) view.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = offset + 20;
                mListVenue.clear();
                getVenueList();
                mVenueAdapter.notifyDataSetChanged();
            }
        });
        less = (TextView) view.findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (offset >= 20) {
                    offset = offset - 20;
                    mListVenue.clear();
                    getVenueList();
                    mVenueAdapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    private void getVenueList() {

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.VENUESLIST + "?offset=" + offset,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("tamara", response);
                            JSONArray response1 = new JSONArray(response);
                            for (int i = 0; i < response1.length(); i++) {
                                String id = response1.getJSONObject(i).getString("id");
                                String name = response1.getJSONObject(i).getString("name");
                                String submitted = response1.getJSONObject(i).getString("submitted");
                                JSONObject location = response1.getJSONObject(i).getJSONObject("location");
                                String lat = location.getString("Lat");
                                String lng = location.getString("Lng");
                                String thalis = response1.getJSONObject(i).getString("thalis");

                                VenueDTO venue = new VenueDTO(id, name, submitted, lat, lng, thalis);
                                mListVenue.add(venue);
                                mVenueAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tamara", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);//post request on queue
    }

}
