package org.icspl.ecdtlp.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.icspl.ecdtlp.MainActivity;
import org.icspl.ecdtlp.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends android.app.Fragment {


    private Context mContext;
    private Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.about_page, container, false);
        // int key = getArguments().getInt(getString(R.string.about_key), 0);

        mContext = mView.getContext();
        mActivity = getActivity();

        return aboutCIC();
    }


    // cic
    public View aboutCIC() {

        Element versionElement = new Element();
        versionElement.setTitle("ABOUT US");

        String description = "International Certification Services has been providing their value added certification and" +
                " inspection services to the Government, Public and Private Sector Organizations involved in land," +
                " offshore, marine activities. It is a professional organization backed by devoted, highly qualified and" +
                " experienced personnel. We provide a wide spectrum of customer oriented certification and inspection services " +
                "in an efficient and cost effective manner. National Accreditation Board for Certification Bodies (NABCB) for Quality " +
                "Management System for specific sector & Environmental Management System for chemicals, chemical products & fibres, " +
                "rubber & plastic products. International Certification Services has a number of Regional Offices, Stations and sites " +
                "in India as well as overseas with highly qualified and competent manpower located close to our valued customers. " +
                "International Certification Services has well established Quality Assurance System and has been accredited by JAS-ANZ," +
                " the Joint Accreditation System of Australia and New Zealand, Australia for Quality and Environmental Management Systems.\n" +
                "\n";

        return new AboutPage(mActivity)
                .isRTL(false)
                .setImage(R.drawable.ic_tlp_logo)
                .addItem(new Element().setTitle("About ECD").setGravity(Gravity.CENTER))
                .setDescription(description)
                .addGroup("Careers")
                .addItem(getCopyRightsElement())
                .addGroup("Connect with us")
                .addEmail("icspl@gmail.com")
                .addWebsite("http://icstechnologies.org:4011")
                .create();
    }

    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        copyRightsElement.setTitle(getString(R.string.copyrigths)).setIconTint(R.color.md_green_200);
        copyRightsElement.setIconDrawable(R.drawable.ic_info);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, getString(R.string.copyrigths), Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity mainActivity = (MainActivity) getActivity();
        Toolbar sToolbar;
        if (mainActivity != null) {
            sToolbar = mainActivity.findViewById(R.id.toolbar);
            sToolbar.setTitle("About");
        }
    }
}
