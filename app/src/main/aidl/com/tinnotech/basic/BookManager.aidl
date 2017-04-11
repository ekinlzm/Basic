// IBookManager.aidl
package com.tinnotech.basic;

// Declare any non-default types here with import statements
import com.tinnotech.basic.Book;

interface BookManager {
    List<Book> getBooks();

    Book addBook(in Book book);
}
