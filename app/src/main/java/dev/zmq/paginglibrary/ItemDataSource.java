package dev.zmq.paginglibrary;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer,Item>
{

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Item> callback) {
        RetrofitClient.getInstance().getApi()
                .getAnswers( URL.FIRST_PAGE,URL.PAGE_SIZE,URL.SITE_NAME )
                .enqueue( new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body()!=null)
                        {
                            callback.onResult( response.body().items,null,URL.FIRST_PAGE+1 );
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
                        System.out.println( "loadInitial"+t.getMessage() );
                    }
                } );
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance().getApi()
                .getAnswers( URL.FIRST_PAGE,URL.PAGE_SIZE,URL.SITE_NAME )
                .enqueue( new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        //if the current page is greater than one
                        //we are decrementing the page number
                        //else there is no previous page
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            //passing the loaded data
                            //and the previous page key
                            callback.onResult(response.body().items, adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
                        System.out.println( "loadBefore"+t.getMessage() );
                    }
                } );
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {
        RetrofitClient.getInstance().getApi()
                .getAnswers(URL.FIRST_PAGE,URL.PAGE_SIZE,URL.SITE_NAME  )
                .enqueue( new Callback<StackApiResponse>() {
                    @Override
                    public void onResponse(Call<StackApiResponse> call, Response<StackApiResponse> response) {
                        if (response.body() != null) {
                            //if the response has next page
                            //incrementing the next page number
                            Integer key = response.body().has_more ? params.key + 1 : null;

                            //passing the loaded data and next page value
                            callback.onResult(response.body().items, key);
                        }
                    }

                    @Override
                    public void onFailure(Call<StackApiResponse> call, Throwable t) {
                        System.out.println( "loadAfter"+t.getMessage() );

                    }
                } );
    }
}
