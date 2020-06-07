package fr.application.Lyscan.ui.explorer;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.application.Lyscan.R;
import fr.application.Lyscan.data.database.ChapitreDAO;
import fr.application.Lyscan.data.database.MangaDAO;
import fr.application.Lyscan.data.type.Chapitre;
import fr.application.Lyscan.data.type.Manga;
import fr.application.Lyscan.ui.chapitre.ChapterFragment;
import fr.application.Lyscan.utils.API_url;
import fr.application.Lyscan.utils.MangaListener;

public class ExplorerFragment extends Fragment implements MangaListener {

    private View v;
    private ArrayList<Manga> listManga = new ArrayList<>();
    private RequestQueue requestqueue;
    private MangaDAO mangaDAO;
    private ExplorerAdapter explorerAdapter;
    private ChapitreDAO chapitreDAO;
    private ProgressBar loading;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_explorer, container, false);
        requireActivity().setTitle(R.string.title_explorer);
        this.mangaDAO = new MangaDAO(requireContext());
        this.chapitreDAO = new ChapitreDAO(requireContext());
        this.loading = v.findViewById(R.id.load_explorer_hot);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = v.findViewById(R.id.fragment_explorer_recycler_view);
        explorerAdapter = new ExplorerAdapter(requireContext(), listManga, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(explorerAdapter);
        if (listManga.isEmpty()) {
            listManga = new ArrayList<>();
            requestqueue = Volley.newRequestQueue(requireContext());
            parseJson();
        } else loading.setVisibility(View.INVISIBLE);

    }

    private void parseJson() {
        String url_json = API_url.url + "json.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url_json, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    mangaDAO.open();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonManga = response.getJSONObject(i);
                        Manga manga;
                        int id = mangaDAO.getIdManga(jsonManga.getString("name_raw"));
                        if (id == -1) {
                            manga = new Manga(jsonManga.getString("name_eng"),
                                    jsonManga.getString("name_raw"), jsonManga.getString("description"),
                                    jsonManga.getInt("in_progress"), null);
                            JSONArray chapitre = jsonManga.getJSONArray("chapitre");
                            ArrayList<Chapitre> chapitreList = new ArrayList<>();
                            for (int j = 0; j < chapitre.length(); j++) {
                                JSONObject jsonChapitre = chapitre.getJSONObject(j);
                                chapitreList.add(new Chapitre(-1, jsonChapitre.getInt("chapitre_nb"),
                                        jsonChapitre.getString("chapitre_name"), 0));
                            }
                            manga.setChapitres(chapitreList);
                        } else {
                            manga = mangaDAO.select(id);
                            chapitreDAO.open();
                            manga.setChapitres(chapitreDAO.getMangaChapitres(id));
                            chapitreDAO.close();
                        }
                        listManga.add(manga);
                        explorerAdapter.addItem(manga);
                        loading.setVisibility(View.INVISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mangaDAO.close();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(requireContext(), "Erreur réseau", Toast.LENGTH_SHORT);
                toast.show();
                error.printStackTrace();
            }
        });
        requestqueue.add(request);
    }

    @Override
    public void OnMangaListener(int position, ImageView img) {
        Fragment fragment = new ChapterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manga", listManga.get(position));
        bundle.putParcelable("image", ((BitmapDrawable) img.getDrawable()).getBitmap());
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)// transition between explorer and manga
                .replace(((ViewGroup) v.getParent()).getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
