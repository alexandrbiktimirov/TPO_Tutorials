package com.example.tutorial12.services;

import org.springframework.stereotype.Service;
import com.example.tutorial12.model.Book;
import com.example.tutorial12.model.BookDTO;
import com.example.tutorial12.repositories.BookRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    //...
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;

    public BookService(BookRepository bookRepository, BookDtoMapper bookDtoMapper) {
        this.bookRepository = bookRepository;
        this.bookDtoMapper = bookDtoMapper;
    }

    public Iterable<BookDTO> getBooks(){
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(bookDtoMapper::map).toList();
    }

    public Optional<BookDTO> getBookById(int id){
        return bookRepository.findById(id).map(bookDtoMapper::map);
    }

    public BookDTO saveBook(BookDTO bookDTO){
        Book book = bookRepository.save(bookDtoMapper.map(bookDTO));
        return bookDtoMapper.map(book);
    }
    //...
    public Optional<BookDTO> replaceBook(Integer bookID, BookDTO bookDTO){
        System.out.println(bookRepository.existsById(bookID));
        if (!bookRepository.existsById(bookID))
            return Optional.empty();
        bookDTO.setBookID(bookID);

        Book newObject = bookDtoMapper.map(bookDTO);
        Book savedObject = bookRepository.save(newObject);
        return Optional.of(bookDtoMapper.map(savedObject));
    }

    public Optional<Book> updateBook(BookDTO bookDTO){
        return Optional.of(bookRepository.save(bookDtoMapper.map(bookDTO)));
    }

    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }

}
