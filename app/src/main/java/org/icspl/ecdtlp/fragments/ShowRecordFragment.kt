package org.icspl.ecdtlp.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.icspl.ecdtlp.MainActivity
import org.icspl.ecdtlp.R
import java.util.*

class ShowRecordFragment : android.app.Fragment() {

    private var tabLayout: TabLayout? = null
    private var mContext: Context? = null
    private var viewpager: ViewPager? = null
    private var mActivity: FragmentActivity? = null
    private var mFragment: CreateReportFragment? = null
    private var mViewReportFragment: ViewReportFragment? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView: starts ")
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_show_record, container, false)

        val q = arguments.getString("quarter", null)
        val s = arguments.getString("section", null)
        Log.i(TAG, "onCreateView: Q: $q s: $s u:")

        mFragment = CreateReportFragment()
        mViewReportFragment = ViewReportFragment()
        val mBundle = Bundle()
        mBundle.putString("quarter", q)
        mBundle.putString("section", s)
        mFragment!!.arguments = mBundle
        mViewReportFragment!!.arguments = mBundle

        init(mView)
        return mView
    }

    // init
    private fun init(mView: View) {
        mActivity = activity as FragmentActivity
        mContext = mView.context
        tabLayout = mView.findViewById(R.id.tabLayout)
        viewpager = mView.findViewById(R.id.viewpager)

        setupViewPager(viewpager!!)
        tabLayout!!.setupWithViewPager(viewpager)

    }

    private fun setupViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(mActivity!!.supportFragmentManager)

        adapter.addFragment(mFragment, "Create")
        adapter.addFragment(mViewReportFragment, "View")
        viewpager.adapter = adapter
        viewpager.refreshDrawableState()
    }

    internal inner class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        init {
            notifyDataSetChanged()
            Log.i(TAG, "ViewPagerAdapter: notifying")
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }


        fun addFragment(fragment: Fragment?, title: String) {
            if (fragment != null) {
                mFragmentList.add(fragment)
            }
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val mainActivity = activity as MainActivity
            val sToolbar: Toolbar = mainActivity.findViewById(R.id.toolbar)
            sToolbar.title = "Show Records"
        }catch(e : Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private const val TAG = "ShowRecordFragment"
    }
}
