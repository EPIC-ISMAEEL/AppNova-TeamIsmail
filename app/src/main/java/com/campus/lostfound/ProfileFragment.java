package com.campus.lostfound;
import android.content.Intent; import android.os.Bundle; import android.view.*; import org.json.*; import androidx.fragment.app.Fragment;
public class ProfileFragment extends Fragment {
    public View onCreateView(LayoutInflater in, ViewGroup c, Bundle b){
        View v=in.inflate(R.layout.fragment_profile,c,false); JSONObject u=Db.me(); String n=u.optString("name"), e=u.optString("email");
        JSONArray a=Db.arr("items"); int cnt=0; for(int i=0;i<a.length();i++) if(a.optJSONObject(i).optString("email").equals(e)) cnt++;
        Db.s(v,R.id.av,n.isEmpty()?"?":n.substring(0,1).toUpperCase()); Db.s(v,R.id.name,n); Db.s(v,R.id.email,e);
        Db.s(v,R.id.phone,"📞  "+u.optString("phone")); Db.s(v,R.id.posts,"📦  Items posted: "+cnt);
        v.findViewById(R.id.out).setOnClickListener(x->{ Db.logout(); startActivity(new Intent(getContext(),AuthActivity.class)); requireActivity().finish(); });
        return v;
    }
}
