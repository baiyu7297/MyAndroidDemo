// IBookManager.aidl
package com.fantasy.android.demo.android;
import com.fantasy.android.demo.android.Book;
import com.fantasy.android.demo.android.IOnNewBookArrived;
interface IBookManager {

    void addBook(in Book book);
    List<Book> getBookList();
    void registerOnNewBookArrived(IOnNewBookArrived onNewBookArrived);
    void unregisterOnNewBookArrived(IOnNewBookArrived onNewBookArrived);
}
