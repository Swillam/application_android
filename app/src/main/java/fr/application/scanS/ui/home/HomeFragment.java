package fr.application.scanS.ui.home;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.ui.chapitre.ChapterFragment;
import fr.application.scanS.utils.AsyncTaskLoad;
import fr.application.scanS.utils.MangaListener;

public class HomeFragment extends Fragment implements MangaListener {
    private View v;
    private ArrayList<Manga> listManga;

    public void setListManga(ArrayList<Manga> listManga) {
        this.listManga = listManga;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        requireActivity().setTitle(R.string.title_home);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v.findViewById(R.id.noManga).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        new AsyncTaskLoad(this).execute();
    }

    public void OnMangaListener(int position, ImageView img) {
        Fragment fragment = new ChapterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manga", listManga.get(position));
        bundle.putParcelable("image", ((BitmapDrawable) img.getDrawable()).getBitmap());
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)// transition between home and manga
                .replace(((ViewGroup) v.getParent()).getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
