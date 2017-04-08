package hcmut.exchanger;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Model mainModel;
    private Controller mainController;
    private RateTab rateTab;
    private ExchangerTab exchangerTab;
    private String tab1Title;
    private String tab2Title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mainModel = new Model(this);
        this.rateTab = new RateTab();
        this.exchangerTab = new ExchangerTab();
        this.tab1Title = getString(R.string.tab1);
        this.tab2Title = getString(R.string.tab2);
        this.mainController = new Controller(
                this,
                this.rateTab,
                this.exchangerTab,
                this.mainModel);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public Controller getController() {
        return this.mainController;
    }

    public Model getModel() {
        return this.mainModel;
    }





    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return MainActivity.this.rateTab;
                case 1:
                    return MainActivity.this.exchangerTab;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return MainActivity.this.tab1Title;
                case 1:
                    return MainActivity.this.tab2Title;
            }
            return null;
        }
    }
}
