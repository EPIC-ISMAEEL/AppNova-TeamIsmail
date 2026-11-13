package com.campus.lostfound;
import android.Manifest; import android.annotation.SuppressLint; import android.app.TimePickerDialog; import android.content.*; import android.location.*;
import android.os.Bundle; import android.provider.Settings; import android.view.*; import android.widget.*;
import androidx.activity.result.ActivityResultLauncher; import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat; import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView; import com.google.android.material.chip.ChipGroup; import org.json.*; import java.util.*;
public class PostFragment extends Fragment {
    double lat,lng; boolean has; long ts=System.currentTimeMillis();
    TextView loc; Button tb; View v; LocationManager lm; LocationListener ll;
    ActivityResultLauncher<String[]> perm=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),r->{
        if(granted()) locate(); else loc.setText("Location permission needed. Tap Refresh.");
    });
    boolean granted(){ Context c=requireContext(); return ContextCompat.checkSelfPermission(c,Manifest.permission.ACCESS_FINE_LOCATION)==0||ContextCompat.checkSelfPermission(c,Manifest.permission.ACCESS_COARSE_LOCATION)==0; }
    void set(Location l){ if(!isAdded()||loc==null) return; lat=l.getLatitude(); lng=l.getLongitude(); has=true; loc.setText(String.format(Locale.US,"📍 %.5f, %.5f",lat,lng)); }
    @SuppressLint("MissingPermission")
    void locate(){
        if(!granted()){ perm.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}); return; }
        lm=(LocationManager)requireContext().getSystemService(Context.LOCATION_SERVICE);
        if(ll!=null) lm.removeUpdates(ll);
        ll=new LocationListener(){
            public void onLocationChanged(Location l){ set(l); lm.removeUpdates(this); }
            public void onStatusChanged(String p,int s,Bundle b){}
            public void onProviderEnabled(String p){}
            public void onProviderDisabled(String p){}
        };
        boolean any=false; loc.setText("Getting location…");
        for(String p:lm.getProviders(true)){
            if(p.equals("passive")) continue; any=true;
            Location l=lm.getLastKnownLocation(p); if(l!=null&&!has) set(l);
            lm.requestLocationUpdates(p,0,0,ll);
        }
        if(!any){ loc.setText("Please turn on device location, then tap Refresh"); startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); }
    }
    public View onCreateView(LayoutInflater in, ViewGroup c, Bundle b){
        v=in.inflate(R.layout.fragment_post,c,false);
        Db.chips(in,v.findViewById(R.id.cat),false); loc=v.findViewById(R.id.loc); tb=v.findViewById(R.id.time);
        tb.setText("🕒 Found at: "+Db.fmt(ts));
        tb.setOnClickListener(x->{
            Calendar n=Calendar.getInstance();
            new TimePickerDialog(getContext(),(tp,h,m)->{
                Calendar k=Calendar.getInstance(); k.set(Calendar.HOUR_OF_DAY,h); k.set(Calendar.MINUTE,m);
                if(k.getTimeInMillis()>System.currentTimeMillis()) k.add(Calendar.DAY_OF_MONTH,-1);
                ts=k.getTimeInMillis(); tb.setText("🕒 Found at: "+Db.fmt(ts));
            },n.get(Calendar.HOUR_OF_DAY),n.get(Calendar.MINUTE),false).show();
        });
        v.findViewById(R.id.refresh).setOnClickListener(x->{ has=false; locate(); });
        v.findViewById(R.id.post).setOnClickListener(x->{
            String t=Db.t(v,R.id.title);
            if(t.isEmpty()||!has){ Toast.makeText(getContext(),"Add a title and wait for location",Toast.LENGTH_SHORT).show(); return; }
            JSONObject u=Db.me();
            try{
                Db.addItem(new JSONObject().put("title",t).put("cat",Db.sel(v.findViewById(R.id.cat))).put("desc",Db.t(v,R.id.desc)).put("place",Db.t(v,R.id.place))
                    .put("lat",lat).put("lng",lng).put("time",ts).put("by",u.optString("name")).put("phone",u.optString("phone")).put("email",u.optString("email")));
            }catch(JSONException e){}
            Toast.makeText(getContext(),"Posted!",Toast.LENGTH_SHORT).show();
            ((BottomNavigationView)requireActivity().findViewById(R.id.nav)).setSelectedItemId(R.id.feed);
        });
        locate();
        return v;
    }
    public void onDestroyView(){ super.onDestroyView(); if(lm!=null&&ll!=null) lm.removeUpdates(ll); }
}
