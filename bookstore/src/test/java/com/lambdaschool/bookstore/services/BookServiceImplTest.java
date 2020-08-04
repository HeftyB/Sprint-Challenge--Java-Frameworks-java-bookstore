package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.BookstoreApplication;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Role;
import com.lambdaschool.bookstore.models.Section;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookstoreApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//**********
// Note security is handled at the controller, hence we do not need to worry about security here!
//**********
public class BookServiceImplTest
{

    @Autowired
    private BookService bookService;

    @Autowired SectionService sectionService;

    @Before
    public void setUp() throws
            Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws
            Exception
    {
    }

    @Test
    public void a_findAll()
    {
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void b_findBookById()
    {
        assertEquals("Calling Texas Home", bookService.findBookById(30).getTitle());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void c_notFindBookById()
    {
        assertEquals("Calling Texas Home", bookService.findBookById(266));
    }

    @Test
    public void d_delete()
    {
        bookService.delete(30);
        assertEquals(4, bookService.findAll().size());
    }

    @Test
    public void e_save()
    {
        Book newBook = new Book("Real Code", "9000307474278", 2000, sectionService.findSectionById(21));
        newBook = bookService.save(newBook);
        assertEquals(5, bookService.findAll().size());
    }

    @Test
    public void f_update()
    {
        Book updatedBook = new Book();
        //Book updatedBook = bookService.findByTitle("Calling Texas Home");
        updatedBook.setTitle("Calling Texas Home UPDATED");
        bookService.update(updatedBook, bookService.findByTitle("Calling Texas Home").getBookid());
        assertEquals("Calling Texas Home UPDATED", bookService.findByTitle("Calling Texas Home UPDATED").getTitle());
    }

    @Test
    public void g_deleteAll()
    {
        bookService.deleteAll();
        assertEquals(0, bookService.findAll().size());
    }
}