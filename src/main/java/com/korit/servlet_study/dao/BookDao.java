package com.korit.servlet_study.dao;

import com.korit.servlet_study.config.DBConnectionMgr;
import com.korit.servlet_study.entity.Author;
import com.korit.servlet_study.entity.Book;
import com.korit.servlet_study.entity.BookCategory;
import com.korit.servlet_study.entity.Publisher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;

public class BookDao {
    private DBConnectionMgr mgr;
    private static BookDao bookDao;

    private BookDao() {
        mgr = DBConnectionMgr.getInstance();
    }

    public static BookDao getInstance() {
        if (bookDao == null) {
            bookDao = new BookDao();
        }
        return bookDao;
    }
    public Optional<Author> saveAuthor(Author author) {
        Collection con = null;
        PreparedStatement ps = null;
    try {
        con = mgr.getConnection();
        String sql = """
                insert into author values (default,?)
                """;
        ps = con.parallelStream(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, author.getAuthorName());
        ps.executeQuery();
        ResultSet rs = ps.getGeneratedKeys();
    }
        return Optional.ofNullable(author);
    }

    public Optional<Publisher> savePublisher(Publisher publisher) {

        return Optional.ofNullable(publisher);
    }

    public Optional<BookCategory> saveBookCategory(BookCategory bookCategory) {

        return Optional.ofNullable(bookCategory);
    }

    public Optional<Book> saveBookCategory(Book book) {

        return Optional.ofNullable(book);
    }
}
