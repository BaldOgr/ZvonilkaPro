package kg.kloop.rinat.zvonilka;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.EventsAdapter;
import kg.kloop.rinat.zvonilka.adapters.ToDoAdapter;
import kg.kloop.rinat.zvonilka.adapters.UsersDataAdapter;
import kg.kloop.rinat.zvonilka.data.Event;
import kg.kloop.rinat.zvonilka.data.ToDo;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class MenuSelectActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static UsersDataAdapter usersDataAdapter;
    private static EventsAdapter eventsAdapter;
    private static ToDoAdapter toDoAdapter;
    private static boolean onBackground = false;
    private static boolean[] allLoaded = new boolean[3];
    private static ListView userDataList;
    private static ListView eventsList;
    private static ListView userToDoList;
    private static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        for (int i = 0; i < 3; i++) {
            allLoaded[i] = false;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_load_background);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_user_data) {
            Intent intent = new Intent(getApplicationContext(), AddUserDataActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_add_event) {
            Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_select_events, container, false);
                    initEventsFragment(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_select_users, container, false);
                    initUsersFragment(rootView);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_select_to_do, container, false);
                    initToDoFragment(rootView);
            }

            return rootView;

        }

        ///////////********* Initial Event List *****************//////////////////
        private void initEventsFragment(View view) {

            eventsList = (ListView) view.findViewById(R.id.select_activity_list_events);
            if (eventsAdapter == null) {
                Backendless.Persistence.of(Event.class).find(new DefaultCallback<BackendlessCollection<Event>>(getContext()) {
                    @Override
                    public void handleResponse(final BackendlessCollection<Event> response) {
                        final List<Event> events = response.getData();
                        eventsAdapter = new EventsAdapter(getContext(), events);
                        eventsList.setAdapter(eventsAdapter);
                        eventsList.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {

                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                Log.d("Events", i + " " + i1 + " " + i2);
                                if (!onBackground && !allLoaded[0] && (i + i1 * 2 >= i2 || i2 == 10)) {
                                    onBackground = true;
                                    progressBar.setVisibility(View.VISIBLE);
                                    response.getPage(10, i2, new AsyncCallback<BackendlessCollection<Event>>() {
                                        @Override
                                        public void handleResponse(BackendlessCollection<Event> eventBackendlessCollection) {
                                            eventsAdapter.add(eventBackendlessCollection.getData());
                                            eventsAdapter.notifyDataSetChanged();
                                            onBackground = false;
                                            allLoaded[0] = eventBackendlessCollection.getData().size() == 0;
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Log.d("Get Item on back", "Events Successful " + eventsAdapter.getCount());
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault backendlessFault) {
                                            Log.d("Get Item on back", "Error: " + backendlessFault.getMessage());
                                            onBackground = false;
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }
                        });
                        super.handleResponse(response);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        super.handleFault(fault);
                    }
                });
            } else {
                eventsList.setAdapter(eventsAdapter);
            }


            eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), EventActivity.class);
                    Event event = (Event) adapterView.getItemAtPosition(i);
                    intent.putExtra(Resources.EVENT_ID_KEY, event.getObjectId());
                    startActivity(intent);
                }
            });

        }

        ///////////********* Initial User List *****************//////////////////
        private void initUsersFragment(View view) {

            userDataList = (ListView) view.findViewById(R.id.select_activity_list_users);

            if (usersDataAdapter == null) {
                Backendless.Persistence.of(UserData.class).find(new DefaultCallback<BackendlessCollection<UserData>>(getContext()) {
                    @Override
                    public void handleResponse(final BackendlessCollection<UserData> response) {
                        List<UserData> usersData = response.getData();
                        usersDataAdapter = new UsersDataAdapter(getContext(), usersData);
                        userDataList.setAdapter(usersDataAdapter);
                        userDataList.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {

                            }

                            @Override
                            public void onScroll(final AbsListView absListView, int i, int i1, int i2) {
                                Log.d("Users", i + " " + i1 + " " + i2);
                                if (!onBackground && !allLoaded[1] && (i + i1 * 2 >= i2 || i2 <= i1 + i)) {
                                    onBackground = true;
                                    progressBar.setVisibility(View.VISIBLE);
                                    response.getPage(10, i2, new AsyncCallback<BackendlessCollection<UserData>>() {
                                        @Override
                                        public void handleResponse(BackendlessCollection<UserData> userDataBackendlessCollection) {
                                            usersDataAdapter.add(userDataBackendlessCollection.getData());
                                            usersDataAdapter.notifyDataSetChanged();
                                            onBackground = false;
                                            progressBar.setVisibility(View.INVISIBLE);
                                            allLoaded[1] = userDataBackendlessCollection.getData().size() == 0;
                                            Log.d("Get Item on back", "UsersData Successful " + usersDataAdapter.getCount());
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault backendlessFault) {
                                            Log.d("Get Item on back", "Error: " + backendlessFault.getMessage());
                                            onBackground = false;
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }
                        });
                        super.handleResponse(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        super.handleFault(fault);
                    }
                });
            } else {
                userDataList.setAdapter(usersDataAdapter);
            }
            userDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("Item Click", "clicked" + i);
                    final UserData userData = (UserData) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent();
                    intent.setClass(getContext(), UserDataActivity.class);
                    intent.putExtra(Resources.USER_DATA_ID_KEY, userData.getObjectId());
                    startActivity(intent);
                }
            });
        }

        //////////********** Initial ToDoUser List ************//////////////////
        private void initToDoFragment(View view) {
            userToDoList = (ListView) view.findViewById(R.id.select_activity_list_to_do_list);
            if (toDoAdapter == null) {
                Backendless.Persistence.of(ToDo.class).find(new DefaultCallback<BackendlessCollection<ToDo>>(getContext()) {
                    @Override
                    public void handleResponse(final BackendlessCollection<ToDo> response) {
                        List<ToDo> toDoList = response.getData();
                        toDoAdapter = new ToDoAdapter(getContext(), toDoList);
                        userToDoList.setAdapter(toDoAdapter);
                        userToDoList.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {

                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                Log.d("ToDos", i + " " + i1 + " " + i2);
                                if (!onBackground && !allLoaded[2] && (i + i1 * 2 >= i2 || i2 == 10)) {
                                    onBackground = true;
                                    progressBar.setVisibility(View.VISIBLE);
                                    response.getPage(10, i2, new AsyncCallback<BackendlessCollection<ToDo>>() {
                                        @Override
                                        public void handleResponse(BackendlessCollection<ToDo> toDoBackendlessCollection) {
                                            toDoAdapter.add(toDoBackendlessCollection.getData());
                                            toDoAdapter.notifyDataSetChanged();
                                            onBackground = false;
                                            allLoaded[2] = toDoBackendlessCollection.getData().size() == 0;
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Log.d("ToDos", "ToDos Successful " + toDoAdapter.getCount());
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault backendlessFault) {
                                            onBackground = false;
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }

                            }
                        });
                        super.handleResponse(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        super.handleFault(fault);
                    }
                });
            } else {
                userToDoList.setAdapter(toDoAdapter);
            }
            userToDoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getContext(), ToDoActivity.class);
                    intent.putExtra(getResources().getString(R.string.objectidquerry), toDoAdapter.getItem(i).getObjectId());
                    startActivity(intent);
                }
            });


        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.fragment_select_events);
                case 1:
                    return getResources().getString(R.string.fragment_select_users);
                case 2:
                    return getResources().getString(R.string.fragment_select_to_do);
            }
            return null;
        }

    }
}
