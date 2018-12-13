package com.alvaro.sergio.smov_yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TimelineFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    private static final String TAG = TimelineFragment.class.getSimpleName();
    private SimpleCursorAdapter mAdapter;
    private static final String[] FROM = {SupportServices.USER,
            SupportServices.MESSAGE, SupportServices.CREATED_AT};
    private static final int[] TO = {R.id.list_item_text_user,
            R.id.list_item_text_message, R.id.list_item_text_created_at};

    private static final int LOADER_ID = 42;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("Sin datos...");
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item,
                null, FROM, TO, 0);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }



    @NonNull
    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i != LOADER_ID)
            return null;
        Log.d(TAG,
                "onCreateLoader");
        return new CursorLoader(getActivity(), SupportServices.CONTENT_URI, null, null,
                null, SupportServices.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

    }

    class TimelineViewBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            if (view.getId() != R.id.list_item_text_created_at)
                return false;
// Convertimos el timestamp a tiempo relativo
            long timestamp = cursor.getLong(columnIndex);
            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(timestamp);
            ((TextView)view).setText(relativeTime);
            return true;
        }
    }

}
