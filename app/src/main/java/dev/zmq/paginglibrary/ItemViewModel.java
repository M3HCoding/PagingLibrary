package dev.zmq.paginglibrary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

public class ItemViewModel extends ViewModel
{
    LiveData<PagedList<Item>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer,Item>> liveDataSource;

    public ItemViewModel()
    {
        ItemDataSourceFactory itemDataSourceFactory=new ItemDataSourceFactory();
        liveDataSource=itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config pagedListConfig=(new PagedList.Config.Builder())
                .setEnablePlaceholders( false )
                .setPageSize( URL.PAGE_SIZE ).build();

        //Building the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();
    }
}
