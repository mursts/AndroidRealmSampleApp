package jp.mursts.android.androidrealmsampleapp.models;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoDAO {
    private static final String TAG = TodoDAO.class.getSimpleName();

    static public RealmResults<Todo> loadAll(Realm realm) {
        return realm.where(Todo.class).findAllAsync();
    }

    static public Todo loadOne(Realm realm, long id) {
        return realm.where(Todo.class).equalTo("id", id).findFirstAsync();
    }

    static public void addTodo(Realm realm, final Todo todo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(todo);
            }
        });

    }
}
